package com.example.dim.licence.entities;

import android.os.Bundle;
import android.util.Log;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import java.text.ParseException;
import java.util.Date;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.utils.commons.Commons.VIN_ANNEE;
import static com.example.dim.licence.utils.commons.Commons.VIN_ANNEE_MAX;
import static com.example.dim.licence.utils.commons.Commons.VIN_APPELATION;
import static com.example.dim.licence.utils.commons.Commons.VIN_COMMENTAIRE;
import static com.example.dim.licence.utils.commons.Commons.VIN_ID;
import static com.example.dim.licence.utils.commons.Commons.VIN_IMAGE;
import static com.example.dim.licence.utils.commons.Commons.VIN_LABEL;
import static com.example.dim.licence.utils.commons.Commons.VIN_PRIX;
import static com.example.dim.licence.utils.commons.Commons.VIN_TYPE;
import static com.example.dim.licence.utils.commons.Commons.VIN_VIGNERON;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;

public class Vin implements EntityBundleInterface{

    private Long vinId;
    private String vinImage;
    private String vinLibelle;
    private Appelation vinAppelation;
    private TypeVin vinType;
    private Vigneron vinVigneron;
    private Double vinPrix;
    private Date vinAnnee;
    private Date vinAnneeMax;
    private String vinCommentaire;

    public Vin() {
        vinVigneron = new Vigneron();
    }

    public Vin(Bundle bundle) {
        vinVigneron = new Vigneron();
        this.vinId = bundle.getLong(VIN_ID, 0);
        this.vinLibelle = bundle.getString(VIN_LABEL, "");
        this.vinCommentaire = bundle.getString(VIN_COMMENTAIRE, "");
        this.vinPrix = bundle.getDouble(VIN_PRIX);
        this.vinImage = bundle.getString(VIN_IMAGE, "");

        if (bundle.getBundle(VIN_APPELATION) != null) {
            this.vinAppelation = new Appelation(bundle.getBundle(VIN_APPELATION));
        }
        if (bundle.getBundle(VIN_TYPE) != null) {
            this.vinType = new TypeVin(bundle.getBundle(VIN_TYPE));
        }
        if (bundle.getBundle(VIN_VIGNERON) != null) {
            this.vinVigneron = new Vigneron(bundle.getBundle(VIN_VIGNERON));
        }
        if (bundle.getString(VIN_ANNEE) != null) {
            if (!bundle.getString(VIN_ANNEE).isEmpty()) {
                try {
                    this.vinAnnee = vinDateFormat.parse(bundle.getString(VIN_ANNEE));
                } catch (ParseException pe) {
                    Log.e(ARG_DEBUG, "Vin: ", pe);
                }

            }
        }

        if (bundle.getString(VIN_ANNEE_MAX) != null) {
            if (!bundle.getString(VIN_ANNEE_MAX).isEmpty()) {
                try {
                    this.vinAnneeMax = vinDateFormat.parse(bundle.getString(VIN_ANNEE_MAX));
                } catch (ParseException pe) {
                    Log.e(ARG_DEBUG, "Vin: ", pe);
                }

            }
        }


    }

    public Long getVinId() {
        return vinId;
    }

    public void setVinId(Long vinId) {
        this.vinId = vinId;
    }

    public String getVinLibelle() {
        return vinLibelle;
    }

    public void setVinLibelle(String vinLibelle) {
        this.vinLibelle = vinLibelle;
    }

    public Appelation getVinAppelation() {
        return vinAppelation;
    }

    public void setVinAppelation(Appelation vinAppelation) {
        this.vinAppelation = vinAppelation;
    }

    public TypeVin getVinType() {
        return vinType;
    }

    public void setVinType(TypeVin vinType) {
        this.vinType = vinType;
    }

    public Vigneron getVinVigneron() {
        return vinVigneron;
    }

    public void setVinVigneron(Vigneron vinVigneron) {
        this.vinVigneron = vinVigneron;
    }

    public Double getVinPrix() {
        return vinPrix;
    }

    public void setVinPrix(Double vinPrix) {
        this.vinPrix = vinPrix;
    }

    public Date getVinAnnee() {
        return vinAnnee;
    }

    public void setVinAnnee(Date vinAnnee) {
        this.vinAnnee = vinAnnee;
    }

