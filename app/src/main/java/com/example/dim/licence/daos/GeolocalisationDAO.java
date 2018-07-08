package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Region;
import com.example.dim.licence.entities.Ville;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_ID;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_REGION;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE1;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE2;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE3;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_COMPLEMENT;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ID;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LATITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LONGITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ZIP_CODE;
import static com.example.dim.licence.models.DbHelper.TABLE_ACTION;
import static com.example.dim.licence.models.DbHelper.TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;
import static com.example.dim.licence.models.DbHelper.TABLE_VILLE;


public class GeolocalisationDAO implements BaseDAO<Geolocalisation> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        ContentValues contentValues = new ContentValues();
        if (geolocalisation.getGeolocVille() != null) {
            contentValues.put(KEY_GEOLOC_VILLE, geolocalisation.getGeolocVille().getVilleId());
        }
        contentValues.put(KEY_GEOLOC_ADRESSE1, geolocalisation.getGeolocAdresse1());
        contentValues.put(KEY_GEOLOC_ADRESSE2, geolocalisation.getGeolocAdresse2());
        contentValues.put(KEY_GEOLOC_ADRESSE3, geolocalisation.getGeolocAdresse3());
        contentValues.put(KEY_GEOLOC_COMPLEMENT, geolocalisation.getGeolocComplement());
        long id = sqLiteDatabase.insert(TABLE_GEOLOCALISATION, null, contentValues);
        geolocalisation.setGeolocId(id);
        Log.i(ARG_DEBUG, "insert: "+id);
        return id;
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Geolocalisation geolocalisation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GEOLOC_ID, geolocalisation.getGeolocId());
        contentValues.put(KEY_GEOLOC_VILLE, geolocalisation.getGeolocVille().getVilleId());
        contentValues.put(KEY_GEOLOC_ADRESSE1, geolocalisation.getGeolocAdresse1());
        contentValues.put(KEY_GEOLOC_ADRESSE2, geolocalisation.getGeolocAdresse2());
        contentValues.put(KEY_GEOLOC_ADRESSE3, geolocalisation.getGeolocAdresse3());
        contentValues.put(KEY_GEOLOC_COMPLEMENT, geolocalisation.getGeolocComplement());

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
                new String[]{KEY_GEOLOC_ID, KEY_GEOLOC_VILLE, KEY_GEOLOC_ADRESSE1,
                        KEY_GEOLOC_ADRESSE2, KEY_GEOLOC_ADRESSE3, KEY_GEOLOC_COMPLEMENT},KEY_GEOLOC_ID+ " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    geolocalisation = new Geolocalisation();

                    geolocalisation.setGeolocId(cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_ID)));
                    geolocalisation.setGeolocAdresse1(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE1)));
                    geolocalisation.setGeolocAdresse2(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE2)));
                    geolocalisation.setGeolocAdresse3(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE3)));
                    geolocalisation.setGeolocComplement(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_COMPLEMENT)));

                    if (geolocalisation.getGeolocVille() != null) {
                        Ville ville = getVilleById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_VILLE)));
                        geolocalisation.setGeolocVille(ville);
                    }

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
                    geolocalisation.setGeolocAdresse1(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE1)));
                    geolocalisation.setGeolocAdresse2(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE2)));
                    geolocalisation.setGeolocAdresse3(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE3)));
                    geolocalisation.setGeolocComplement(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_COMPLEMENT)));

                    if (geolocalisation.getGeolocVille() != null) {
                        Ville ville = getVilleById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_VILLE)));
                        geolocalisation.setGeolocVille(ville);
                    }

                    list.add(geolocalisation);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }

    public Ville getVilleById(SQLiteDatabase sqLiteDatabase, long itemId) {

        Ville ville = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                KEY_VILLE_ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_VILLE_DEPARTEMENT)));
                    ville.setVilleDepartement(departement);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return ville;
    }

    public Departement getDepartementById(SQLiteDatabase sqLiteDatabase, long itemId) {

        Departement departement = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_DEPARTEMENT,
                new String[]{KEY_DEPARTEMENT_ID, KEY_DEPARTEMENT_LIBELLE, KEY_DEPARTEMENT_REGION},
                KEY_REGION_ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    departement = new Departement();

                    departement.setDepartementId(cursor.getLong(cursor.getColumnIndex(KEY_DEPARTEMENT_ID)));
                    departement.setDepartementLibelle(cursor.getString(cursor.getColumnIndex(KEY_DEPARTEMENT_LIBELLE)));

                    Region region = getRegionById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_DEPARTEMENT_REGION)));
                    departement.setDepartementRegion(region);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return departement;
    }

    public Region getRegionById(SQLiteDatabase sqLiteDatabase, long itemId) {

        Region region = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_REGION,
                new String[]{KEY_REGION_ID, KEY_REGION_LIBELLE},
                KEY_REGION_ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    region = new Region();

                    region.setRegionId(cursor.getLong(cursor.getColumnIndex(KEY_REGION_ID)));
                    region.setRegionLibelle(cursor.getString(cursor.getColumnIndex(KEY_REGION_LIBELLE)));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return region;
    }
}
