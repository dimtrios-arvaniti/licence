package com.example.dim.licence.utils.commons;

import java.text.SimpleDateFormat;

public class Commons {

    private static final String COMMONS_DEBUG_TAG = "COMMONS_DEBUG";


    public static SimpleDateFormat vinDateFormat = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat appDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public static final int[] DICO_INT_1 = new int[]{0x459420, 0x915748, 0x864858, 0x324249, 0x423672,
            0x459646, 0x916248, 0x866288, 0x324199, 0x731672, 0x459456, 0x11538, 0x864338,
            0x326249, 0x423611, 0x866288, 0x324199, 0x731672, 0x459456, 0x11538, 0x864338,
            0x866288, 0x324199, 0x731672, 0x459456, 0x11538, 0x864338, 0x866288, 0x324199
    };


    /* some validation regexs*/

    public static final String MAIL_REGEX = ".{1,}@{1}.{1,}[.]{1}(fr{1}|com{1}|net{1}|eu{1})";
    public static final String TEL_REGEX = "([0-9]{1,15}|[+]{1}[0-9]{1,15}){1}";
    public static final String CODE_REGEX = "([0-9]{5}|[CEDEX]{1}[0-9]{10}){1}";
    public static final String YEAR_REGEX = "[1-2]{1}[0-9]{3}";


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
    public static final String GEOLOC_PAYS = "GEOLOC_PAYS";
    public static final String GEOLOC_VILLE = "GEOLOC_VILLE";
    public static final String GEOLOC_ADR = "GEOLOC_ADR";
    public static final String GEOLOC_CP = "GEOLOC_CP";


}
