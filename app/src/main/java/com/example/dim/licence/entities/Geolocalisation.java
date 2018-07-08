package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.GEOLOC_ADR1;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_ADR2;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_ADR3;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_COMPLEMENT;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_ID;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_VILLE;


public class Geolocalisation implements EntityBundleInterface {

    private Long geolocId;
    private Ville geolocVille;
    private String geolocAdresse1;
    private String geolocAdresse2;
    private String geolocAdresse3;
    private String geolocComplement;

    public Geolocalisation() {
        geolocVille = new Ville();
    }

    public Geolocalisation( Bundle bundle) {
        this.geolocVille = new Ville();
        if (bundle != null) {
            this.geolocId = bundle.getLong(GEOLOC_ID);
            this.geolocAdresse1 = bundle.getString(GEOLOC_ADR1);
            this.geolocAdresse2 = bundle.getString(GEOLOC_ADR2);
            this.geolocAdresse3 = bundle.getString(GEOLOC_ADR3);
            this.geolocComplement = bundle.getString(GEOLOC_COMPLEMENT);
            if (bundle.getBundle(GEOLOC_VILLE) != null) {
                this.geolocVille = new Ville(bundle.getBundle(GEOLOC_VILLE));
            }

        }
    }

    public Long getGeolocId() {
        return geolocId;
    }

    public void setGeolocId(Long geolocId) {
        this.geolocId = geolocId;
    }

    public Ville getGeolocVille() {
        return geolocVille;
    }

    public void setGeolocVille(Ville geolocVille) {
        this.geolocVille = geolocVille;
    }

    public String getGeolocAdresse1() {
        return geolocAdresse1;
    }

    public void setGeolocAdresse1(String geolocAdresse1) {
        this.geolocAdresse1 = geolocAdresse1;
    }

    public String getGeolocAdresse2() {
        return geolocAdresse2;
    }

    public void setGeolocAdresse2(String geolocAdresse2) {
        this.geolocAdresse2 = geolocAdresse2;
    }

    public String getGeolocAdresse3() {
        return geolocAdresse3;
    }

    public void setGeolocAdresse3(String geolocAdresse3) {
        this.geolocAdresse3 = geolocAdresse3;
    }

    public String getGeolocComplement() {
        return geolocComplement;
    }

    public void setGeolocComplement(String geolocComplement) {
        this.geolocComplement = geolocComplement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geolocalisation that = (Geolocalisation) o;

        if (geolocId != null ? !geolocId.equals(that.geolocId) : that.geolocId != null)
            return false;
        if (geolocVille != null ? !geolocVille.equals(that.geolocVille) : that.geolocVille != null)
            return false;
        if (geolocAdresse1 != null ? !geolocAdresse1.equals(that.geolocAdresse1) : that.geolocAdresse1 != null)
            return false;
        if (geolocAdresse2 != null ? !geolocAdresse2.equals(that.geolocAdresse2) : that.geolocAdresse2 != null)
            return false;
        if (geolocAdresse3 != null ? !geolocAdresse3.equals(that.geolocAdresse3) : that.geolocAdresse3 != null)
            return false;
        return geolocComplement != null ? geolocComplement.equals(that.geolocComplement) : that.geolocComplement == null;
    }

    @Override
    public int hashCode() {
        int result = geolocId != null ? geolocId.hashCode() : 0;
        result = 31 * result + (geolocVille != null ? geolocVille.hashCode() : 0);
        result = 31 * result + (geolocAdresse1 != null ? geolocAdresse1.hashCode() : 0);
        result = 31 * result + (geolocAdresse2 != null ? geolocAdresse2.hashCode() : 0);
        result = 31 * result + (geolocAdresse3 != null ? geolocAdresse3.hashCode() : 0);
        result = 31 * result + (geolocComplement != null ? geolocComplement.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Geolocalisation{" +
                "geolocId=" + geolocId +
                ", geolocVille=" + geolocVille +
                ", geolocAdresse1='" + geolocAdresse1 + '\'' +
                ", geolocAdresse2='" + geolocAdresse2 + '\'' +
                ", geolocAdresse3='" + geolocAdresse3 + '\'' +
                ", geolocComplement='" + geolocComplement + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        if (geolocId != null) {
            bundle.putLong(GEOLOC_ID, geolocId);
        }
        bundle.putString(GEOLOC_ADR1, geolocAdresse1);
        bundle.putString(GEOLOC_ADR2, geolocAdresse2);
        bundle.putString(GEOLOC_ADR3, geolocAdresse3);
        bundle.putString(GEOLOC_COMPLEMENT, geolocComplement);
        if (geolocVille !=  null){
            bundle.putBundle(GEOLOC_VILLE, geolocVille.entityToBundle());
        }

        return bundle;
    }
}
