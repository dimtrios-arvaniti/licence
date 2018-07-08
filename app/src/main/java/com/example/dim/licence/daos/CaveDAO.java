package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Cave;
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
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_FAVORIS;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_QUANTITE;
import static com.example.dim.licence.models.DbHelper.KEY_CAVE_VIN;
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
import static com.example.dim.licence.models.DbHelper.TABLE_CAVE;
import static com.example.dim.licence.models.DbHelper.TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_REGION;
import static com.example.dim.licence.models.DbHelper.TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_VILLE;
import static com.example.dim.licence.models.DbHelper.TABLE_VIN;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;

public class CaveDAO implements BaseDAO<Cave> {

    public long oldInsert(SQLiteDatabase sqLiteDatabase, Cave item, int vinId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CAVE_FAVORIS, item.isCaveFavoris());
        contentValues.put(KEY_CAVE_QUANTITE, item.getCaveQuantite());
        contentValues.put(KEY_CAVE_VIN, vinId);

        long id = sqLiteDatabase.insert(TABLE_CAVE, null, contentValues);
        return id;
    }


    @Override
    public long insert(SQLiteDatabase sqLiteDatabase, Cave item) {

        ContentValues vinContentValues = new ContentValues();
        vinContentValues.put(KEY_VIN_LIBELLE, item.getCaveVin().getVinLibelle());

        // handle dictionary data APPELATION
        if (item.getCaveVin().getVinAppelation() != null) {
            vinContentValues.put(KEY_VIN_APPELATION, item.getCaveVin().getVinAppelation().getAppelationId());
        }
        // handle dictionary data TYPEVIN
        if (item.getCaveVin().getVinType().getTypeVinId() != null) {
            vinContentValues.put(KEY_VIN_TYPE, item.getCaveVin().getVinType().getTypeVinId());
        }
        // be sure that vigneron id is set if already existing, won't be saved otherwise
        if (item.getCaveVin().getVinVigneron() != null) {
            vinContentValues.put(KEY_VIN_VIGNERON, item.getCaveVin().getVinVigneron().getVigneronId());
        }

        vinContentValues.put(KEY_VIN_PRIX, item.getCaveVin().getVinPrix());
        vinContentValues.put(KEY_VIN_COMMENTAIRE, item.getCaveVin().getVinCommentaire());

        if (item.getCaveVin().getVinAnnee() != null) {
            vinContentValues.put(KEY_VIN_ANNEE, vinDateFormat.format(item.getCaveVin().getVinAnnee()));
        }
        if (item.getCaveVin().getVinAnneeMax() != null) {
            vinContentValues.put(KEY_VIN_ANNEEMAX, vinDateFormat.format(item.getCaveVin().getVinAnneeMax()));
        }


        long vinId = sqLiteDatabase.insert(TABLE_VIN, null, vinContentValues);
        Log.i(ARG_DEBUG, "insert: new id = " + vinId);

//        Vin v = getVinById(sqLiteDatabase, Integer.valueOf(String.valueOf(vinId)));
//        Log.i(ARG_DEBUG, "insert: retrieved id= "+v.getVinId());

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CAVE_FAVORIS, item.isCaveFavoris());
        contentValues.put(KEY_CAVE_QUANTITE, item.getCaveQuantite().intValue());
        contentValues.put(KEY_CAVE_VIN, vinId);

        long id = sqLiteDatabase.insert(TABLE_CAVE, null, contentValues);
        return id;
    }

    @Override
    public boolean update(SQLiteDatabase sqLiteDatabase, Cave item) {
        ContentValues vinContentValues = new ContentValues();

        vinContentValues.put(KEY_VIN_ID, item.getCaveVin().getVinId());
        vinContentValues.put(KEY_VIN_LIBELLE, item.getCaveVin().getVinLibelle());

        // handle dictionary data APPELATION
        if (item.getCaveVin().getVinAppelation().getAppelationId() != null) {
            vinContentValues.put(KEY_VIN_APPELATION, item.getCaveVin().getVinAppelation().getAppelationId());
        }
        // handle dictionary data TYPEVIN
        if (item.getCaveVin().getVinType().getTypeVinId() != null) {
            vinContentValues.put(KEY_VIN_TYPE, item.getCaveVin().getVinType().getTypeVinId());
        }
        // be sure that vigneron id is set if already existing, won't be saved otherwise
        if (item.getCaveVin().getVinVigneron().getVigneronId() != null) {
            vinContentValues.put(KEY_VIN_VIGNERON, item.getCaveVin().getVinVigneron().getVigneronId());
        }

        vinContentValues.put(KEY_VIN_IMAGE, item.getCaveVin().getVinImage());
        vinContentValues.put(KEY_VIN_PRIX, item.getCaveVin().getVinPrix());
        vinContentValues.put(KEY_VIN_COMMENTAIRE, item.getCaveVin().getVinCommentaire());
        if (item.getCaveVin().getVinAnnee() != null) {
            vinContentValues.put(KEY_VIN_ANNEE, vinDateFormat.format(item.getCaveVin().getVinAnnee()));
        }

        if (item.getCaveVin().getVinAnneeMax() != null) {
            vinContentValues.put(KEY_VIN_ANNEEMAX, vinDateFormat.format(item.getCaveVin().getVinAnneeMax()));
        }

        int i = sqLiteDatabase.update(TABLE_VIN, vinContentValues,
                KEY_VIN_ID + " = ?",
                new String[]{String.valueOf(item.getCaveVin().getVinId())});

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CAVE_ID, item.getCaveId());
        contentValues.put(KEY_CAVE_FAVORIS, item.isCaveFavoris());
        Log.i(ARG_DEBUG, "update: UPDATE CAVE QTY = "+item.getCaveQuantite());
        contentValues.put(KEY_CAVE_QUANTITE, item.getCaveQuantite());
        contentValues.put(KEY_CAVE_VIN, i);

        long ri =  sqLiteDatabase.update(TABLE_CAVE, contentValues,
                KEY_CAVE_ID + "= ?",
                new String[]{String.valueOf(item.getCaveId())});

        return ri  > 0;

    }

    @Override
    public boolean delete(SQLiteDatabase sqLiteDatabase, Cave item) {
        return sqLiteDatabase.delete(TABLE_CAVE, KEY_CAVE_ID + "= ?",
                new String[]{String.valueOf(item.getCaveId())}) > 0;
    }

    @Override
    public Cave getById(SQLiteDatabase sqLiteDatabase, int itemId) {
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

    @Override
    public List<Cave> getAll(SQLiteDatabase sqLiteDatabase) {
        List<Cave> list = null;

        Cursor cursor = sqLiteDatabase.query(TABLE_CAVE,
                new String[]{KEY_CAVE_ID, KEY_CAVE_VIN, KEY_CAVE_FAVORIS, KEY_CAVE_QUANTITE},
                null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<>();
                Cave cave;

                do {
                    cave = new Cave();
                    cave.setCaveId(cursor.getLong(cursor.getColumnIndex(KEY_CAVE_ID)));
                    cave.setCaveFavoris(cursor.getInt(cursor.getColumnIndex(KEY_CAVE_FAVORIS)) != 0);
                    Log.i(ARG_DEBUG, "getAll: "+cursor.getInt(cursor.getColumnIndex(KEY_CAVE_QUANTITE)));
                    cave.setCaveQuantite(cursor.getInt(cursor.getColumnIndex(KEY_CAVE_QUANTITE)));

                    Vin vin = getVinById(sqLiteDatabase, cursor.getInt(cursor.getColumnIndex(KEY_CAVE_VIN)));
                    cave.setCaveVin(vin);

                    list.add(cave);

                } while (cursor.moveToNext());
            }

            cursor.close();
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
                    vin.setVinImage(c.getString(c.getColumnIndex(KEY_VIN_IMAGE)));
                    vin.setVinLibelle(c.getString(c.getColumnIndex(KEY_VIN_LIBELLE)));
                    vin.setVinCommentaire(c.getString(c.getColumnIndex(KEY_VIN_COMMENTAIRE)));
                    vin.setVinPrix(c.getDouble(c.getColumnIndex(KEY_VIN_PRIX)));
                    try {
                        if (c.getString(c.getColumnIndex(KEY_VIN_ANNEE)) != null) {
                            if (!c.getString(c.getColumnIndex(KEY_VIN_ANNEE)).isEmpty()) {
                                vin.setVinAnnee(vinDateFormat.parse(c.getString(c.getColumnIndex(KEY_VIN_ANNEE))));
                            }
                        }
                        if (c.getString(c.getColumnIndex(KEY_VIN_ANNEEMAX)) != null) {
                            if (!c.getString(c.getColumnIndex(KEY_VIN_ANNEEMAX)).isEmpty()) {
                                vin.setVinAnneeMax(vinDateFormat.parse(c.getString(c.getColumnIndex(KEY_VIN_ANNEEMAX))));
                            }
                        }

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
    public Vigneron getVigneronById(SQLiteDatabase sqLiteDatabase, long itemId) {

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
