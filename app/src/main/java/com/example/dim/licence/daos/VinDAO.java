package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Region;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Ville;
import com.example.dim.licence.entities.Vin;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_LIBELLE;
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
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LATITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_LONGITUDE;
import static com.example.dim.licence.models.DbHelper.KEY_VILLE_ZIP_CODE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_ANNEE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_ANNEEMAX;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_APPELATION;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_COMMENTAIRE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_ID;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_IMAGE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_PRIX;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_TYPE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_APPELATION;
import static com.example.dim.licence.models.DbHelper.TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;
import static com.example.dim.licence.models.DbHelper.TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_VILLE;
import static com.example.dim.licence.models.DbHelper.TABLE_VIN;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;

public class VinDAO implements BaseDAO<Vin> {

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Vin item) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VIN_LIBELLE, item.getVinLibelle());
        contentValues.put(KEY_VIN_IMAGE, item.getVinImage());
        if (item.getVinAnnee() != null) {
            contentValues.put(KEY_VIN_ANNEE, vinDateFormat.format(item.getVinAnnee()).toUpperCase());
        }
        if (item.getVinAnneeMax() != null) {
            contentValues.put(KEY_VIN_ANNEEMAX, vinDateFormat.format(item.getVinAnneeMax()).toUpperCase());
        }
        contentValues.put(KEY_VIN_COMMENTAIRE, item.getVinCommentaire());

        // handle dictionary data APPELATION
        if (item.getVinAppelation().getAppelationId() != null) {
            contentValues.put(KEY_VIN_APPELATION, item.getVinAppelation().getAppelationId());
        }
        // handle dictionary data TYPEVIN
        if (item.getVinType().getTypeVinId() != null) {
            contentValues.put(KEY_VIN_TYPE, item.getVinType().getTypeVinId());
        }
        // be sure that vigneron id is set if already existing, won't be saved otherwise
        if (item.getVinVigneron().getVigneronId() != null) {

            contentValues.put(KEY_VIN_VIGNERON, item.getVinVigneron().getVigneronId());
        }

        contentValues.put(KEY_VIN_PRIX, item.getVinPrix());

        return sqLiteDatabase.insert(TABLE_VIN, null, contentValues);
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Vin vin) {
        ContentValues vinCv = new ContentValues();
        vinCv.put(KEY_VIN_IMAGE, vin.getVinImage());

        if (vin.getVinAnnee() != null) {
            vinCv.put(KEY_VIN_ANNEE, vinDateFormat.format(vin.getVinAnnee()));
        }

        if (vin.getVinAnneeMax() != null) {
            vinCv.put(KEY_VIN_ANNEEMAX, vinDateFormat.format(vin.getVinAnneeMax()));
        }

        vinCv.put(KEY_VIN_COMMENTAIRE, vin.getVinCommentaire());
        vinCv.put(KEY_VIN_APPELATION, vin.getVinAppelation().getAppelationId());
        vinCv.put(KEY_VIN_LIBELLE, vin.getVinLibelle());
        vinCv.put(KEY_VIN_PRIX, vin.getVinPrix());

        vinCv.put(KEY_VIN_TYPE, vin.getVinType().getTypeVinId());
        vinCv.put(KEY_VIN_VIGNERON, vin.getVinVigneron().getVigneronId());

        return sqLiteDatabase.update(TABLE_VIN, vinCv,
                KEY_VIN_ID + " = ?",
                new String[]{String.valueOf(vin.getVinId())}) > 0;
    }

    public boolean delete(SQLiteDatabase sqLiteDatabase, Vin vin) {
        //sqLiteDatabase.delete(TABLE_GEOLOCALISATION, KEY_GEOLOC_ID + " = ?", new String[]{String.valueOf(vigneron.getVigneronGeoloc().getGeolocId())});
        int rowsAffected = sqLiteDatabase.delete(TABLE_VIN, KEY_VIN_ID + " = ?",
                new String[]{String.valueOf(vin.getVinId())});
        return rowsAffected > 0;
    }

    @Override
    public Vin getById(SQLiteDatabase sqLiteDatabase, int itemId) {
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
                    vin.setVinImage(c.getString(c.getColumnIndex(KEY_VIN_IMAGE)));
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

    @Override
    public List<Vin> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Vin> list = null;
        Cursor c = sqLiteDatabase.
                query(TABLE_VIN, new String[]{" * "}, null, null,
                        null, null, KEY_VIN_ID);

        if (c != null) {
            if (c.moveToFirst()) {
                list = new ArrayList<>();
                Vin vin = null;

                do {
                    vin = new Vin();
                    vin.setVinId(c.getLong(c.getColumnIndex(KEY_VIN_ID)));
                    vin.setVinImage(c.getString(c.getColumnIndex(KEY_VIN_IMAGE)));
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

                    list.add(vin);
                    Log.i(ARG_DEBUG, "getAll: " + vin.toString());
                } while (c.moveToNext());
            }

            c.close();
        }

        return list;
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

    private Vigneron getVigneronById(SQLiteDatabase sqLiteDatabase, long itemId) {

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


    private Geolocalisation getGeolocalisationById(SQLiteDatabase sqLiteDatabase, long itemId) {
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

    private Ville getVilleById(SQLiteDatabase sqLiteDatabase, long itemId) {

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

    private Departement getDepartementById(SQLiteDatabase sqLiteDatabase, long itemId) {

        Departement departement = null;
        Cursor cursor = sqLiteDatabase.query(TABLE_DEPARTEMENT,
                new String[]{KEY_DEPARTEMENT_ID, KEY_DEPARTEMENT_LIBELLE, KEY_DEPARTEMENT_REGION},
                KEY_REGION_ID + " = ?",
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

    private Region getRegionById(SQLiteDatabase sqLiteDatabase, long itemId) {

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
