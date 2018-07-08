package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.Region;
import com.example.dim.licence.entities.Ville;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_ID;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_REGION;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LATITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LONGITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ZIP_CODE;
import static com.example.dim.licence.models.DbHelper.TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;
import static com.example.dim.licence.models.DbHelper.TABLE_VILLE;

public class VilleDAO implements BaseDAO<Ville> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Ville ville) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VILLE_LIBELLE, ville.getVilleLibelle());
        contentValues.put(KEY_VILLE_ZIP_CODE, ville.getVilleZipCode());
        contentValues.put(KEY_VILLE_LATITUDE, ville.getVilleLatitude());
        contentValues.put(KEY_VILLE_LONGITUDE, ville.getVilleLongitude());
        contentValues.put(KEY_VILLE_DEPARTEMENT, ville.getVilleDepartement().getDepartementId());
        return sqLiteDatabase.insert(TABLE_VILLE, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Ville ville) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_VILLE_LIBELLE, ville.getVilleLibelle());
        cv.put(KEY_VILLE_ZIP_CODE, ville.getVilleZipCode());
        cv.put(KEY_VILLE_LATITUDE, ville.getVilleLatitude());
        cv.put(KEY_VILLE_LONGITUDE, ville.getVilleLongitude());
        cv.put(KEY_VILLE_DEPARTEMENT, ville.getVilleDepartement().getDepartementId());

        return sqLiteDatabase.update(TABLE_VILLE, cv,
                " WHERE " + KEY_VILLE_ID + " = ? ",
                new String[]{String.valueOf(ville.getVilleId())}) > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Ville ville) {
        return sqLiteDatabase.delete(TABLE_VILLE,
                null,
                new String[]{String.valueOf(ville.getVilleId())}) > 0;
    }

    @Override
    public Ville getById(SQLiteDatabase sqLiteDatabase, int itemId) {

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

    @Override
    public List<Ville> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Ville> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Ville ville;

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, ville.getVilleDepartement().getDepartementId());
                    ville.setVilleDepartement(departement);

                    list.add(ville);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }


    public Ville getByLibelleAndZip(SQLiteDatabase sqLiteDatabase, String libelle, String zip) {

        Ville ville = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT DISTINCT " + KEY_VILLE_ID + ", "
                + KEY_VILLE_LIBELLE + ", " + KEY_VILLE_ZIP_CODE + ", " + KEY_VILLE_DEPARTEMENT + ", "
                + KEY_VILLE_LATITUDE + ", " + KEY_VILLE_LONGITUDE
                + " FROM " + TABLE_VILLE
                + " WHERE " + KEY_VILLE_LIBELLE + "=? AND "+KEY_VILLE_ZIP_CODE+"=?;", new String[]{libelle, zip});

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

    public List<Ville> filterByLibelle(SQLiteDatabase sqLiteDatabase, String text) {
        ArrayList<Ville> list = null;
        // satrts with - change


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_VILLE
                + " WHERE LIKE('" + text + "%', " + KEY_VILLE_LIBELLE + ")=1 LIMIT 15;", null);

       /* Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                KEY_VILLE_LIBELLE +" LIKE '%^%%' ESCAPE '^' " ,new String[]{text.toLowerCase()}, null, null, null, "15");
*/
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Ville ville;

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, ville.getVilleDepartement().getDepartementId());
                    ville.setVilleDepartement(departement);

                    list.add(ville);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return list;
    }

    public List<Ville> filterByZipCode(SQLiteDatabase sqLiteDatabase, String text) {
        ArrayList<Ville> list = null;
        // satrts with - change

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_VILLE
                + " WHERE LIKE('" + text + "%', " + KEY_VILLE_ZIP_CODE + ")=1 LIMIT 15;", null);
/*
        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                KEY_VILLE_ZIP_CODE +" LIKE '?%' " ,new String[]{text}, null, null, null, "15");
*/
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Ville ville;

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, ville.getVilleDepartement().getDepartementId());
                    ville.setVilleDepartement(departement);

                    list.add(ville);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return list;
    }

    public List<Ville> getFirst15(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Ville> list = null;
        // satrts with - change

        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                null, null, null, null, null, "15");
/*
        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                KEY_VILLE_ZIP_CODE +" LIKE '?%' " ,new String[]{text}, null, null, null, "15");
*/
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Ville ville;

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, ville.getVilleDepartement().getDepartementId());
                    ville.setVilleDepartement(departement);

                    list.add(ville);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return list;
    }

    public boolean isEmpty(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Ville> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                KEY_VILLE_ID + " BETWEEN 1 AND 2", null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Ville ville;

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, ville.getVilleDepartement().getDepartementId());
                    ville.setVilleDepartement(departement);

                    list.add(ville);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // if empty
        if (list == null) {
            return true;
        }

        return list.size() <= 1;
    }

    public Departement getDepartementById(SQLiteDatabase sqLiteDatabase, long itemId) {

        Departement departement = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_DEPARTEMENT,
                new String[]{KEY_DEPARTEMENT_ID, KEY_DEPARTEMENT_LIBELLE, KEY_DEPARTEMENT_REGION},
                KEY_DEPARTEMENT_ID + " = ?",
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
