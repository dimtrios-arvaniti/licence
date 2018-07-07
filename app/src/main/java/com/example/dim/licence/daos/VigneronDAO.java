package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Vigneron;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_CODE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ID;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_PAYS;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_COMMENTAIRE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_DOMAINE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FAX;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FIXE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MAIL;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MOBILE;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;

public class VigneronDAO implements BaseDAO<Vigneron> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GEOLOC_PAYS, vigneron.getVigneronGeoloc().getGeolocPays());
        contentValues.put(KEY_GEOLOC_VILLE, vigneron.getVigneronGeoloc().getGeolocVille());
        contentValues.put(KEY_GEOLOC_CODE, vigneron.getVigneronGeoloc().getGeolocCode());
        contentValues.put(KEY_GEOLOC_ADRESSE, vigneron.getVigneronGeoloc().getGeolocAdresse());
        long geolocId = sqLiteDatabase.insert(TABLE_GEOLOCALISATION, null, contentValues);

        ContentValues vigneronCV = new ContentValues();
        vigneronCV.put(KEY_VIGNERON_LIBELLE, vigneron.getVigneronLibelle());
        vigneronCV.put(KEY_VIGNERON_DOMAINE, vigneron.getVigneronDomaine());
        vigneronCV.put(KEY_VIGNERON_GEOLOCALISATION, geolocId);
        vigneronCV.put(KEY_VIGNERON_FIXE, vigneron.getVigneronFixe());
        vigneronCV.put(KEY_VIGNERON_MOBILE, vigneron.getVigneronMobile());
        vigneronCV.put(KEY_VIGNERON_MAIL, vigneron.getVigneronMail());
        vigneronCV.put(KEY_VIGNERON_FAX, vigneron.getVigneronFax());
        vigneronCV.put(KEY_VIGNERON_COMMENTAIRE, vigneron.getVigneronComment());

        long id = sqLiteDatabase.insert(TABLE_VIGNERON, null, vigneronCV);
        return id;
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        ContentValues geolocCV = new ContentValues();
        geolocCV.put(KEY_GEOLOC_ID, vigneron.getVigneronGeoloc().getGeolocId());
        geolocCV.put(KEY_GEOLOC_PAYS, vigneron.getVigneronGeoloc().getGeolocPays());
        geolocCV.put(KEY_GEOLOC_VILLE, vigneron.getVigneronGeoloc().getGeolocVille());
        geolocCV.put(KEY_GEOLOC_CODE, vigneron.getVigneronGeoloc().getGeolocCode());
        geolocCV.put(KEY_GEOLOC_ADRESSE, vigneron.getVigneronGeoloc().getGeolocAdresse());
        int geolocId = sqLiteDatabase.update(TABLE_GEOLOCALISATION, geolocCV,
                KEY_GEOLOC_ID + " = ?",
                new String[]{String.valueOf(vigneron.getVigneronGeoloc().getGeolocId())});

        ContentValues vigneronCV = new ContentValues();
        vigneronCV.put(KEY_VIGNERON_ID, vigneron.getVigneronId());
        vigneronCV.put(KEY_VIGNERON_LIBELLE, vigneron.getVigneronLibelle());
        vigneronCV.put(KEY_VIGNERON_DOMAINE, vigneron.getVigneronDomaine());
        vigneronCV.put(KEY_VIGNERON_GEOLOCALISATION, geolocId);
        vigneronCV.put(KEY_VIGNERON_FIXE, vigneron.getVigneronFixe());
        vigneronCV.put(KEY_VIGNERON_MOBILE, vigneron.getVigneronMobile());
        vigneronCV.put(KEY_VIGNERON_MAIL, vigneron.getVigneronMail());
        vigneronCV.put(KEY_VIGNERON_FAX, vigneron.getVigneronFax());
        vigneronCV.put(KEY_VIGNERON_COMMENTAIRE, vigneron.getVigneronComment());

        long vigneronId = sqLiteDatabase.update(TABLE_VIGNERON, vigneronCV,
                KEY_VIGNERON_ID + " = ? ",
                new String[]{String.valueOf(vigneron.getVigneronId())});

        return vigneronId > 0;
    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        sqLiteDatabase.delete(TABLE_GEOLOCALISATION, KEY_GEOLOC_ID + " = ?", new String[]{String.valueOf(vigneron.getVigneronGeoloc().getGeolocId())});
        int rowsAffected = sqLiteDatabase.delete(TABLE_VIGNERON, KEY_VIGNERON_ID + " = ?", new String[]{String.valueOf(vigneron.getVigneronId())});
        return rowsAffected > 0;
    }


    @Override
    public Vigneron getById(SQLiteDatabase sqLiteDatabase, int itemId) {

        Cursor cursor = sqLiteDatabase.query(TABLE_VIGNERON,
                new String[]{KEY_VIGNERON_ID, KEY_VIGNERON_LIBELLE, KEY_VIGNERON_DOMAINE,
                KEY_VIGNERON_FIXE, KEY_VIGNERON_MOBILE, KEY_VIGNERON_MAIL, KEY_VIGNERON_FAX,
                KEY_VIGNERON_GEOLOCALISATION, KEY_VIGNERON_COMMENTAIRE},
                KEY_VIGNERON_ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        Vigneron vigneron = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int rowNbr = 0;
                Geolocalisation geoloc = null;

                do {
                    vigneron = new Vigneron();
                    vigneron.setVigneronId(cursor.getLong(cursor.getColumnIndex(KEY_VIGNERON_ID)));
                    vigneron.setVigneronLibelle(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_LIBELLE)));
                    vigneron.setVigneronDomaine(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_DOMAINE)));
                    vigneron.setVigneronFixe(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_FIXE)));
                    vigneron.setVigneronMobile(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_MOBILE)));
                    vigneron.setVigneronMail(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_MAIL)));
                    vigneron.setVigneronFax(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_FAX)));
                    vigneron.setVigneronComment(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_COMMENTAIRE)));

                    geoloc = getGeolocalisationById(sqLiteDatabase, cursor.getInt(cursor.getColumnIndex(KEY_VIGNERON_ID)));
                    vigneron.setVigneronGeoloc(geoloc);

                    Log.i(ARG_DEBUG, "getAll: " + vigneron.toString());
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return vigneron ;
    }


    @Override
    public List<Vigneron> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Vigneron> list = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_VIGNERON,
                new String[]{KEY_VIGNERON_ID, KEY_VIGNERON_LIBELLE, KEY_VIGNERON_DOMAINE,
                        KEY_VIGNERON_FIXE, KEY_VIGNERON_MOBILE, KEY_VIGNERON_MAIL, KEY_VIGNERON_FAX,
                        KEY_VIGNERON_GEOLOCALISATION, KEY_VIGNERON_COMMENTAIRE},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                int rowNbr = 0;
                Vigneron v = null;
                Geolocalisation geoloc = null;

                do {
                    v = new Vigneron();
                    v.setVigneronId(cursor.getLong(cursor.getColumnIndex(KEY_VIGNERON_ID)));
                    v.setVigneronLibelle(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_LIBELLE)));
                    v.setVigneronDomaine(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_DOMAINE)));
                    v.setVigneronFixe(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_FIXE)));
                    v.setVigneronMobile(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_MOBILE)));
                    v.setVigneronMail(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_MAIL)));
                    v.setVigneronFax(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_FAX)));
                    v.setVigneronComment(cursor.getString(cursor.getColumnIndex(KEY_VIGNERON_COMMENTAIRE)));

                    geoloc = getGeolocalisationById(sqLiteDatabase, cursor.getInt(cursor.getColumnIndex(KEY_VIGNERON_ID)));
                    v.setVigneronGeoloc(geoloc);
                    list.add(v);
                    Log.i(ARG_DEBUG, "getAll: " + v.toString());
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return list;
    }

    private Geolocalisation getGeolocalisationById(SQLiteDatabase sqLiteDatabase, int id) {
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

}
