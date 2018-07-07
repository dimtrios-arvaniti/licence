package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.TypeVin;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_TYPEVIN_ID;
import static com.example.dim.licence.models.DbHelper.KEY_TYPEVIN_LIBELLE;
import static com.example.dim.licence.models.DbHelper.TABLE_TYPEVIN;

public class TypeVinDAO implements BaseDAO<TypeVin> {


    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, TypeVin typeVin) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TYPEVIN_LIBELLE, typeVin.getTypeVinLibelle());
        return sqLiteDatabase.insert(TABLE_TYPEVIN, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, TypeVin typeVin) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_TYPEVIN_LIBELLE, typeVin.getTypeVinLibelle());
        return sqLiteDatabase.update(TABLE_TYPEVIN, cv,
                " WHERE " + KEY_TYPEVIN_ID + " = ? ",
                new String[]{String.valueOf(typeVin.getTypeVinId())}) > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, TypeVin typeVin) {
        return sqLiteDatabase.delete(TABLE_TYPEVIN, null,
                new String[]{String.valueOf(typeVin.getTypeVinId())}) > 0;
    }

    @Override
    public TypeVin getById(SQLiteDatabase sqLiteDatabase, int itemId) {
        TypeVin typeVin = null;

        Cursor c = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + TABLE_TYPEVIN
                + " WHERE "+KEY_TYPEVIN_ID + " = ? ;",
                new String[]{String.valueOf(itemId)});


        if (c != null) {
            if (c.moveToFirst()) {

                do {
                    typeVin = new TypeVin();

                    typeVin.setTypeVinId(c.getLong(c.getColumnIndex(KEY_TYPEVIN_ID)));
                    typeVin.setTypeVinLibelle(c.getString(c.getColumnIndex(KEY_TYPEVIN_LIBELLE)));

                } while (c.moveToNext());
            }

            c.close();
        }
        return typeVin;
    }

    @Override
    public List<TypeVin> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<TypeVin> list = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TYPEVIN + ";", null);

        if (c != null) {
            if (c.moveToFirst()) {
                list = new ArrayList<>();
                TypeVin typeVin = null;

                do {
                    typeVin = new TypeVin();

                    typeVin.setTypeVinId(c.getLong(c.getColumnIndex(KEY_TYPEVIN_ID)));
                    typeVin.setTypeVinLibelle(c.getString(c.getColumnIndex(KEY_TYPEVIN_LIBELLE)));

                    list.add(typeVin);

                } while (c.moveToNext());
            }

            c.close();
        }
        return list;
    }

}
