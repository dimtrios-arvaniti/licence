package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Geolocalisation;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_CODE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ID;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_PAYS;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.TABLE_ACTION;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;


public class GeolocalisationDAO implements BaseDAO<Geolocalisation> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GEOLOC_PAYS, geolocalisation.getGeolocPays());
        contentValues.put(KEY_GEOLOC_VILLE, geolocalisation.getGeolocVille());
        contentValues.put(KEY_GEOLOC_CODE, geolocalisation.getGeolocCode());
        contentValues.put(KEY_GEOLOC_ADRESSE, geolocalisation.getGeolocAdresse());
        long id = sqLiteDatabase.insert(TABLE_GEOLOCALISATION, null, contentValues);
        geolocalisation.setGeolocId(id);
        Log.i(ARG_DEBUG, "insert: "+id);
        return id;
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GEOLOC_ID, geolocalisation.getGeolocId());
        contentValues.put(KEY_GEOLOC_PAYS, geolocalisation.getGeolocPays());
        contentValues.put(KEY_GEOLOC_VILLE, geolocalisation.getGeolocVille());
        contentValues.put(KEY_GEOLOC_CODE, geolocalisation.getGeolocCode());
        int rows = sqLiteDatabase.update(TABLE_GEOLOCALISATION, null, " WHERE "+KEY_GEOLOC_ID+" = ?",
                new String[]{String.valueOf(geolocalisation.getGeolocId())});
        return rows > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
         return sqLiteDatabase.delete(TABLE_GEOLOCALISATION, null,
                 new String[]{String.valueOf(geolocalisation.getGeolocId())}) > 0;
    }


    @Override
    public Geolocalisation getById(SQLiteDatabase sqLiteDatabase, int itemId) {
        Geolocalisation geolocalisation = null;

        Cursor cursor = sqLiteDatabase.query(TABLE_GEOLOCALISATION,
                new String[]{KEY_GEOLOC_ID, KEY_GEOLOC_PAYS, KEY_GEOLOC_VILLE, KEY_GEOLOC_CODE,
                        KEY_GEOLOC_ADRESSE},KEY_GEOLOC_ID+ " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    geolocalisation = new Geolocalisation();

                    geolocalisation.setGeolocId(cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_ID)));
                    geolocalisation.setGeolocPays(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_PAYS)));
                    geolocalisation.setGeolocVille(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_VILLE)));
                    geolocalisation.setGeolocCode(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_CODE)));
                    geolocalisation.setGeolocAdresse(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE)));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return geolocalisation;

    }

    @Override
    public List<Geolocalisation> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Geolocalisation> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_ACTION,
                new String[]{KEY_ACTION_ID, KEY_ACTION_LIBELLE},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                int rowNbr = 0;
                Geolocalisation geolocalisation = null;

                do {
                    geolocalisation = new Geolocalisation();

                    geolocalisation.setGeolocId(cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_ID)));
                    geolocalisation.setGeolocPays(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_PAYS)));
                    geolocalisation.setGeolocVille(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_VILLE)));
                    geolocalisation.setGeolocCode(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_CODE)));
                    geolocalisation.setGeolocAdresse(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE)));

                    list.add(geolocalisation);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }
}
