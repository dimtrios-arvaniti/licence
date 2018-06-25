package com.example.dim.licence.entities.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Vigneron;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_CODE;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_PAYS;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.KEY_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_COMMENTAIRE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_DOMAINE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FAX;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FIXE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MAIL;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MOBILE;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;

public class VigneronDAO {

    public long insertVigneron(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        ContentValues geolocCV = new ContentValues();
        geolocCV.put(KEY_GEOLOC_PAYS, vigneron.getVigneronGeoloc().getGeolocPays());
        geolocCV.put(KEY_GEOLOC_VILLE, vigneron.getVigneronGeoloc().getGeolocVille());
        geolocCV.put(KEY_GEOLOC_CODE, vigneron.getVigneronGeoloc().getGeolocCode());
        geolocCV.put(KEY_GEOLOC_ADRESSE, vigneron.getVigneronGeoloc().getGeolocAdresse());
        long geolocId = sqLiteDatabase.insert(TABLE_GEOLOCALISATION, null, geolocCV);

        ContentValues vigneronCV = new ContentValues();
        vigneronCV.put(KEY_VIGNERON_LIBELLE, vigneron.getVigneronLibelle());
        vigneronCV.put(KEY_VIGNERON_DOMAINE, vigneron.getVigneronDomaine());
        vigneronCV.put(KEY_VIGNERON_GEOLOCALISATION, geolocId);
        vigneronCV.put(KEY_VIGNERON_FIXE, vigneron.getVigneronFixe());
        vigneronCV.put(KEY_VIGNERON_MOBILE, vigneron.getVigneronMobile());
        vigneronCV.put(KEY_VIGNERON_MAIL, vigneron.getVigneronMail());
        vigneronCV.put(KEY_VIGNERON_FAX, vigneron.getVigneronFax());
        vigneronCV.put(KEY_VIGNERON_COMMENTAIRE, vigneron.getVigneronComment());
        return sqLiteDatabase.insert(TABLE_VIGNERON, null, vigneronCV);
    }

    public int updateVigneron(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        ContentValues geolocCV = new ContentValues();
        geolocCV.put(KEY_GEOLOC_PAYS, vigneron.getVigneronGeoloc().getGeolocPays());
        geolocCV.put(KEY_GEOLOC_VILLE, vigneron.getVigneronGeoloc().getGeolocVille());
        geolocCV.put(KEY_GEOLOC_CODE, vigneron.getVigneronGeoloc().getGeolocCode());
        geolocCV.put(KEY_GEOLOC_ADRESSE, vigneron.getVigneronGeoloc().getGeolocAdresse());
        sqLiteDatabase.update(TABLE_GEOLOCALISATION, geolocCV, " WHERE " + KEY_ID + " = ? ",
                new String[]{String.valueOf(vigneron.getVigneronGeoloc().getGeolocId())});

        ContentValues vigneronCV = new ContentValues();
        vigneronCV.put(KEY_VIGNERON_LIBELLE, vigneron.getVigneronLibelle());
        vigneronCV.put(KEY_VIGNERON_DOMAINE, vigneron.getVigneronDomaine());
        //vigneronCV.put(KEY_VIGNERON_GEOLOCALISATION, geolocId);
        vigneronCV.put(KEY_VIGNERON_FIXE, vigneron.getVigneronFixe());
        vigneronCV.put(KEY_VIGNERON_MOBILE, vigneron.getVigneronMobile());
        vigneronCV.put(KEY_VIGNERON_MAIL, vigneron.getVigneronMail());
        vigneronCV.put(KEY_VIGNERON_FAX, vigneron.getVigneronFax());
        vigneronCV.put(KEY_VIGNERON_COMMENTAIRE, vigneron.getVigneronComment());
        return sqLiteDatabase.update(TABLE_VIGNERON, vigneronCV, " WHERE " + KEY_ID + " = ? ",
                new String[]{String.valueOf(vigneron.getVigneronId())});
    }

    public int deleteVigneron(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        sqLiteDatabase.delete(TABLE_GEOLOCALISATION, null, new String[]{String.valueOf(vigneron.getVigneronGeoloc().getGeolocId())});
        return sqLiteDatabase.delete(TABLE_VIGNERON, null, new String[]{String.valueOf(vigneron.getVigneronId())});
    }

    private Geolocalisation getGeolocalisationById(SQLiteDatabase sqLiteDatabase, int id) {
        Geolocalisation geoloc = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_GEOLOCALISATION + " WHERE " + KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            geoloc = new Geolocalisation();
            geoloc.setGeolocId(c.getInt(c.getColumnIndex(KEY_ID)));
            geoloc.setGeolocPays(c.getString(c.getColumnIndex(KEY_GEOLOC_PAYS)));
            geoloc.setGeolocVille(c.getString(c.getColumnIndex(KEY_GEOLOC_VILLE)));
            geoloc.setGeolocCode(c.getString(c.getColumnIndex(KEY_GEOLOC_CODE)));
            geoloc.setGeolocAdresse(c.getString(c.getColumnIndex(KEY_GEOLOC_ADRESSE)));
        }

        return geoloc;
    }

    public List<Vigneron> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Vigneron> list = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_VIGNERON+" ;", null);

        if (c.moveToFirst()) {
            list = new ArrayList<>();
            int rowNbr = 0;
            Vigneron v = null;
            Geolocalisation geoloc = null;

            do {
                v = new Vigneron();
                v.setVigneronId(c.getInt(c.getColumnIndex(KEY_ID)));
                v.setVigneronLibelle(c.getString(c.getColumnIndex(KEY_VIGNERON_LIBELLE)));
                v.setVigneronDomaine(c.getString(c.getColumnIndex(KEY_VIGNERON_DOMAINE)));
                geoloc = getGeolocalisationById(sqLiteDatabase, c.getInt(c.getColumnIndex(KEY_VIGNERON_GEOLOCALISATION)));
                if (geoloc == null) {
                    Log.i("DEBUG_VIGNERONDAO", "getAll: !!!!!!!!!  GEOLOC IS NULL  !!!!!!!!!");
                }
                v.setVigneronGeoloc(geoloc);
                v.setVigneronFixe(c.getString(c.getColumnIndex(KEY_VIGNERON_FIXE)));
                v.setVigneronMobile(c.getString(c.getColumnIndex(KEY_VIGNERON_MOBILE)));
                v.setVigneronMail(c.getString(c.getColumnIndex(KEY_VIGNERON_MAIL)));
                v.setVigneronFax(c.getString(c.getColumnIndex(KEY_VIGNERON_FAX)));
                v.setVigneronComment(c.getString(c.getColumnIndex(KEY_VIGNERON_COMMENTAIRE)));
                list.add(v);
            } while (c.moveToNext());
        }

        return list;
    }
}
