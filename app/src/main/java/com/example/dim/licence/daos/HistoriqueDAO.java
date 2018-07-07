package com.example.dim.licence.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dim.licence.entities.Action;
import com.example.dim.licence.entities.AppUser;
import com.example.dim.licence.entities.Historique;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Vin;

import java.util.ArrayList;
import java.util.List;

import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_ACTION;
import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_APPUSER;
import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_DATE;
import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_ETAT;
import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_ID;
import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_NEW_VIGNERON;
import static com.example.dim.licence.models.DbHelper.KEY_HISTORIQUE_NEW_VIN;
import static com.example.dim.licence.models.DbHelper.TABLE_HISTORIQUE;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;

public class HistoriqueDAO {

    public long insertHistorique(SQLiteDatabase sqLiteDatabase,
                                 Historique historique,
                                 int userId, int actionId,
                                 int ancienVinId, int nouveauVinId,
                                 int ancienVigneronId, int nouveauVigneronId) {

        ContentValues historiqueCV = new ContentValues();
        historiqueCV.put(KEY_HISTORIQUE_APPUSER, userId);
        historiqueCV.put(KEY_HISTORIQUE_DATE, vinDateFormat.format(historique.getHistoriqueDate()));
        historiqueCV.put(KEY_HISTORIQUE_ACTION, actionId);
        historiqueCV.put(KEY_HISTORIQUE_NEW_VIN, nouveauVinId);
        historiqueCV.put(KEY_HISTORIQUE_NEW_VIGNERON, nouveauVigneronId);

        return sqLiteDatabase.insert(TABLE_HISTORIQUE, null, historiqueCV);
    }

    public boolean updateHistorique(SQLiteDatabase sqLiteDatabase,
                                    Historique historique) {

        ContentValues historiqueCV = new ContentValues();
        historiqueCV.put(KEY_HISTORIQUE_ETAT, 1);

        return sqLiteDatabase.update(TABLE_HISTORIQUE, historiqueCV,
                KEY_HISTORIQUE_ID + " = ? ",
                new String[]{String.valueOf(historique.getHistoriqueId())}) > 0;

    }

    public List<Historique> getAll(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Historique> list = null;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_HISTORIQUE + ";", null);

        if (c != null) {
            if (c.moveToFirst()) {
                list = new ArrayList<>();
                int rowNbr = 0;
                Historique h = null;
                AppUser hu = null;
                Action action = null;
                Vin ov = null;
                Vin nv = null;
                Vigneron ovg = null;
                Vigneron nvg = null;

                do {
                 /*   v = new Vigneron();
                    v.setVigneronId(c.getLong(c.getColumnIndex(KEY_VIGNERON_ID)));
                    v.setVigneronLibelle(c.getString(c.getColumnIndex(KEY_VIGNERON_LIBELLE)));
                    v.setVigneronDomaine(c.getString(c.getColumnIndex(KEY_VIGNERON_DOMAINE)));
                    v.setVigneronFixe(c.getString(c.getColumnIndex(KEY_VIGNERON_FIXE)));
                    v.setVigneronMobile(c.getString(c.getColumnIndex(KEY_VIGNERON_MOBILE)));
                    v.setVigneronMail(c.getString(c.getColumnIndex(KEY_VIGNERON_MAIL)));
                    v.setVigneronFax(c.getString(c.getColumnIndex(KEY_VIGNERON_FAX)));
                    v.setVigneronComment(c.getString(c.getColumnIndex(KEY_VIGNERON_COMMENTAIRE)));

                    geoloc = getGeolocalisationById(sqLiteDatabase, c.getInt(c.getColumnIndex(KEY_VIGNERON_ID)));
                    v.setVigneronGeoloc(geoloc);
                    list.add(v);
                    Log.i(ARG_DEBUG, "getAll: " + v.toString()); */
                } while (c.moveToNext());
            }

            c.close();
        }

        return list;
    }
}
