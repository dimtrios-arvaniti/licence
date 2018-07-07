package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.AppUser;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Vin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_ACTION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_APIKEY;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_CAVE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_MAIL;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_MASTER;
import static com.example.dim.licence.models.DbHelper.KEY_APPUSER_PWD;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_FAVORIS;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_QUANTITE;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_VIN;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_CODE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ID;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_PAYS;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.KEY_TYPEVIN_ID;
import static com.example.dim.licence.models.DbHelper.KEY_TYPEVIN_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_COMMENTAIRE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_DOMAINE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FAX;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FIXE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MAIL;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MOBILE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_ANNEE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_ANNEEMAX;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_APPELATION;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_COMMENTAIRE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_PRIX;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_TYPE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_ACTION;
import static com.example.dim.licence.models.DbHelper.TABLE_APPELATION;
import static com.example.dim.licence.models.DbHelper.TABLE_APPUSER;
import static com.example.dim.licence.models.DbHelper.TABLE_CAVE;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_VIN;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;


public class AppUserDAO implements BaseDAO<AppUser> {


    /**
     * Insert
     *
     * @param sqLiteDatabase
     * @param appUser
     * @return the new inserted id
     */
    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, AppUser appUser) {
        ContentValues caveContentValues = new ContentValues();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_APPUSER_MAIL, appUser.getAppUserMail());
        contentValues.put(KEY_APPUSER_MASTER, 1);
        contentValues.put(KEY_APPUSER_APIKEY, appUser.getAppUserApiKey());
        contentValues.put(KEY_APPUSER_CAVE_ID, appUser.getAppUserCaveId());
        contentValues.put(KEY_APPUSER_PWD, appUser.getAppUserPwd());

        return sqLiteDatabase.insert(TABLE_APPUSER, null, contentValues);
    }

    /**
     * Update
     * <p>
     * As SqliteDatabase update method returns the number of rows affected by the statement
     * we simply return if this number is strictly superior to 0 or not
     *
     * @param sqLiteDatabase SQLiteDatabase - database on which to perform the statement
     * @param appUser        AppUser - entity to update
     * @return true if 1 or more rows where affected by the statement
     */
    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, AppUser appUser) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_APPUSER_MAIL, appUser.getAppUserMail());
        cv.put(KEY_APPUSER_CAVE_ID, appUser.getAppUserCaveId());
        cv.put(KEY_APPUSER_MASTER, appUser.isAppUserMaster());
        cv.put(KEY_APPUSER_PWD, appUser.getAppUserPwd());

        return sqLiteDatabase.update(TABLE_APPUSER, cv, " WHERE " + KEY_APPUSER_ID + " = ? ",
                new String[]{String.valueOf(appUser.getAppUserId())}) > 0;
    }


    /**
     * Delete
     * <p>
     * As SqliteDatabase delete method returns the number of rows affected by the statement
     * we simply return if this number is strictly superior to 0 or not
     *
     * @param sqLiteDatabase SQLiteDatabase - database on which to perform the statement
     * @param appUser        AppUser - entity to remove
     * @return true if 1 or more rows where affected by the statement
     */
    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, AppUser appUser) {
        return sqLiteDatabase.delete(TABLE_APPUSER,
                null,
                new String[]{String.valueOf(appUser.getAppUserId())}) > 0;
    }

    @Override
    public AppUser getById(SQLiteDatabase sqLiteDatabase, int itemId) {
        AppUser appUser = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_APPUSER
                + " WHERE " + KEY_APPUSER_ID + " = ? ;", new String[]{String.valueOf(itemId)});


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    appUser = new AppUser();

                    appUser.setAppUserId(cursor.getLong(cursor.getColumnIndex(KEY_APPUSER_ID)));
                    appUser.setAppUserMail(cursor.getString(cursor.getColumnIndex(KEY_APPUSER_MAIL)));
                    appUser.setAppUserApiKey(cursor.getString(cursor.getColumnIndex(KEY_APPUSER_APIKEY)));
                    appUser.setAppUserMaster(cursor.getInt(cursor.getColumnIndex(KEY_APPUSER_MASTER)) != 0);
                    appUser.setAppUserPwd(cursor.getString(cursor.getColumnIndex(KEY_APPUSER_PWD)));
                    appUser.setAppUserCaveId(cursor.getLong(cursor.getColumnIndex(KEY_APPUSER_CAVE_ID)));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return appUser;
    }

    @Override
    public List<AppUser> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<AppUser> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_ACTION,
                new String[]{KEY_ACTION_ID, KEY_ACTION_LIBELLE},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                AppUser appUser = null;

                do {
                    appUser = new AppUser();

                    appUser.setAppUserId(cursor.getLong(cursor.getColumnIndex(KEY_APPUSER_ID)));
                    appUser.setAppUserMail(cursor.getString(cursor.getColumnIndex(KEY_APPUSER_MAIL)));
                    appUser.setAppUserApiKey(cursor.getString(cursor.getColumnIndex(KEY_APPUSER_APIKEY)));
                    appUser.setAppUserMaster(cursor.getInt(cursor.getColumnIndex(KEY_APPUSER_MASTER)) != 0);
                    appUser.setAppUserPwd(cursor.getString(cursor.getColumnIndex(KEY_APPUSER_PWD)));
                    appUser.setAppUserCaveId(cursor.getLong(cursor.getColumnIndex(KEY_APPUSER_CAVE_ID)));

                    list.add(appUser);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return list;
    }

    public long getUserIdByCredentials(SQLiteDatabase sqLiteDatabase, String mail, String pwd) {
        Cursor cursor = sqLiteDatabase.query(TABLE_APPUSER, new String[]{KEY_APPUSER_ID},
                KEY_APPUSER_MAIL + "= ? AND " + KEY_APPUSER_PWD + "= ?",
                new String[]{mail, pwd}, null, null, null);
        long id = -1;
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    id = cursor.getLong(cursor.getColumnIndex(KEY_APPUSER_ID));
                    // careful to order '+=' , not the same as '=+' !
                    count += 1;
                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        // avoid multiple users return, should never happen though
        if (count != 1) {
            return -1;
        }

        return id;
    }


    private Cave getCaveById(SQLiteDatabase sqLiteDatabase, int itemId) {
        Cave cave = null;

        Cursor cursor = sqLiteDatabase.query(TABLE_CAVE,
                new String[]{KEY_CAVE_ID, KEY_CAVE_VIN, KEY_CAVE_FAVORIS, KEY_CAVE_QUANTITE},
                KEY_CAVE_ID + " = ?",
                new String[]{String.valueOf(itemId)}, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    cave = new Cave();

                    cave.setCaveId(cursor.getLong(cursor.getColumnIndex(KEY_CAVE_ID)));

                    Vin vin = getVinById(sqLiteDatabase, cursor.getInt(cursor.getColumnIndex(KEY_CAVE_VIN)));
                    cave.setCaveVin(vin);

                    cave.setCaveFavoris(cursor.getInt(cursor.getColumnIndex(KEY_CAVE_FAVORIS)) != 0);
                    cave.setCaveQuantite(cursor.getInt(cursor.getColumnIndex(KEY_CAVE_QUANTITE)));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return cave;
    }


    private Appelation getAppelationById(SQLiteDatabase sqLiteDatabase, long id) {
        Appelation appelation = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_APPELATION + " WHERE " + KEY_APPELATION_ID + " = ?", new String[]{String.valueOf(id)});

        if (c != null) {
            c.moveToFirst();

            appelation = new Appelation();
            appelation.setAppelationId(c.getLong(c.getColumnIndex(KEY_APPELATION_ID)));
            appelation.setAppelationLibelle(c.getString(c.getColumnIndex(KEY_APPELATION_LIBELLE)));

            c.close();
        }
        return appelation;
    }


    private Vin getVinById(SQLiteDatabase sqLiteDatabase, int itemId) {
        Vin vin = null;

        Cursor c = sqLiteDatabase.
                query(TABLE_VIN, new String[]{" * "},
                        KEY_VIN_ID + " = ?",
                        new String[]{String.valueOf(itemId)},
                        null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {

                do {
                    vin = new Vin();
                    vin.setVinId(c.getLong(c.getColumnIndex(KEY_VIN_ID)));
                    vin.setVinLibelle(c.getString(c.getColumnIndex(KEY_VIN_LIBELLE)));
                    vin.setVinCommentaire(c.getString(c.getColumnIndex(KEY_VIN_COMMENTAIRE)));
                    vin.setVinPrix(c.getDouble(c.getColumnIndex(KEY_VIN_PRIX)));
                    try {
                        vin.setVinAnnee(vinDateFormat.parse(c.getString(c.getColumnIndex(KEY_VIN_ANNEE))));
                        vin.setVinAnneeMax(vinDateFormat.parse(c.getString(c.getColumnIndex(KEY_VIN_ANNEEMAX))));
                    } catch (ParseException pe) {
                        Log.e(ARG_DEBUG, "getAll: ", pe);
                    }

                    Appelation appelation = getAppelationById(sqLiteDatabase, c.getLong(c.getColumnIndex(KEY_VIN_APPELATION)));
                    vin.setVinAppelation(appelation);

                    TypeVin typeVin = getTypeVinById(sqLiteDatabase, c.getLong(c.getColumnIndex(KEY_VIN_TYPE)));
                    vin.setVinType(typeVin);

                    Vigneron vigneron = getVigneronById(sqLiteDatabase, c.getLong(c.getColumnIndex(KEY_VIN_VIGNERON)));
                    vin.setVinVigneron(vigneron);

                } while (c.moveToNext());
            }

            c.close();
        }

        return vin;
    }


    private TypeVin getTypeVinById(SQLiteDatabase sqLiteDatabase, long id) {
        TypeVin typeVin = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TYPEVIN + " WHERE " + KEY_TYPEVIN_ID + " = ?", new String[]{String.valueOf(id)});

        if (c != null) {
            c.moveToFirst();

            typeVin = new TypeVin();
            typeVin.setTypeVinId(c.getLong(c.getColumnIndex(KEY_TYPEVIN_ID)));
            typeVin.setTypeVinLibelle(c.getString(c.getColumnIndex(KEY_TYPEVIN_LIBELLE)));

            c.close();
        }
        return typeVin;
    }


    private Geolocalisation getGeolocalisationById(SQLiteDatabase sqLiteDatabase, long id) {
        Geolocalisation geoloc = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_GEOLOCALISATION + " WHERE " + KEY_GEOLOC_ID + " = ?", new String[]{String.valueOf(id)});

        if (c != null) {
            c.moveToFirst();

            geoloc = new Geolocalisation();
            geoloc.setGeolocId(c.getLong(c.getColumnIndex(KEY_GEOLOC_ID)));
            geoloc.setGeolocPays(c.getString(c.getColumnIndex(KEY_GEOLOC_PAYS)));
            geoloc.setGeolocVille(c.getString(c.getColumnIndex(KEY_GEOLOC_VILLE)));
            geoloc.setGeolocCode(c.getString(c.getColumnIndex(KEY_GEOLOC_CODE)));
            geoloc.setGeolocAdresse(c.getString(c.getColumnIndex(KEY_GEOLOC_ADRESSE)));

            c.close();
        }
        return geoloc;
    }


    private Vigneron getVigneronById(SQLiteDatabase sqLiteDatabase, long id) {
        Vigneron vigneron = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_VIGNERON + " WHERE " + KEY_VIGNERON_ID + " = ?", new String[]{String.valueOf(id)});

        if (c != null) {
            c.moveToFirst();

            vigneron = new Vigneron();
            vigneron.setVigneronId(c.getLong(c.getColumnIndex(KEY_VIGNERON_ID)));
            vigneron.setVigneronComment(c.getString(c.getColumnIndex(KEY_VIGNERON_COMMENTAIRE)));

            Geolocalisation geoloc = getGeolocalisationById(sqLiteDatabase,
                    c.getLong(c.getColumnIndex(KEY_VIGNERON_GEOLOCALISATION)));
            vigneron.setVigneronGeoloc(geoloc);

            vigneron.setVigneronMail(c.getString(c.getColumnIndex(KEY_VIGNERON_MAIL)));
            vigneron.setVigneronFax(c.getString(c.getColumnIndex(KEY_VIGNERON_FAX)));
            vigneron.setVigneronFixe(c.getString(c.getColumnIndex(KEY_VIGNERON_FIXE)));
            vigneron.setVigneronMobile(c.getString(c.getColumnIndex(KEY_VIGNERON_MOBILE)));
            vigneron.setVigneronDomaine(c.getString(c.getColumnIndex(KEY_VIGNERON_DOMAINE)));
            vigneron.setVigneronLibelle(c.getString(c.getColumnIndex(KEY_VIGNERON_LIBELLE)));

            c.close();
        }
        return vigneron;
    }

}
