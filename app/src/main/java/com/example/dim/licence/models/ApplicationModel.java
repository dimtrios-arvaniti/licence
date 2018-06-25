package com.example.dim.licence.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dim.licence.entities.daos.GeolocalisationDAO;
import com.example.dim.licence.entities.daos.VigneronDAO;

import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_ACTION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_APPUSER;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_DOMAINE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_HISTORIQUE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VIN;
import static com.example.dim.licence.models.DbHelper.DATABASE_NAME;
import static com.example.dim.licence.models.DbHelper.DATABASE_VERSION;

public class ApplicationModel extends SQLiteOpenHelper {

    private static ApplicationModel model;
    private GeolocalisationDAO geolocalisationDAO;
    private VigneronDAO vigneronDAO;

    public ApplicationModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        geolocalisationDAO = new GeolocalisationDAO();
        vigneronDAO = new VigneronDAO();
    }


    public static ApplicationModel getInstance(Context context) {
        if (model == null) {
            model = new ApplicationModel(context);
        }
        return model;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_DOMAINE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TYPEVIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_GEOLOCALISATION);
        sqLiteDatabase.execSQL(CREATE_TABLE_APPUSER);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIGNERON);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORIQUE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // FIRST VERSION FOR NOW !
    }

    public GeolocalisationDAO getGeolocalisationDAO() {
        return geolocalisationDAO;
    }

    public VigneronDAO getVigneronDAO() {
        return vigneronDAO;
    }
}
