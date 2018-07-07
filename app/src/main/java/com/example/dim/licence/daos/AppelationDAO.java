package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Appelation;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.TABLE_APPELATION;

public class AppelationDAO implements BaseDAO<Appelation> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Appelation action) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_APPELATION_LIBELLE, action.getAppelationLibelle());
        return sqLiteDatabase.insert(TABLE_APPELATION, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Appelation action) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_APPELATION_LIBELLE, action.getAppelationLibelle());

        return sqLiteDatabase.update(TABLE_APPELATION, cv,
                " WHERE "+ KEY_APPELATION_ID +" = ? ",
                new String[]{String.valueOf(action.getAppelationId())}) > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Appelation action) {
        return sqLiteDatabase.delete(TABLE_APPELATION,
                null,
                new String[]{String.valueOf(action.getAppelationId())}) > 0;
    }

    @Override
    public Appelation getById(SQLiteDatabase sqLiteDatabase, int itemId) {

        Appelation appelation = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_APPELATION,
                new String[]{KEY_APPELATION_ID, KEY_APPELATION_LIBELLE},
                KEY_APPELATION_ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int rowNbr = 0;

                do {
                    appelation = new Appelation();

                    appelation.setAppelationId(cursor.getLong(cursor.getColumnIndex(KEY_APPELATION_ID)));
                    appelation.setAppelationLibelle(cursor.getString(cursor.getColumnIndex(KEY_APPELATION_LIBELLE)));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return appelation;
    }

    @Override
    public List<Appelation> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Appelation> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_APPELATION,
                new String[]{KEY_APPELATION_ID, KEY_APPELATION_LIBELLE},
                null,null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                int rowNbr = 0;
                Appelation appelation = null;

                do {
                    appelation = new Appelation();

                    appelation.setAppelationId(cursor.getLong(cursor.getColumnIndex(KEY_APPELATION_ID)));
                    appelation.setAppelationLibelle(cursor.getString(cursor.getColumnIndex(KEY_APPELATION_LIBELLE)));

                    list.add(appelation);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }

}
