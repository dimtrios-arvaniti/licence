package com.example.dim.licence.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dim.licence.daos.ActionDAO;
import com.example.dim.licence.daos.AppUserDAO;
import com.example.dim.licence.daos.AppelationDAO;
import com.example.dim.licence.daos.CaveDAO;
import com.example.dim.licence.daos.GeolocalisationDAO;
import com.example.dim.licence.daos.HistoriqueDAO;
import com.example.dim.licence.daos.TypeVinDAO;
import com.example.dim.licence.daos.VigneronDAO;
import com.example.dim.licence.daos.VinDAO;
import com.example.dim.licence.entities.Action;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;

import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_ACTION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_APPUSER;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_CAVE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_DOMAINE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_GEOLOCALISATION;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_HISTORIQUE;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_TYPEVIN;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VIGNERON;
import static com.example.dim.licence.models.DbHelper.CREATE_TABLE_VIN;
import static com.example.dim.licence.models.DbHelper.DATABASE_VERSION;

public abstract class AbstractModel extends SQLiteOpenHelper {

    // Single instance of MasterModel
    protected static MasterModel model;
    // Model DAO's
    private ActionDAO actionDAO;
    private AppelationDAO appelationDAO;
    private AppUserDAO appUserDAO;
    private CaveDAO caveDAO;
    private GeolocalisationDAO geolocalisationDAO;
    private HistoriqueDAO historiqueDAO;
    private TypeVinDAO typeVinDAO;
    private VigneronDAO vigneronDAO;
    private VinDAO vinDAO;

    protected AbstractModel(Context context, String dbName) {
        super(context, dbName, null, DATABASE_VERSION);
        actionDAO = new ActionDAO();
        appelationDAO = new AppelationDAO();
        appUserDAO = new AppUserDAO();
        caveDAO = new CaveDAO();
        geolocalisationDAO = new GeolocalisationDAO();
        historiqueDAO = new HistoriqueDAO();
        typeVinDAO = new TypeVinDAO();
        vigneronDAO = new VigneronDAO();
        vinDAO = new VinDAO();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_DOMAINE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TYPEVIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_GEOLOCALISATION);
        sqLiteDatabase.execSQL(CREATE_TABLE_APPUSER);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIGNERON);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_CAVE);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORIQUE);
        initDatabaseData(sqLiteDatabase);
    }

    /**
     * Init basic dictionnary data for wineRoom
     * @param sqLiteDatabase
     */
    private void initDatabaseData(SQLiteDatabase sqLiteDatabase) {
        initTypeVinsDico(sqLiteDatabase);
        initAppelationsDico(sqLiteDatabase);
        initActionData(sqLiteDatabase);
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

    protected ActionDAO getActionDAO() {
        return actionDAO;
    }

    protected AppelationDAO getAppelationDAO() {
        return appelationDAO;
    }

    protected AppUserDAO getAppUserDAO() {
        return appUserDAO;
    }

    protected CaveDAO getCaveDAO() {
        return caveDAO;
    }

    protected GeolocalisationDAO getGeolocalisationDAO() {
        return geolocalisationDAO;
    }

    protected HistoriqueDAO getHistoriqueDAO() {
        return historiqueDAO;
    }

    protected TypeVinDAO getTypeVinDAO() {
        return typeVinDAO;
    }

    protected VigneronDAO getVigneronDAO() {
        return vigneronDAO;
    }

    protected VinDAO getVinDAO() {
        return vinDAO;
    }

}
