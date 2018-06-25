package com.example.dim.licence.entities.daos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.AppUser;

import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_APIKEY;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_CAVE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_MAIL;
import static com.example.dim.licence.models.DbHelper.KEY_ID;
import static com.example.dim.licence.models.DbHelper.TABLE_APPUSER;

public class AppUserDAO {


    public long insertAppUser(SQLiteDatabase sqLiteDatabase, AppUser appUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_APPUSER_MAIL, appUser.getAppUserMail());
        contentValues.put(KEY_APPUSER_APIKEY, appUser.getAppUserApiKey());
        contentValues.put(KEY_APPUSER_CAVE_ID, appUser.getAppUserCaveId());
        return sqLiteDatabase.insert(TABLE_APPUSER, null, contentValues);
    }

    public int updateAppUser(SQLiteDatabase sqLiteDatabase, AppUser appUser) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_APPUSER_MAIL, appUser.getAppUserMail());
        cv.put(KEY_APPUSER_APIKEY, appUser.getAppUserApiKey());
        cv.put(KEY_APPUSER_CAVE_ID, appUser.getAppUserCaveId());
        return sqLiteDatabase.update(TABLE_APPUSER, cv, " WHERE "+KEY_ID+" = ? ",
                new String[]{String.valueOf(appUser.getAppUserId())});
    }

    public void deleteAppUser(SQLiteDatabase sqLiteDatabase, AppUser appUser) {
        sqLiteDatabase.delete(TABLE_APPUSER, null, new String[]{String.valueOf(appUser.getAppUserId())});
    }
}
