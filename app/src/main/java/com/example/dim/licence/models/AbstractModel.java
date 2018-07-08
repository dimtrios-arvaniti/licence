package com.example.dim.licence.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
import com.example.dim.licence.entities.Action;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Departement;
import com.example.dim.licence.entities.Region;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Ville;

import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_ACTION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_APPUSER;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_CAVE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_DEPARTEMENT;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_DOMAINE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_HISTORIQUE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_REGION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VILLE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VIN;
import static com.example.dim.licence.models.DbHelper.DATABASE_VERSION;
import static com.example.dim.licence.models.DbHelper.DEPARTEMENT_RAW_INSERT;
import static com.example.dim.licence.models.DbHelper.MASTER_DATABASE_NAME;
import static com.example.dim.licence.models.DbHelper.REGION_RAW_INSERT;

public abstract class AbstractModel extends SQLiteOpenHelper {

    // Single instance of MasterModel
    protected static MasterModel model;
    // Model DAO's
    protected ActionDAO actionDAO;
    protected AppelationDAO appelationDAO;
    protected AppUserDAO appUserDAO;
    protected CaveDAO caveDAO;
    protected GeolocalisationDAO geolocalisationDAO;
    protected HistoriqueDAO historiqueDAO;
    protected TypeVinDAO typeVinDAO;
    protected VigneronDAO vigneronDAO;
    protected VinDAO vinDAO;
    protected VilleDAO villeDAO;
    protected RegionDAO regionDAO;
    protected DepartementDAO departementDAO;

    protected AbstractModel(Context context) {
        super(context, MASTER_DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_DOMAINE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TYPEVIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_REGION);
        sqLiteDatabase.execSQL(CREATE_TABLE_DEPARTEMENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_VILLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_GEOLOCALISATION);
        sqLiteDatabase.execSQL(CREATE_TABLE_APPUSER);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIGNERON);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_CAVE);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORIQUE);
        initDatabaseData(sqLiteDatabase);
        sqLiteDatabase.execSQL(REGION_RAW_INSERT);
        sqLiteDatabase.execSQL(DEPARTEMENT_RAW_INSERT);
    }

    /**
     * Init basic dictionnary data for wineRoom
     * @param sqLiteDatabase
     */
    private void initDatabaseData(SQLiteDatabase sqLiteDatabase) {
        initTypeVinsDico(sqLiteDatabase);
        initAppelationsDico(sqLiteDatabase);
        initActionData(sqLiteDatabase);
        initVigneronData(sqLiteDatabase);
        initVillesData(sqLiteDatabase);

    }

    private void initVillesData(SQLiteDatabase sqLiteDatabase) {
        Region region = new Region();
        region.setRegionLibelle("UNDEFINED");
        Departement departement = new Departement();
        departement.setDepartementLibelle("UNDEFINED");
        departement.setDepartementRegion(region);
        Ville ville = new Ville();
        ville.setVilleLibelle("AUCUN");
        ville.setVilleDepartement(departement);
        villeDAO.insert(sqLiteDatabase, ville);
        }

    private void initVigneronData(SQLiteDatabase sqLiteDatabase) {
        //init default vigneron
        Vigneron vigneron = new Vigneron();
        vigneron.setVigneronLibelle("AUCUN");
        vigneronDAO.insert(sqLiteDatabase, vigneron);
    }

    private void initActionData(SQLiteDatabase sqLiteDatabase) {
        Action action = new Action();
        action.setActionLibelle("CREATION");
        long id = actionDAO.insert(sqLiteDatabase, action);

        action.setActionLibelle("SUPPRESSION");
        id = actionDAO.insert(sqLiteDatabase, action);

        action.setActionLibelle("MODIFICATION");
        id = actionDAO.insert(sqLiteDatabase, action);
    }

    /**
     * Init basic data for appelation
     * @param sqLiteDatabase
     */
    private void initAppelationsDico(SQLiteDatabase sqLiteDatabase) {
        Appelation appelation = new Appelation();
        appelation.setAppelationLibelle("AUCUN"); // DEFAULT VALUE
        long id = appelationDAO.insert(sqLiteDatabase, appelation);

        appelation.setAppelationLibelle("Bordeaux");
        id = appelationDAO.insert(sqLiteDatabase, appelation);

        appelation.setAppelationLibelle("Beaujolais");
        id = appelationDAO.insert(sqLiteDatabase, appelation);

    }

    /**
     * Initialize basic typevin data
     * @param sqLiteDatabase database to insert values into
     */
    private void initTypeVinsDico(SQLiteDatabase sqLiteDatabase) {
        TypeVin typeVin = new TypeVin();

        typeVin.setTypeVinLibelle("ROUGE");
        long id = typeVinDAO.insert(sqLiteDatabase, typeVin);

        typeVin.setTypeVinLibelle("BLANC");
        id = typeVinDAO.insert(sqLiteDatabase, typeVin);

        typeVin.setTypeVinLibelle("ROSEE");
        id = typeVinDAO.insert(sqLiteDatabase, typeVin);

        typeVin.setTypeVinLibelle("MOUSSEUX");
        id = typeVinDAO.insert(sqLiteDatabase, typeVin);

        typeVin.setTypeVinLibelle("CHAMPAGNE");
        id = typeVinDAO.insert(sqLiteDatabase, typeVin);

        typeVin.setTypeVinLibelle("AUTRE");
        id = typeVinDAO.insert(sqLiteDatabase, typeVin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // FIRST VERSION FOR NOW !
    }

}
