package com.example.dim.licence.entities.daos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Geolocalisation;

import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_CODE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_PAYS;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.KEY_ID;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;


public class GeolocalisationDAO {

    public long insertGeolocalisation(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GEOLOC_PAYS, geolocalisation.getGeolocPays());
        contentValues.put(KEY_GEOLOC_VILLE, geolocalisation.getGeolocVille());
        contentValues.put(KEY_GEOLOC_CODE, geolocalisation.getGeolocCode());
        contentValues.put(KEY_GEOLOC_ADRESSE, geolocalisation.getGeolocAdresse());
        return sqLiteDatabase.insert(TABLE_GEOLOCALISATION, null, contentValues);
    }

    public int updateGeolocalisation(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GEOLOC_PAYS, geolocalisation.getGeolocPays());
        contentValues.put(KEY_GEOLOC_VILLE, geolocalisation.getGeolocVille());
        contentValues.put(KEY_GEOLOC_CODE, geolocalisation.getGeolocCode());
        return sqLiteDatabase.update(TABLE_GEOLOCALISATION, null, " WHERE "+KEY_ID+" = ? ",
                new String[]{String.valueOf(geolocalisation.getGeolocId())});
    }

    public void deleteGeolocalisation(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        sqLiteDatabase.delete(TABLE_GEOLOCALISATION, null, new String[]{String.valueOf(geolocalisation.getGeolocId())});
    }
}
