package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Action;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_ACTION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.TABLE_ACTION;

public class ActionDAO implements BaseDAO<Action> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Action action) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ACTION_LIBELLE, action.getActionLibelle());
        return sqLiteDatabase.insert(TABLE_ACTION, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Action action) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ACTION_LIBELLE, action.getActionLibelle());
        return sqLiteDatabase.update(TABLE_ACTION, cv,
                " WHERE " + KEY_ACTION_ID + " = ? ",
                new String[]{String.valueOf(action.getActionId())}) > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Action action) {
        return sqLiteDatabase.delete(TABLE_ACTION, null,
                new String[]{String.valueOf(action.getActionId())}) > 0;
    }
    @Override
    public Action getById(SQLiteDatabase sqLiteDatabase, int actionId) {

        Cursor cursor = sqLiteDatabase.query(TABLE_ACTION,
                new String[]{KEY_ACTION_ID, KEY_ACTION_LIBELLE},
                KEY_ACTION_ID + " = ?",
                new String[]{String.valueOf(actionId)},
                null, null, null);

        Action action = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    action = new Action();

                    action.setActionId(cursor.getLong(cursor.getColumnIndex(KEY_ACTION_ID)));
                    action.setActionLibelle(cursor.getString(cursor.getColumnIndex(KEY_ACTION_LIBELLE)));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return action;
    }

    @Override
    public List<Action> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Action> list = null;
       Cursor cursor = sqLiteDatabase.query(TABLE_ACTION,
                new String[]{KEY_ACTION_ID, KEY_ACTION_LIBELLE},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                int rowNbr = 0;
                Action action = null;

                do {
                    action = new Action();

                    action.setActionId(cursor.getLong(cursor.getColumnIndex(KEY_ACTION_ID)));
                    action.setActionLibelle(cursor.getString(cursor.getColumnIndex(KEY_ACTION_LIBELLE)));

                    list.add(action);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }
}
