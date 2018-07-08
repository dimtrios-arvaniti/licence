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
    public static final String TABLE_REGION = "region";
    public static final String TABLE_DEPARTEMENT = "departement";
    public static final String TABLE_VILLE = "ville";
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

    // REGION
    public static final String KEY_REGION_ID = "region_id";
    public static final String KEY_REGION_LIBELLE = "region_libelle";

    // DEPARTEMENT
    public static final String KEY_DEPARTEMENT_ID = "dptt_id";
    public static final String KEY_DEPARTEMENT_LIBELLE = "dptt_libelle";
    public static final String KEY_DEPARTEMENT_REGION = "dptt_region";

    // VILLE
    public static final String KEY_VILLE_ID = "ville_id";
    public static final String KEY_VILLE_LIBELLE = "ville_libelle";
    public static final String KEY_VILLE_ZIP_CODE = "ville_zip_code";
    public static final String KEY_VILLE_LONGITUDE = "ville_longitude";
    public static final String KEY_VILLE_LATITUDE = "ville_latitude";
    public static final String KEY_VILLE_DEPARTEMENT = "ville_departement";

    // GEOLOCALISATION
    public static final String KEY_GEOLOC_ID = "geoloc_id";
    public static final String KEY_GEOLOC_VILLE = "geoloc_ville";
    public static final String KEY_GEOLOC_ADRESSE1 = "geoloc_adresse1";
    public static final String KEY_GEOLOC_ADRESSE2 = "geoloc_adresse2";
    public static final String KEY_GEOLOC_ADRESSE3 = "geoloc_adresse3";
    public static final String KEY_GEOLOC_COMPLEMENT = "geoloc_complement";

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
    public static final String KEY_VIN_VIGNERON = "vin_vigneron";
    public static final String KEY_VIN_TYPE = "vin_type";
    public static final String KEY_VIN_ANNEE = "vin_annee";
    public static final String KEY_VIN_PRIX = "vin_prix";
    public static final String KEY_VIN_ANNEEMAX = "vin_anneemax";
    public static final String KEY_VIN_COMMENTAIRE = "vin_comment";

    // ---------------------------------------------------------------------------------------------
    // STATEMENTS

    // CREATES
    public static final String CREATE_TABLE_ACTION = "CREATE TABLE " + TABLE_ACTION + " ("
            + KEY_ACTION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACTION_LIBELLE + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_REGION = "CREATE TABLE " + TABLE_REGION + " ("
            + KEY_REGION_ID +" INTEGER PRIMARY KEY, "
            + KEY_REGION_LIBELLE + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_DEPARTEMENT = "CREATE TABLE " + TABLE_DEPARTEMENT + " ("
            + KEY_DEPARTEMENT_ID +" INTEGER PRIMARY KEY, "
            + KEY_DEPARTEMENT_LIBELLE + " TEXT NOT NULL, "
            + KEY_DEPARTEMENT_REGION + " INTEGER, "
            + " FOREIGN KEY (" + KEY_DEPARTEMENT_REGION + ") REFERENCES "
            + TABLE_REGION + "(" + KEY_REGION_ID + ")); ";

    public static final String CREATE_TABLE_VILLE= "CREATE TABLE " + TABLE_VILLE + " ("
            + KEY_VILLE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_VILLE_LIBELLE + " TEXT NOT NULL collate nocase, "
            + KEY_VILLE_ZIP_CODE + " TEXT, "
            + KEY_VILLE_LATITUDE + " TEXT, "
            + KEY_VILLE_LONGITUDE + " TEXT, "
            + KEY_VILLE_DEPARTEMENT + " INTEGER NOT NULL, "
            + " FOREIGN KEY (" + KEY_VILLE_DEPARTEMENT + ") REFERENCES "
            + TABLE_DEPARTEMENT + "(" + KEY_DEPARTEMENT_ID + ")); ";

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
            + KEY_GEOLOC_VILLE + " INTEGER, "
            + KEY_GEOLOC_ADRESSE1 + " TEXT (200) DEFAULT '', "
            + KEY_GEOLOC_ADRESSE2 + " TEXT(200) DEFAULT '', "
            + KEY_GEOLOC_ADRESSE3 + " TEXT(200) DEFAULT '', "
            + KEY_GEOLOC_COMPLEMENT + " TEXT (3)  DEFAULT '', "
            + " FOREIGN KEY (" + KEY_GEOLOC_VILLE + ") REFERENCES "
            + TABLE_VILLE + "(" + KEY_VILLE_ID + ")); ";

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
    public static final String DROP_TABLE_VILLE = "DROP TABLE " + TABLE_VILLE +";";
    public static final String DROP_TABLE_REGION = "DROP TABLE " + TABLE_REGION +";";
    public static final String DROP_TABLE_DEPARTEMENT = "DROP TABLE " + TABLE_DEPARTEMENT +";";


    public static final String REGION_RAW_INSERT = "INSERT INTO "+ TABLE_REGION + " VALUES "
            + "(0, 'OUTRE MER'), "
            + "(1, 'GUADELOUPE'), "
            + "(2, 'MARTINIQUE'), "
            + "(3, 'GUYANE'), "
            + "(4, 'LA REUNION'), "
            + "(11, 'ILE-DE-FRANCE'), "
            + "(21, 'CHAMPAGNE-ARDENNE'), "
            + "(22, 'PICARDIE'), "
            + "(23, 'HAUTE-NORMANDIE'), "
            + "(24, 'CENTRE'), "
            + "(25, 'BASSE-NORMANDIE'), "
            + "(26, 'BOURGOGNE'), "
            + "(31, 'NORD-PAS-DE-CALAIS'), "
            + "(41, 'LORRAINE'), "
            + "(42, 'ALSACE'), "
            + "(43, 'FRANCHE-COMTE'), "
            + "(52, 'PAYS DE LA LOIRE'), "
            + "(53, 'BRETAGNE'), "
            + "(54, 'POITOU-CHARENTES'), "
            + "(72, 'AQUITAINE'), "
            + "(73, 'MIDI-PYRENEES'), "
            + "(74, 'LIMOUSIN'), "
            + "(82, 'RHONE-ALPES'), "
            + "(83, 'AUVERGNE'), "
            + "(91, 'LANGUEDOC-ROUSSILLON'), "
            + "(93, 'PROVENCE-ALPES-COTE D''AZUR'), "
            + "(94, 'CORSE')"
            +";";

    public static final String DEPARTEMENT_RAW_INSERT = "INSERT INTO "+ TABLE_DEPARTEMENT + " VALUES "
            + "(01,82,'AIN'), "
            + "(02,22,'AISNE'), "
            + "(03,83,'ALLIER'), "
            + "(04,93,'ALPES-DE-HAUTE-PROVENCE'), "
            + "(05,93,'HAUTES-ALPES'), "
            + "(06,93,'ALPES-MARITIMES'), "
            + "(07,82,'ARDECHE'), "
            + "(08,21,'ARDENNES'), "
            + "(09,73,'ARIEGE'), "
            + "(10,21,'AUBE'), "
            + "(11,91,'AUDE'), "
            + "(12,73,'AVEYRON'), "
            + "(13,93,'BOUCHES-DU-RHONE'), "
            + "(14,25,'CALVADOS'), "
            + "(15,83,'CANTAL'), "
            + "(16,54,'CHARENTE'), "
            + "(17,54,'CHARENTE-MARITIME'), "
            + "(18,24,'CHER'), "
            + "(19,74,'CORREZE'), "
            + "(21,26,'COTE-D''OR'), "
            + "(22,53,'COTES-D''ARMOR'), "
            + "(23,74,'CREUSE'), "
            + "(24,72,'DORDOGNE'), "
            + "(25,43,'DOUBS'), "
            + "(26,82,'DROME'), "
            + "(27,23,'EURE'), "
            + "(28,24,'EURE-ET-LOIR'), "
            + "(29,53,'FINISTERE'), "
            + "(30,91,'GARD'), "
            + "(31,73,'HAUTE-GARONNE'), "
            + "(32,73,'GERS'), "
            + "(33,72,'GIRONDE'), "
            + "(34,91,'HERAULT'), "
            + "(35,53,'ILLE-ET-VILAINE'), "
            + "(36,24,'INDRE'), "
            + "(37,24,'INDRE-ET-LOIRE'), "
            + "(38,82,'ISERE'), "
            + "(39,43,'JURA'), "
            + "(40,72,'LANDES'), "
            + "(41,24,'LOIR-ET-CHER'), "
            + "(42,82,'LOIRE'), "
            + "(43,83,'HAUTE-LOIRE'), "
            + "(44,52,'LOIRE-ATLANTIQUE'), "
            + "(45,24,'LOIRET'), "
            + "(46,73,'LOT'), "
            + "(47,72,'LOT-ET-GARONNE'), "
            + "(48,91,'LOZERE'), "
            + "(49,52,'MAINE-ET-LOIRE'), "
            + "(50,25,'MANCHE'), "
            + "(51,21,'MARNE'), "
            + "(52,21,'HAUTE-MARNE'), "
            + "(53,52,'MAYENNE'), "
            + "(54,41,'MEURTHE-ET-MOSELLE'), "
            + "(55,41,'MEUSE'), "
            + "(56,53,'MORBIHAN'), "
            + "(57,41,'MOSELLE'), "
            + "(58,26,'NIEVRE'), "
            + "(59,31,'NORD'), "
            + "(60,22,'OISE'), "
            + "(61,25,'ORNE'), "
            + "(62,31,'PAS-DE-CALAIS'), "
            + "(63,83,'PUY-DE-DOME'), "
            + "(64,72,'PYRENEES-ATLANTIQUES'), "
            + "(65,73,'HAUTES-PYRENEES'), "
            + "(66,91,'PYRENEES-ORIENTALES'), "
            + "(67,42,'BAS-RHIN'), "
            + "(68,42,'HAUT-RHIN'), "
            + "(69,82,'RHONE'), "
            + "(70,43,'HAUTE-SAONE'), "
            + "(71,26,'SAONE-ET-LOIRE'), "
            + "(72,52,'SARTHE'), "
            + "(73,82,'SAVOIE'), "
            + "(74,82,'HAUTE-SAVOIE'), "
            + "(75,11,'PARIS'), "
            + "(76,23,'SEINE-MARITIME'), "
            + "(77,11,'SEINE-ET-MARNE'), "
            + "(78,11,'YVELINES'), "
            + "(79,54,'DEUX-SEVRES'), "
            + "(80,22,'SOMME'), "
            + "(81,73,'TARN'), "
            + "(82,73,'TARN-ET-GARONNE'), "
            + "(83,93,'VAR'), "
            + "(84,93,'VAUCLUSE'), "
            + "(85,52,'VENDEE'), "
            + "(86,54,'VIENNE'), "
            + "(87,74,'HAUTE-VIENNE'), "
            + "(88,41,'VOSGES'), "
            + "(89,26,'YONNE'), "
            + "(90,43,'TERRITOIRE DE BELFORT'), "
            + "(91,11,'ESSONNE'), "
            + "(92,11,'HAUTS-DE-SEINE'), "
            + "(93,11,'SEINE-SAINT-DENIS'), "
            + "(94,11,'VAL-DE-MARNE'), "
            + "(95,11,'VAL-D''OISE'), "
            + "(210,94,'CORSE-DU-SUD'), "
            + "(211,94,'HAUTE-CORSE'), "
            + "(971,01,'GUADELOUPE'), "
            + "(972,02,'MARTINIQUE'), "
            + "(973,03,'GUYANE'), "
            + "(974,04,'LA REUNION'), "
            + "(975,00,'ST PIERRE ET MIQUELON'), "
            + "(976,00,'MAYOTTE'), "
            + "(986,00,'WALLIS ET FUTUNA'), "
            + "(987,00,'POLYNÉSIE FRANÇAISE'), "
            + "(988,00,'NOUVELLE CALÉDONIE') "
            + ";";

}
