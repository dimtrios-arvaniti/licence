package com.example.dim.licence.entities.daos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Action;

import static com.example.dim.licence.models.DbHelper.KEY_ACTION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_ID;
import static com.example.dim.licence.models.DbHelper.TABLE_ACTION;

public class ActionDAO {

    public long insertAction(SQLiteDatabase sqLiteDatabase, Action action) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ACTION_LIBELLE, action.getActionLibelle());
        return sqLiteDatabase.insert(TABLE_ACTION, null, contentValues);
    }

    public int updateAction(SQLiteDatabase sqLiteDatabase, Action action) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ACTION_LIBELLE, action.getActionLibelle());
        return sqLiteDatabase.update(TABLE_ACTION, cv, " WHERE "+KEY_ID+" = ? ",
                new String[]{String.valueOf(action.getActionId())});
    }

    public void deleteAction(SQLiteDatabase sqLiteDatabase, Action action) {
        sqLiteDatabase.delete(TABLE_ACTION, null, new String[]{String.valueOf(action.getActionId())});
    }


}
