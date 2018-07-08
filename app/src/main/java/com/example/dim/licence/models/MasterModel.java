package com.example.dim.licence.models;

import android.content.Context;
import android.util.Log;

import com.example.dim.licence.daos.ActionDAO;
import com.example.dim.licence.daos.AppUserDAO;
import com.example.dim.licence.daos.AppelationDAO;
import com.example.dim.licence.daos.CaveDAO;
import com.example.dim.licence.daos.DepartementDAO;
import com.example.dim.licence.daos.GeolocalisationDAO;
import com.example.dim.licence.daos.HistoriqueDAO;
import com.example.dim.licence.daos.RegionDAO;
import com.example.dim.licence.daos.TypeVinDAO;
import com.example.dim.licence.daos.VigneronDAO;
import com.example.dim.licence.daos.VilleDAO;
import com.example.dim.licence.daos.VinDAO;
import com.example.dim.licence.entities.AppUser;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Ville;

import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

/**
 * This class defines actions allowed for master level (aka role in appUser table) users
 */
public class MasterModel extends AbstractModel {

    // STAYS PRIVATE !
    private MasterModel(Context context) {
        super(context);
        actionDAO = new ActionDAO();
        appelationDAO = new AppelationDAO();
        appUserDAO = new AppUserDAO();
        caveDAO = new CaveDAO();
        geolocalisationDAO = new GeolocalisationDAO();
        historiqueDAO = new HistoriqueDAO();
        typeVinDAO = new TypeVinDAO();
        vigneronDAO = new VigneronDAO();
        vinDAO = new VinDAO();
        regionDAO = new RegionDAO();
        departementDAO = new DepartementDAO();
        villeDAO = new VilleDAO();
    }

    public static MasterModel getInstance(Context context) {
        if (model == null) {
            model = new MasterModel(context);
        }
        return model;
    }

    public List<Ville> getAllVilles() {
        return villeDAO.getAll(getReadableDatabase());
    }

    public AppUser login(String mail, String pwd) {
        long id = appUserDAO.getUserIdByCredentials(getReadableDatabase(), mail, pwd);
        if (id == -1) {
            return null;
        }
        return appUserDAO.getById(getReadableDatabase(), Integer.valueOf(String.valueOf(id)));
    }

    public long createAccount(AppUser appUser) {
        return appUserDAO.insert(getWritableDatabase(), appUser);
    }

    // CRUD FOR VIGNERON FULLY ALLOWED
    // INSERT / UPDATE / DELETE / GET BY ID / GET ALL
    public boolean insertVigneron(Vigneron vigneron) {
        long id = vigneronDAO.insert(getWritableDatabase(), vigneron);

        if (id == -1) {
            return false;
        }
        // set new id in object - do not if id == -1 !
        vigneron.setVigneronId(id);
        return true;
    }

    public boolean updateVigneron(Vigneron vigneron) {
        return vigneronDAO.update(getWritableDatabase(), vigneron);
    }

    public boolean deleteVigneron(Vigneron vigneron) {
        return vigneronDAO.delete(getWritableDatabase(), vigneron);
    }

    public Vigneron getVigneronByID(int vigneronId) {
        return vigneronDAO.getById(getReadableDatabase(), vigneronId);
    }

    public List<Vigneron> getAllButDefaultVignerons() {
        List<Vigneron> list = vigneronDAO.getAll(getReadableDatabase());
        // important : remove default cave vigneron value
        list.remove(0); // remove Vigneron 'AUCUN' !
        return list;
    }

    public Ville getVilleById(int id) {
        return villeDAO.getById(getReadableDatabase(), id);
    }

    public Ville getVilleByLibelleAndZip(String libelle, String zip) {
        return villeDAO.getByLibelleAndZip(getReadableDatabase(), libelle, zip);
    }

    public List<Ville> filterVilleByLibelle(String text) {
        return villeDAO.filterByLibelle(getReadableDatabase(), text);
    }

    public List<Ville> filterVilleByZipCode(String text) {
        return villeDAO.filterByZipCode(getReadableDatabase(), text);
    }

    public List<Ville> getFirst15Villes() {
        return villeDAO.getFirst15(getReadableDatabase());
    }

    public List<Vigneron> getAllVignerons() {
        return vigneronDAO.getAll(getReadableDatabase());
    }

    public Departement getDepartmentById(int id) {
        return departementDAO.getById(getReadableDatabase(), id);
    }

    public boolean insertVille(Ville ville) {
           return villeDAO.insert(getWritableDatabase(), ville) > 0;
    }


    // CRUD FOR CAVE FULLY ALLOWED
    // INSERT / UPDATE / DELETE / GET BY ID / GET ALL
    public boolean insertCave(Cave cave) {
        Log.i(ARG_DEBUG, "insertCave: CaveVigneron"+cave.getCaveVin().getVinVigneron());
        long i = vinDAO.insert(getWritableDatabase(), cave.getCaveVin());

        long id = caveDAO.oldInsert(getWritableDatabase(), cave, Integer.valueOf(String.valueOf(i)));
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

    public boolean isVillesEmpty() {
        return villeDAO.isEmpty(getReadableDatabase());
    }

    public boolean updateCave(Cave cave) {
        return caveDAO.update(getWritableDatabase(), cave);
    }

    public boolean deleteCave(Cave cave) {
        return caveDAO.delete(getWritableDatabase(), cave);
    }

    public Cave getCaveByID(int caveId) {
        return caveDAO.getById(getReadableDatabase(), caveId);
    }

    public List<Cave> getAllCave() {
        return caveDAO.getAll(getReadableDatabase());
    }

    // DICTIONARRY DATA ACCESS
    public List<TypeVin> getAllTypeVins(){
        return typeVinDAO.getAll(getReadableDatabase());
    }

    public List<Appelation> getAllAppelations() {
        return appelationDAO.getAll(getReadableDatabase());
    }

}
