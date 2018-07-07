package com.example.dim.licence.models;

public class DbHelper {

    // ---------------------------------------------------------------------------------------------
    // DATABASE INFORMATIONS
    public static final int DATABASE_VERSION = 1;

    public static final String MASTER_DATABASE_NAME = "wineCave";
    public static final String RESTRICT_DATABASE_NAME = "wineCave_res";
    //public static final String ADMIN_DATABASE_NAME = "wineCave_adn";

    // TABLES
    public static final String TABLE_ACTION = "action";
    public static final String TABLE_APPELATION = "appelation";
    public static final String TABLE_TYPEVIN = "typevin";
    public static final String TABLE_CAVE = "cave";
    public static final String TABLE_GEOLOCALISATION = "geolocalisation";
    public static final String TABLE_VIGNERON = "vigneron";
    public static final String TABLE_VIN = "vin";
    public static final String TABLE_HISTORIQUE = "historique";
    public static final String TABLE_APPUSER = "appuser";

    // ---------------------------------------------------------------------------------------------
    // TABLES INFORMATIONS

    // ACTION
    public static final String KEY_ACTION_ID = "action_id";
    public static final String KEY_ACTION_LIBELLE = "action_libelle";
    // DOMAINE

    public static final String KEY_APPELATION_ID = "appel_id";
    public static final String KEY_APPELATION_LIBELLE = "appel_libelle";
    // TYPEVIN

    public static final String KEY_TYPEVIN_ID = "typvin_id";
    public static final String KEY_TYPEVIN_LIBELLE = "typvin_libelle";

    // APPUSER
    public static final String KEY_APPUSER_ID = "apuser_id";
    public static final String KEY_APPUSER_MAIL = "apuser_mail";
    public static final String KEY_APPUSER_PWD = "apuser_password";
    public static final String KEY_APPUSER_MASTER = "apuser_master";
    public static final String KEY_APPUSER_APIKEY = "apuser_apikey";
    public static final String KEY_APPUSER_CAVE_ID = "apuser_caveid";

    // CAVE
    public static final String KEY_CAVE_ID = "cave_id";
    public static final String KEY_CAVE_VIN = "cave_vin";
    public static final String KEY_CAVE_QUANTITE = "cave_quantite";
    public static final String KEY_CAVE_FAVORIS = "cave_favoris";

    // GEOLOCALISATION
    public static final String KEY_GEOLOC_ID = "geoloc_id";
    public static final String KEY_GEOLOC_PAYS = "geoloc_pays";
    public static final String KEY_GEOLOC_VILLE = "geoloc_ville";
    public static final String KEY_GEOLOC_CODE = "geoloc_code";
    public static final String KEY_GEOLOC_ADRESSE = "geoloc_adresse";

    // HISTORIQUE
    public static final String KEY_HISTORIQUE_ID = "histor_id";
    public static final String KEY_HISTORIQUE_APPUSER = "histor_appuser";
    public static final String KEY_HISTORIQUE_DATE = "histor_date";
    public static final String KEY_HISTORIQUE_ETAT = "histor_etat";
    public static final String KEY_HISTORIQUE_ACTION = "histor_action";
    public static final String KEY_HISTORIQUE_NEW_VIN = "histor_newvin";
    public static final String KEY_HISTORIQUE_NEW_VIGNERON = "histor_newvigneron";

    // VIGNERON
    public static final String KEY_VIGNERON_ID = "vigner_id";
    public static final String KEY_VIGNERON_LIBELLE = "vigner_libelle";
    public static final String KEY_VIGNERON_DOMAINE = "vigner_domaine";
    public static final String KEY_VIGNERON_GEOLOCALISATION = "vigner_geoloc";
    public static final String KEY_VIGNERON_FIXE = "vigner_fixe";
    public static final String KEY_VIGNERON_MOBILE = "vigner_mobile";
    public static final String KEY_VIGNERON_MAIL = "vigner_mail";
    public static final String KEY_VIGNERON_FAX = "vigner_fax";
    public static final String KEY_VIGNERON_COMMENTAIRE = "vigner_comment";

