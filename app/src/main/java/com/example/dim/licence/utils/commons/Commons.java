package com.example.dim.licence.utils.commons;

import java.text.SimpleDateFormat;

public class Commons {

    private static final String COMMONS_DEBUG_TAG = "COMMONS_DEBUG";
    public static final int IMAGE_RESIZE = 400;
    //private static final int imagesMinS = 400;

    public static SimpleDateFormat vinDateFormat = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat appDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");




    /* some validation regexs*/

    public static final String MAIL_REGEX = ".{1,}@{1}.{1,}[.]{1}(fr{1}|com{1}|net{1}|eu{1})";
    public static final String TEL_REGEX = "([0-9]{1,15}|[+]{1}[0-9]{1,15}){1}";
    public static final String CODE_REGEX = "([0-9]{5}|[CEDEX]{1}[0-9]{10}){1}";
    public static final String YEAR_REGEX = "[1-2]{1}[0-9]{3}";


    public static final String CONTACT_TYPE = "CONTACT_TYPE";
    public static final String AVAILABLE_CONTACT_TYPES = "AVAILABLES_CONTACT_TYPE";


    /* Region bundle fields */
    public static final String REGION_ = "REGION_";
    public static final String REGION_COUNT = "REGION_COUNT";
    public static final String REGION_ID = "REGION_ID";
    public static final String REGION_LIBELLE = "REGION_LIBELLE";

    /* Departement bundle fields */
    public static final String DEPT_ = "DEPT_";
    public static final String DEPT_COUNT = "DEPT_COUNT";
    public static final String DEPT_ID = "DEPT_ID";
    public static final String DEPT_LIBELLE = "DEPT_LIBELLE";
    public static final String DEPT_REGION = "DEPT_REGION";

    /* Ville bunlde fields */
    public static final String VILLE_ = "VILLE_";
    public static final String VILLE_COUNT = "VILLE_COUNT";
    public static final String VILLE_ID = "VILLE_ID";
    public static final String VILLE_LIBELLE = "VILLE_LIBELLE";
    public static final String VILLE_ZIP_CODE = "VILLE_ZIP_CODE";
    public static final String VILLE_LONGITUDE = "VILLE_LONGITUDE";
    public static final String VILLE_LATITUDE = "VILLE_LATITUDE";
    public static final String VILLE_DEPARTEMENT = "VILLE_DEPARTEMENT";

    /* Appelation bundle fields */
    public static final String APLN_ = "APLN_";
    public static final String APLN_SELECTED = "APLN_SELECTED";
    public static final String APLN_COUNT = "APLN_COUNT";
    public static final String APLN_ID = "APLN_ID";
    public static final String APLN_LABEL = "APLN_LABEL";

    /* TypeVin bundle fields */
    public static final String TVIN_ = "TVIN_";
    public static final String TVIN_SELECTED = "TVIN_SELECTED";
    public static final String TVIN_COUNT = "TVIN_COUNT";
    public static final String TVIN_ID = "TVIN_ID";
    public static final String TVIN_LABEL = "TVIN_LABEL";

    /* Action bundle fields */
    public static final String ACT__= "ACT_";
    public static final String ACT_ID = "ACT_ID";
    public static final String ACT_LABEL = "ACT_LABEL";
    public static final String ACT_COUNT = "ACT_COUNT";

    /* Vin bundle fields */
    public static final String VIN_ = "VIN_";
    public static final String VIN_COUNT = "VIN_COUNT";
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
    public static final String CAVE_ = "CAVE_";
    public static final String CAVE_COUNT = "CAVE_COUNT";
    public static final String CAVE_SELECTED = "CAVE_SELECTED";
    public static final String CAVE_ID = "CAVE_ID";
    public static final String CAVE_VIN = "CAVE_VIN";
    public static final String CAVE_QTE = "CAVE_QTE";
    public static final String CAVE_FAVORIS = "CAVE_FAVORIS";

    /* types de dialogues */
    public static final String DIALOG_TYPE = "DIALOG_TYPE";
    public static final String DIALOG_CONTACT = "DIALOG_CONTACT";
    public static final String DIALOG_DELETE = "DIALOG_DELETE";
    public static final String DIALOG_SAVE = "DIALOG_SAVE";
    public static final String DIALOG_FILTER = "DIALOG_FILTER";
    public static final String DIALOG_CANCEL = "DIALOG_CANCEL";


