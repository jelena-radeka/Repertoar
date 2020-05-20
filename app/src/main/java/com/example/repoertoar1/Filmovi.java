package com.example.repoertoar1;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Filmovi.TABLE_NAME_USERS)
public class Filmovi {

    public static final String TABLE_NAME_USERS = "filmovi";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAZIV = "naziv";
    public static final String FIELD_NAME_GODINA = "godina";
    public static final String FIELD_NAME_CENA = "cena";
    public static final String FIELD_NAME_VREME = "vreme";
    public static final String FIELD_NAME_IMAGE   = "image";



    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAZIV)
    private String mNaziv;

    @DatabaseField(columnName = FIELD_NAME_GODINA)
    private String mGodina;

    @DatabaseField(columnName = FIELD_NAME_CENA)
    private String mCena;

    @DatabaseField(columnName = FIELD_NAME_VREME)
    private String mVreme;

    @DatabaseField(columnName = FIELD_NAME_IMAGE)
    private String mImage;

    public Filmovi() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }


    public String getmNaziv() {
        return mNaziv;
    }

    public void setmNaziv(String mNaziv) {
        this.mNaziv = mNaziv;
    }

    public String getmGodina() {
        return mGodina;
    }

    public void setmGodina(String mGodina) {
        this.mGodina = mGodina;
    }

    public String getmCena() {
        return mCena;
    }

    public void setmCena(String mCena) {
        this.mCena = mCena;
    }

    public String getmVreme() {
        return mVreme;
    }

    public void setmVreme(String mVreme) {
        this.mVreme = mVreme;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}


