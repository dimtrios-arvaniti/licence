package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.Region;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_ID;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_REGION;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;

public class DepartementDAO implements BaseDAO<Departement> {


    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Departement departement) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DEPARTEMENT_LIBELLE, departement.getDepartementLibelle());
        contentValues.put(KEY_DEPARTEMENT_REGION, departement.getDepartementRegion().getRegionId());
        return sqLiteDatabase.insert(TABLE_DEPARTEMENT, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Departement departement) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_DEPARTEMENT_LIBELLE, departement.getDepartementLibelle());
        cv.put(KEY_DEPARTEMENT_REGION, departement.getDepartementRegion().getRegionId());

        return sqLiteDatabase.update(TABLE_DEPARTEMENT, cv,
                " WHERE "+ KEY_DEPARTEMENT_ID +" = ? ",
                new String[]{String.valueOf(departement.getDepartementId())}) > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Departement departement) {
        return sqLiteDatabase.delete(TABLE_DEPARTEMENT,
                null,
                new String[]{String.valueOf(departement.getDepartementId())}) > 0;
    }

    @Override
    public Departement getById(SQLiteDatabase sqLiteDatabase, int itemId) {

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

    @Override
    public List<Departement> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Departement> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_DEPARTEMENT,
                new String[]{KEY_DEPARTEMENT_ID, KEY_DEPARTEMENT_LIBELLE, KEY_DEPARTEMENT_REGION},
                null,null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Departement departement;

                do {
                    departement = new Departement();

                    departement.setDepartementId(cursor.getLong(cursor.getColumnIndex(KEY_DEPARTEMENT_ID)));
                    departement.setDepartementLibelle(cursor.getString(cursor.getColumnIndex(KEY_DEPARTEMENT_LIBELLE)));

                    Region region = getRegionById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_DEPARTEMENT_REGION)));
                    departement.setDepartementRegion(region);

                    list.add(departement);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
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