    public static final String CONTACT_FIXE = "CONTACT_FIXE";
    public static final String CONTACT_MOBILE = "CONTACT_MOBILE";
    public static final String CONTACT_MAIL = "CONTACT_MAIL";

    /* Vigneron bundle fields */
    public static final String VIGN_ = "VIGN_";
    public static final String VIGN_COUNT = "VIGN_COUNT";
    public static final String VIGN_SELECTED = "VIGN_SELECTED";
    public static final String VIGN_ID = "VIGN_ID";
    public static final String VIGN_LABEL = "VIGN_LABEL";
    public static final String VIGN_DOMAINE = "VIGN_DOMAINE";
    public static final String VIGN_GEOLOC = "VIGN_GEOLOC"; // is geoloc != null in v entity
    public static final String VIGN_TEL = "VIGN_TEL";
    public static final String VIGN_MOBILE = "VIGN_MOBILE";
    public static final String VIGN_MAIL = "VIGN_MAIL";
    public static final String VIGN_FAX = "VIGN_FAX";
    public static final String VIGN_FIXE = "VIGN_FIXE";
    public static final String VIGN_COMMENT = "VIGN_COMMENT";

    /* Geolocalisation bundle fields */
    public static final String GEOLOC_ = "GEOLOC_";
    public static final String GEOLOC_COUNT = "GEOLOC_COUNT";
    public static final String GEOLOC_ID = "GEOLOC_ID";
    public static final String GEOLOC_VILLE = "GEOLOC_VILLE";
    public static final String GEOLOC_ADR1 = "GEOLOC_ADR1";
    public static final String GEOLOC_ADR2 = "GEOLOC_ADR2";
    public static final String GEOLOC_ADR3 = "GEOLOC_ADR3";
    public static final String GEOLOC_COMPLEMENT = "GEOLOC_COMPLEMENT";

    public static final String SAR_APPELATION_ID = "SAR_APPELATION_ID";
    public static final String SAR_APPELATION_LIBELLE = "SAR_APPELATION_LIBELLE";

    public static final String SAR_TYPEVIN_ID = "SAR_TYPEVIN_ID";
    public static final String SAR_TYPEVIN_LIBELLE = "SAR_TYPEVIN_LIBELLE";


    public static final String SAR_VIGNERON_ID = "SAR_VIGNERON_ID";
    public static final String SAR_VIGNERON_LIBELLE = "SAR_VIGNERON_LIBELLE";

    public static final String SAR_VILLE_ID = "SAR_VILLE_ID";
    public static final String SAR_VILLE_LIBELLE = "SAR_VILLE_LIBELLE";
    public static final String SAR_VILLE_ZIPCODE = "SAR_VILLE_ZIPCODE";

    public static final String VIGN_INTENT_RETURN = "VIGN_INTENT_RETURN";


    public static final String FILTER_TYPE = "FILTER_TYPE";
    public static final String CAVE_FILTER_NOM = "CAVE_FILTER_NOM";
    public static final String CAVE_FILTER_APPELATION = "CAVE_FILTER_APPELATION";
    public static final String CAVE_FILTER_ANNEE = "CAVE_FILTER_ANNEE";
    public static final String CAVE_FILTER_TYPE = "CAVE_FILTER_TYPE";
    public static final String CAVE_FILTER_VIGNERON = "CAVE_FILTER_VIGNERON";
    public static final String CAVE_FILTER_FAVORIS = "CAVE_FILTER_FAVORIS";

    public static final String AUCUN = "AUCUN";
    public static final String VIGN_FILTER_NOM = "VIGN_FILTER_NOM";
    public static final String VIGN_FILTER_DOMAINE = "VIGN_FILTER_DOMAINE";
    public static final String VIGN_FILTER_DEPARTEMENT = "VIGN_FILTER_DEPARTEMENT";
    public static final String VIGN_FILTER_REGION = "VIGN_FILTER_REGION";
    public static final String VIGN_FILTER_VILLE = "VIGN_FILTER_VILLE";
    public static final String VIGN_FILTER_ZIPCODE = "VIGN_FILTER_ZIPCODE";

}