    // VIN
    public static final String KEY_VIN_ID = "vin_id";
    public static final String KEY_VIN_LIBELLE = "vin_libelle";
    public static final String KEY_VIN_IMAGE = "vin_image";
    public static final String KEY_VIN_APPELATION = "vin_appelation";
    //public static final String KEY_VIN_DOMAINE = "vin_domaine";
    public static final String KEY_VIN_VIGNERON = "vin_vigneron";
    public static final String KEY_VIN_TYPE = "vin_type";
    public static final String KEY_VIN_ANNEE = "vin_annee";
    public static final String KEY_VIN_PRIX = "vin_prix";
    public static final String KEY_VIN_ANNEEMAX = "vin_anneemax";
   // public static final String KEY_VIN_FAVORIS = "vin_favoris";
   // public static final String KEY_VIN_QUANTITE = "vin_quantite";
    public static final String KEY_VIN_COMMENTAIRE = "vin_comment";

    // ---------------------------------------------------------------------------------------------
    // STATEMENTS

    // CREATES
    public static final String CREATE_TABLE_ACTION = "CREATE TABLE " + TABLE_ACTION + " ("
            + KEY_ACTION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACTION_LIBELLE + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_DOMAINE = "CREATE TABLE " + TABLE_APPELATION + " ("
            + KEY_APPELATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_APPELATION_LIBELLE + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_TYPEVIN = "CREATE TABLE " + TABLE_TYPEVIN + " ("
            + KEY_TYPEVIN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TYPEVIN_LIBELLE + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_APPUSER = "CREATE TABLE " + TABLE_APPUSER + " ("
            + KEY_APPUSER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_APPUSER_APIKEY +" TEXT, "
            + KEY_APPUSER_MAIL + " TEXT(500), "
            + KEY_APPUSER_PWD + " TEXT, "
            + KEY_APPUSER_MASTER + " BOOLEAN DEFAULT 1, "
            + KEY_APPUSER_CAVE_ID + " INTEGER NOT NULL);";

    public static final String CREATE_TABLE_GEOLOCALISATION = "CREATE TABLE " + TABLE_GEOLOCALISATION + " ("
            + KEY_GEOLOC_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_GEOLOC_PAYS + " TEXT(200) DEFAULT '', "
            + KEY_GEOLOC_VILLE + " TEXT(200) DEFAULT '', "
            + KEY_GEOLOC_CODE + " TEXT(20) DEFAULT '', "
            + KEY_GEOLOC_ADRESSE + " TEXT(500) DEFAULT '');";

    public static final String CREATE_TABLE_CAVE = "CREATE TABLE "+TABLE_CAVE+ " ( "
            + KEY_CAVE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CAVE_VIN + " INTEGER NOT NULL, "
            + KEY_CAVE_QUANTITE + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CAVE_FAVORIS + " BOOLEAN DEFAULT 0, "
            + " FOREIGN KEY (" + KEY_CAVE_VIN + ") REFERENCES "
            + TABLE_VIN + "(" + KEY_VIN_ID + ")); ";

    public static final String CREATE_TABLE_HISTORIQUE = "CREATE TABLE " + TABLE_HISTORIQUE + " ("
            + KEY_HISTORIQUE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_HISTORIQUE_ACTION + " INTEGER, "
            + KEY_HISTORIQUE_APPUSER + " INTEGER, "
            + KEY_HISTORIQUE_DATE + " TEXT(200) DEFAULT '', "
            + KEY_HISTORIQUE_ETAT + " BOOLEAN DEFAULT 0, "
            + KEY_HISTORIQUE_NEW_VIGNERON + " INTEGER, "
            + KEY_HISTORIQUE_NEW_VIN + " INTEGER, "
            + " FOREIGN KEY (" + KEY_HISTORIQUE_ACTION + ") REFERENCES "
            + TABLE_ACTION + "(" + KEY_ACTION_ID + "), "
            + " FOREIGN KEY (" + KEY_HISTORIQUE_APPUSER + ") REFERENCES "
            + TABLE_APPUSER + "(" + KEY_APPUSER_ID + "), "
            + " FOREIGN KEY (" + KEY_HISTORIQUE_NEW_VIGNERON + ") REFERENCES "
            + TABLE_VIGNERON + "(" + KEY_VIGNERON_ID + "), "
            + " FOREIGN KEY (" + KEY_HISTORIQUE_NEW_VIN + ") REFERENCES "
            + TABLE_VIN + "(" + KEY_VIN_ID + ")); ";

