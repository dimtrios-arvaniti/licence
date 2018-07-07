package com.example.dim.licence.models;

import android.content.Context;
import android.util.Log;

import com.example.dim.licence.entities.AppUser;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;

import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.models.DbHelper.MASTER_DATABASE_NAME;

/**
 * This class defines actions allowed for master level (aka role in appUser table) users
 */
public class MasterModel extends AbstractModel {

    // STAYS PRIVATE !
    private MasterModel(Context context, String dbName) {
        super(context, dbName);
    }

    public static MasterModel getInstance(Context context) {
        if (model == null) {
            model = new MasterModel(context, MASTER_DATABASE_NAME);
        }
        return model;
    }

    public AppUser login(String mail, String pwd) {
        long id = getAppUserDAO().getUserIdByCredentials(getReadableDatabase(), mail, pwd);
        if (id == -1) {
            return null;
        }
        return getAppUserDAO().getById(getReadableDatabase(), Integer.valueOf(String.valueOf(id)));
    }

    public long createAccount(AppUser appUser) {
        return getAppUserDAO().insert(getWritableDatabase(), appUser);
    }

    // CRUD FOR VIGNERON FULLY ALLOWED
    // INSERT / UPDATE / DELETE / GET BY ID / GET ALL
    public boolean insertVigneron(Vigneron vigneron) {
        long id = getVigneronDAO().insert(getWritableDatabase(), vigneron);

        if (id == -1) {
            return false;
        }
        // set new id in object - do not if id == -1 !
        vigneron.setVigneronId(id);
        return true;
    }

    public boolean updateVigneron(Vigneron vigneron) {
        return getVigneronDAO().update(getWritableDatabase(), vigneron);
    }

    public boolean deleteVigneron(Vigneron vigneron) {
        return getVigneronDAO().delete(getWritableDatabase(), vigneron);
    }

    public Vigneron getVigneronByID(int vigneronId) {
        return getVigneronDAO().getById(getReadableDatabase(), vigneronId);
    }

    public List<Vigneron> getAllButDefaultVignerons() {
        List<Vigneron> list = getVigneronDAO().getAll(getReadableDatabase());
        // important : remove default cave vigneron value
        list.remove(0); // remove Vigneron 'AUCUN' !
        return list;
    }

    public List<Vigneron> getAllVignerons() {
        return getVigneronDAO().getAll(getReadableDatabase());
    }


    // CRUD FOR CAVE FULLY ALLOWED
    // INSERT / UPDATE / DELETE / GET BY ID / GET ALL
    public boolean insertCave(Cave cave) {
        Log.i(ARG_DEBUG, "insertCave: CaveVigneron"+cave.getCaveVin().getVinVigneron());
        long i = getVinDAO().insert(getWritableDatabase(), cave.getCaveVin());

        long id = getCaveDAO().oldInsert(getWritableDatabase(), cave, Integer.valueOf(String.valueOf(i)));
        //long id = getCaveDAO().insert(getWritableDatabase(), cave);

        if (id == -1) {
            return false;
        }
        // set new id in object - do not if id == -1 !
        cave.setCaveId(id);
        cave.getCaveVin().setVinId(i);
        Log.i(ARG_DEBUG, "insertCave: ++++++++++++++++++++ "+i);
        return true;
    }


    public boolean updateCave(Cave cave) {
        return getCaveDAO().update(getWritableDatabase(), cave);
    }

    public boolean deleteCave(Cave cave) {
        return getCaveDAO().delete(getWritableDatabase(), cave);
    }

    public Cave getCaveByID(int caveId) {
        return getCaveDAO().getById(getReadableDatabase(), caveId);
    }

    public List<Cave> getAllCave() {
        return getCaveDAO().getAll(getReadableDatabase());
    }

    // DICTIONARRY DATA ACCESS
    public List<TypeVin> getAllTypeVins(){
        return getTypeVinDAO().getAll(getReadableDatabase());
    }

    public List<Appelation> getAllAppelations() {
        return getAppelationDAO().getAll(getReadableDatabase());
    }


}