    public Date getVinAnneeMax() {
        return vinAnneeMax;
    }

    public void setVinAnneeMax(Date vinAnneeMax) {
        this.vinAnneeMax = vinAnneeMax;
    }

    public String getVinCommentaire() {
        return vinCommentaire;
    }

    public void setVinCommentaire(String vinCommentaire) {
        this.vinCommentaire = vinCommentaire;
    }

    public String getVinImage() {
        return vinImage;
    }

    public void setVinImage(String vinImage) {
        this.vinImage = vinImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vin vin = (Vin) o;

        if (vinId != null ? !vinId.equals(vin.vinId) : vin.vinId != null) return false;
        if (vinImage != null ? !vinImage.equals(vin.vinImage) : vin.vinImage != null) return false;
        if (vinLibelle != null ? !vinLibelle.equals(vin.vinLibelle) : vin.vinLibelle != null)
            return false;
        if (vinAppelation != null ? !vinAppelation.equals(vin.vinAppelation) : vin.vinAppelation != null)
            return false;
        if (vinType != null ? !vinType.equals(vin.vinType) : vin.vinType != null) return false;
        if (vinVigneron != null ? !vinVigneron.equals(vin.vinVigneron) : vin.vinVigneron != null)
            return false;
        if (vinPrix != null ? !vinPrix.equals(vin.vinPrix) : vin.vinPrix != null) return false;
        if (vinAnnee != null ? !vinAnnee.equals(vin.vinAnnee) : vin.vinAnnee != null) return false;
        if (vinAnneeMax != null ? !vinAnneeMax.equals(vin.vinAnneeMax) : vin.vinAnneeMax != null)
            return false;
        return vinCommentaire != null ? vinCommentaire.equals(vin.vinCommentaire) : vin.vinCommentaire == null;
    }

    @Override
    public int hashCode() {
        int result = vinId != null ? vinId.hashCode() : 0;
        result = 31 * result + (vinImage != null ? vinImage.hashCode() : 0);
        result = 31 * result + (vinLibelle != null ? vinLibelle.hashCode() : 0);
        result = 31 * result + (vinAppelation != null ? vinAppelation.hashCode() : 0);
        result = 31 * result + (vinType != null ? vinType.hashCode() : 0);
        result = 31 * result + (vinVigneron != null ? vinVigneron.hashCode() : 0);
        result = 31 * result + (vinPrix != null ? vinPrix.hashCode() : 0);
        result = 31 * result + (vinAnnee != null ? vinAnnee.hashCode() : 0);
        result = 31 * result + (vinAnneeMax != null ? vinAnneeMax.hashCode() : 0);
        result = 31 * result + (vinCommentaire != null ? vinCommentaire.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vin{" +
                "vinId=" + vinId +
                ", vinImage='" + vinImage + '\'' +
                ", vinLibelle='" + vinLibelle + '\'' +
                ", vinAppelation=" + vinAppelation +
                ", vinType=" + vinType +
                ", vinVigneron=" + vinVigneron +
                ", vinPrix=" + vinPrix +
                ", vinAnnee=" + vinAnnee +
                ", vinAnneeMax=" + vinAnneeMax +
                ", vinCommentaire='" + vinCommentaire + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(VIN_ID, vinId);
        bundle.putString(VIN_LABEL, vinLibelle);
        bundle.putString(VIN_IMAGE, vinImage);
        if (getVinAnnee() != null) {
            bundle.putString(VIN_ANNEE, vinDateFormat.format(getVinAnnee()));
        }
        if (getVinAnneeMax() != null) {
            bundle.putString(VIN_ANNEE_MAX, vinDateFormat.format(getVinAnneeMax()));
        }
        bundle.putString(VIN_COMMENTAIRE, vinCommentaire);
        bundle.putDouble(VIN_PRIX, vinPrix == null ? 0.0 : vinPrix);
        bundle.putBundle(VIN_TYPE, vinType.entityToBundle());
        bundle.putBundle(VIN_APPELATION, vinAppelation.entityToBundle());
        if (vinVigneron != null) {
            bundle.putBundle(VIN_VIGNERON, vinVigneron.entityToBundle());
        }

        return bundle;
    }
}
