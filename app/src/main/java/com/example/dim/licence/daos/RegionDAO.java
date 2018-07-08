package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Region;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_REGION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;

public class RegionDAO implements BaseDAO<Region> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Region region) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_REGION_LIBELLE, region.getRegionLibelle());
        return sqLiteDatabase.insert(TABLE_REGION, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Region region) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_REGION_LIBELLE, region.getRegionLibelle());

        return sqLiteDatabase.update(TABLE_REGION, cv,
                " WHERE "+ KEY_REGION_ID +" = ? ",
                new String[]{String.valueOf(region.getRegionId())}) > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Region region) {
        return sqLiteDatabase.delete(TABLE_REGION,
                null,
                new String[]{String.valueOf(region.getRegionId())}) > 0;
    }

    @Override
    public Region getById(SQLiteDatabase sqLiteDatabase, int itemId) {

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

    @Override
    public List<Region> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Region> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_REGION,
                new String[]{KEY_REGION_ID, KEY_REGION_LIBELLE},
                null,null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Region region;

                do {
                    region = new Region();
                    region.setRegionId(cursor.getLong(cursor.getColumnIndex(KEY_REGION_ID)));
                    region.setRegionLibelle(cursor.getString(cursor.getColumnIndex(KEY_REGION_LIBELLE)));

                    list.add(region);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }
}
