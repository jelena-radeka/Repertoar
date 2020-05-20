package com.example.repoertoar1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepertoarActivity extends AppCompatActivity implements AdapterLista.OnItemClickListener {

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private AdapterLista adapterLista;
    private List<Filmovi> filmovi;
    private SharedPreferences prefs;
    public static final String NOTIF_CHANNEL_ID = "notif_1234";
    private TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertoar);

        createNotificationChannel();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        recyclerView = findViewById(R.id.rvRepertoarLista);
        setupToolbar();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            filmovi = getDataBaseHelper().getFilmoviDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        adapterLista = new AdapterLista(this, filmovi, this);
        recyclerView.setAdapter(adapterLista);
        textView = findViewById(R.id.textLista);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repertoar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_repertoar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_filmove:
                deleteFilmove();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteFilmove() {

        try {
            ArrayList<Filmovi> filmoviZaBrisanje = (ArrayList<Filmovi>) getDataBaseHelper().getFilmoviDao().queryForAll();
            getDataBaseHelper().getFilmoviDao().delete(filmoviZaBrisanje);


            adapterLista.removeAll();
            adapterLista.notifyDataSetChanged();

            String tekstNotifikacije = "Repertoar obrisan";
            boolean toast = prefs.getBoolean(getString(R.string.toast_key), false);
            boolean notif = prefs.getBoolean(getString(R.string.notif_key), false);

            if (toast) {
                Toast.makeText(RepertoarActivity.this, tekstNotifikacije, Toast.LENGTH_LONG).show();

            }

            if (notif) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(RepertoarActivity.this, NOTIF_CHANNEL_ID);
                builder.setSmallIcon(R.drawable.ic_delete_black_24dp);
                builder.setContentTitle("Notifikacija");
                builder.setContentText(tekstNotifikacije);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


                builder.setLargeIcon(bitmap);
                notificationManager.notify(1, builder.build());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(int position) {
        Filmovi film = adapterLista.get(position);

        String tekstNotifikacije = "Projekcija u " + film.getmVreme() + " za " + film.getmNaziv() +
                " je uspesno reservisana";

        boolean toast = prefs.getBoolean(getString(R.string.toast_key), false);
        boolean notif = prefs.getBoolean(getString(R.string.notif_key), false);

        if (toast) {
            Toast.makeText(RepertoarActivity.this, tekstNotifikacije, Toast.LENGTH_LONG).show();

        }

        if (notif) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(RepertoarActivity.this, NOTIF_CHANNEL_ID);
            builder.setSmallIcon(android.R.drawable.ic_menu_add);
            builder.setContentTitle("Notifikacija");
            builder.setContentText(tekstNotifikacije);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


            builder.setLargeIcon(bitmap);
            notificationManager.notify(1, builder.build());

        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

    }

    public DatabaseHelper getDataBaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "Description of My Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Filmovi> film = null;
        try {
            film = (ArrayList<Filmovi>) getDataBaseHelper().getFilmoviDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (film.size() != 0) {
            textView.setVisibility(View.GONE);

        } else {
            textView.setText("Lista je prazna");
        }
    }
}