    public static final String CREATE_TABLE_VIGNERON = "CREATE TABLE " + TABLE_VIGNERON + " ("
            + KEY_VIGNERON_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_VIGNERON_LIBELLE + " TEXT(200) NOT NULL, "
            + KEY_VIGNERON_DOMAINE + " TEXT(200) DEFAULT '', "
            + KEY_VIGNERON_GEOLOCALISATION + " INTEGER, "
            + KEY_VIGNERON_FIXE + " TEXT(20) DEFAULT '', "
            + KEY_VIGNERON_MOBILE + " TEXT(20) DEFAULT '', "
            + KEY_VIGNERON_MAIL + " TEXT(200) DEFAULT '', "
            + KEY_VIGNERON_FAX + " TEXT(20) DEFAULT '', "
            + KEY_VIGNERON_COMMENTAIRE + " TEXT(500) DEFAULT '', "
            + " FOREIGN KEY (" + KEY_VIGNERON_GEOLOCALISATION + ") REFERENCES "
            + TABLE_GEOLOCALISATION + "(" + KEY_GEOLOC_ID + ")); ";

    public static final String CREATE_TABLE_VIN = "CREATE TABLE " + TABLE_VIN + " ("
            + KEY_VIN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_VIN_LIBELLE + " TEXT(200) NOT NULL, "
            + KEY_VIN_IMAGE + " TEXT DEFAULT '', "
            + KEY_VIN_APPELATION + " INTEGER, "
            //+ KEY_VIN_FAVORIS + " BOOLEAN DEFAULT 0, "
            + KEY_VIN_TYPE + " INTEGER, "
            + KEY_VIN_PRIX + " REAL, "
           // + KEY_VIN_QUANTITE + " INTEGER DEFAULT 0, "
            + KEY_VIN_ANNEE + " TEXT(4) DEFAULT '', "
            + KEY_VIN_ANNEEMAX + " TEXT(4) DEFAULT '', "
            + KEY_VIN_VIGNERON + " INTEGER, "
            + KEY_VIN_COMMENTAIRE + " TEXT(500) DEFAULT '', "
            + " FOREIGN KEY (" + KEY_VIN_APPELATION + ") REFERENCES "
            + TABLE_APPELATION + "(" + KEY_APPELATION_ID + "), "
            + " FOREIGN KEY (" + KEY_VIN_TYPE + ") REFERENCES "
            + TABLE_TYPEVIN + "(" + KEY_TYPEVIN_ID + "), "
            + " FOREIGN KEY (" + KEY_VIN_VIGNERON + ") REFERENCES "
            + TABLE_VIGNERON + "(" + KEY_VIGNERON_ID + ")); ";

    // DROPS
    public static final String DROP_TABLE_CAVE = "DROP TABLE " + TABLE_CAVE +";";
    public static final String DROP_TABLE_ACTION = "DROP TABLE " + TABLE_ACTION +";";
    public static final String DROP_TABLE_APPUSER = "DROP TABLE " + TABLE_APPUSER +";";
    public static final String DROP_TABLE_TYPEVIN = "DROP TABLE " + TABLE_TYPEVIN +";";
    public static final String DROP_TABLE_DOMAINE = "DROP TABLE " + TABLE_APPELATION +";";
    public static final String DROP_TABLE_GEOLOCALISATION = "DROP TABLE " + TABLE_GEOLOCALISATION +";";
    public static final String DROP_TABLE_HISTORIQUE = "DROP TABLE " + TABLE_HISTORIQUE +";";
    public static final String DROP_TABLE_VIGNERON = "DROP TABLE " + TABLE_VIGNERON +";";
    public static final String DROP_TABLE_VIN = "DROP TABLE " + TABLE_VIN +";";

}
