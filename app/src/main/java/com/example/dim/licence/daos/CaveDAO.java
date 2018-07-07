package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_ID;
import static com.example.dim.licence.models.DbHelper.KEY_APPELATION_LIBELLE;
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
import static com.example.dim.licence.models.DbHelper.KEY_VIN_IMAGE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_LIBELLE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_PRIX;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_TYPE;
import static com.example.dim.licence.models.DbHelper.KEY_VIN_VIGNERON;
import static com.example.dim.licence.models.DbHelper.TABLE_APPELATION;
import static com.example.dim.licence.models.DbHelper.TABLE_CAVE;
import static com.example.dim.licence.models.DbHelper.TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.TABLE_VIGNERON;
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
