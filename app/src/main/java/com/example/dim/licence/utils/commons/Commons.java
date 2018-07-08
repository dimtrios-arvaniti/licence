package com.example.dim.licence.utils.commons;

import java.text.SimpleDateFormat;

public class Commons {

    private static final String COMMONS_DEBUG_TAG = "COMMONS_DEBUG";


    public static SimpleDateFormat vinDateFormat = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat appDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");




    /* some validation regexs*/

    public static final String MAIL_REGEX = ".{1,}@{1}.{1,}[.]{1}(fr{1}|com{1}|net{1}|eu{1})";
    public static final String TEL_REGEX = "([0-9]{1,15}|[+]{1}[0-9]{1,15}){1}";
    public static final String CODE_REGEX = "([0-9]{5}|[CEDEX]{1}[0-9]{10}){1}";
    public static final String YEAR_REGEX = "[1-2]{1}[0-9]{3}";


    /* Region bundle fields */
    public static final String R_ID = "R_ID";
    public static final String R_LIBELLE = "R_LIBELLE";

    /* Departement bundle fields */
    public static final String D_ID = "D_ID";
    public static final String D_LIBELLE = "D_LIBELLE";
    public static final String D_REGION = "D_REGION";

    /* Ville bunlde fields */
    public static final String VILLE_ID = "V_ID";
    public static final String VILLE_LIBELLE = "V_LIBELLE";
    public static final String VILLE_ZIP_CODE = "V_ZIP_CODE";
    public static final String VILLE_LONGITUDE = "V_LONGITUDE";
    public static final String VILLE_LATITUDE = "V_LATITUDE";
    public static final String VILLE_DEPARTEMENT = "V_DEPARTEMENT";

    /* Appelation bundle fields */
    public static final String A_ID = "A_ID";
    public static final String A_LABEL = "A_LABEL";

    /* TypeVin bundle fields */
    public static final String TV_ID = "D_ID";
    public static final String TV_LABEL = "D_LABEL";

    /* Action bundle fields */
    public static final String ACT_ID = "ACT_ID";
    public static final String ACT_LABEL = "ACT_LABEL";

    /* Vin bundle fields */
    public static final String VIN_ID = "VIN_ID";
    public static final String VIN_IMAGE = "VIN_IMAGE";
    public static final String VIN_LABEL = "VIN_LABEL";
    public static final String VIN_ANNEE = "VIN_ANNEE";
    public static final String VIN_ANNEE_MAX = "VIN_ANNEE_MAX";
    public static final String VIN_COMMENTAIRE = "VIN_COMMENT";
    public static final String VIN_PRIX = "VIN_PRIX";
    public static final String VIN_TYPE = "VIN_TYPE";
    public static final String VIN_VIGNERON = "VIN_VIGNERON";
    public static final String VIN_APPELATION = "VIN_APPELATION";

    /* Cave bundle fields */
    public static final String CAVE_ID = "CAVE_ID";
    public static final String CAVE_VIN = "CAVE_VIN";
    public static final String CAVE_QTE = "CAVE_QTE";
    public static final String CAVE_FAVORIS = "CAVE_FAVORIS";

    /* Vigneron bundle fields */
    public static final String V_ID = "V_ID";
    public static final String V_LABEL = "V_LABEL";
    public static final String V_DOMAINE = "V_DOMAINE";
    public static final String V_GEOLOC = "V_GEOLOC"; // is geoloc != null in v entity
    public static final String V_TEL = "V_TEL";
    public static final String V_MOBILE = "V_MOBILE";
    public static final String V_MAIL = "V_MAIL";
    public static final String V_FAX = "V_FAX";
    public static final String V_COMMENT = "V_COMMENT";

    /* Geolocalisation bundle fields */
    public static final String GEOLOC_ID = "GEOLOC_ID";
    public static final String GEOLOC_VILLE = "GEOLOC_VILLE";
    public static final String GEOLOC_ADR1 = "GEOLOC_ADR1";
    public static final String GEOLOC_ADR2 = "GEOLOC_ADR2";
    public static final String GEOLOC_ADR3 = "GEOLOC_ADR3";
    public static final String GEOLOC_COMPLEMENT = "GEOLOC_COMPLEMENT";


}
