package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Region;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Ville;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_ID;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_DEPARTEMENT_REGION;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE1;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE2;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ADRESSE3;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_COMPLEMENT;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_ID;
import static com.example.dim.licence.models.DbHelper.KEY_GEOLOC_VILLE;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_REGION_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_COMMENTAIRE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_DOMAINE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FAX;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_FIXE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MAIL;
import static com.example.dim.licence.models.DbHelper.KEY_VIGNERON_MOBILE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LATITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LONGITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ZIP_CODE;
import static com.example.dim.licence.models.DbHelper.TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_VILLE;

public class VigneronDAO implements BaseDAO<Vigneron> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Vigneron vigneron) {
        ContentValues contentValues = new ContentValues();
        if (vigneron.getVigneronGeoloc().getGeolocVille() != null) {
            contentValues.put(KEY_GEOLOC_VILLE, vigneron.getVigneronGeoloc().getGeolocVille().getVilleId());
        }
        contentValues.put(KEY_GEOLOC_ADRESSE1, vigneron.getVigneronGeoloc().getGeolocAdresse1());
        contentValues.put(KEY_GEOLOC_ADRESSE2, vigneron.getVigneronGeoloc().getGeolocAdresse2());
        contentValues.put(KEY_GEOLOC_ADRESSE3, vigneron.getVigneronGeoloc().getGeolocAdresse3());
        contentValues.put(KEY_GEOLOC_COMPLEMENT, vigneron.getVigneronGeoloc().getGeolocComplement());

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
        if (vigneron.getVigneronGeoloc().getGeolocVille() != null) {
            geolocCV.put(KEY_GEOLOC_VILLE, vigneron.getVigneronGeoloc().getGeolocVille().getVilleId());
        }
        geolocCV.put(KEY_GEOLOC_ADRESSE1, vigneron.getVigneronGeoloc().getGeolocAdresse1());
        geolocCV.put(KEY_GEOLOC_ADRESSE2, vigneron.getVigneronGeoloc().getGeolocAdresse2());
        geolocCV.put(KEY_GEOLOC_ADRESSE3, vigneron.getVigneronGeoloc().getGeolocAdresse3());
        geolocCV.put(KEY_GEOLOC_COMPLEMENT, vigneron.getVigneronGeoloc().getGeolocComplement());

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

        return vigneron;
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


    public Geolocalisation getGeolocalisationById(SQLiteDatabase sqLiteDatabase, int itemId) {
        Geolocalisation geolocalisation = null;

        Cursor cursor = sqLiteDatabase.query(TABLE_GEOLOCALISATION,
                new String[]{KEY_GEOLOC_ID, KEY_GEOLOC_VILLE, KEY_GEOLOC_ADRESSE1,
                        KEY_GEOLOC_ADRESSE2, KEY_GEOLOC_ADRESSE3, KEY_GEOLOC_COMPLEMENT},KEY_GEOLOC_ID+ " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    geolocalisation = new Geolocalisation();

                    geolocalisation.setGeolocId(cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_ID)));
                    geolocalisation.setGeolocAdresse1(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE1)));
                    geolocalisation.setGeolocAdresse2(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE2)));
                    geolocalisation.setGeolocAdresse3(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_ADRESSE3)));
                    geolocalisation.setGeolocComplement(cursor.getString(cursor.getColumnIndex(KEY_GEOLOC_COMPLEMENT)));

                    if (geolocalisation.getGeolocVille() != null) {
                        Ville ville = getVilleById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_GEOLOC_VILLE)));
                        geolocalisation.setGeolocVille(ville);
                    }

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return geolocalisation;

    }

    public Ville getVilleById(SQLiteDatabase sqLiteDatabase, long itemId) {

        Ville ville = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_VILLE,
                new String[]{KEY_VILLE_ID, KEY_VILLE_LIBELLE, KEY_VILLE_ZIP_CODE,
                        KEY_VILLE_LATITUDE, KEY_VILLE_LONGITUDE, KEY_VILLE_DEPARTEMENT},
                KEY_VILLE_ID + " = ?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    ville = new Ville();

                    ville.setVilleId(cursor.getLong(cursor.getColumnIndex(KEY_VILLE_ID)));
                    ville.setVilleLibelle(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LIBELLE)));
                    ville.setVilleZipCode(cursor.getString(cursor.getColumnIndex(KEY_VILLE_ZIP_CODE)));
                    ville.setVilleLatitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LATITUDE)));
                    ville.setVilleLongitude(cursor.getString(cursor.getColumnIndex(KEY_VILLE_LONGITUDE)));

                    Departement departement = getDepartementById(sqLiteDatabase, cursor.getLong(cursor.getColumnIndex(KEY_VILLE_DEPARTEMENT)));
                    ville.setVilleDepartement(departement);

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        return ville;
    }

    public Departement getDepartementById(SQLiteDatabase sqLiteDatabase, long itemId) {

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
