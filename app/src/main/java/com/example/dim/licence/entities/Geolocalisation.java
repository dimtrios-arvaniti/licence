package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.GEOLOC_ADR;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_CP;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_ID;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_PAYS;
import static com.example.dim.licence.utils.commons.Commons.GEOLOC_VILLE;


public class Geolocalisation implements EntityBundleInterface {

    private Long geolocId;
    private String geolocPays;
    private String geolocVille;
    private String geolocCode;
    private String geolocAdresse;

    public Geolocalisation() {
    }

    public Geolocalisation( Bundle bundle) {
        this.geolocId = bundle.getLong(GEOLOC_ID, 0);
        this.geolocPays = bundle.getString(GEOLOC_PAYS, "");
        this.geolocVille = bundle.getString(GEOLOC_VILLE, "");
        this.geolocCode = bundle.getString(GEOLOC_CP, "");
        this.geolocAdresse = bundle.getString(GEOLOC_ADR, "");
    }

    public Long getGeolocId() {
        return geolocId;
    }

    public void setGeolocId(Long geolocId) {
        this.geolocId = geolocId;
    }

    public String getGeolocPays() {
        return geolocPays;
    }

    public void setGeolocPays(String geolocPays) {
        this.geolocPays = geolocPays;
    }

    public String getGeolocVille() {
        return geolocVille;
    }

    public void setGeolocVille(String geolocVille) {
        this.geolocVille = geolocVille;
    }

    public String getGeolocCode() {
        return geolocCode;
    }

    public void setGeolocCode(String geolocCode) {
        this.geolocCode = geolocCode;
    }

    public String getGeolocAdresse() {
        return geolocAdresse;
    }

    public void setGeolocAdresse(String geolocAdresse) {
        this.geolocAdresse = geolocAdresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geolocalisation that = (Geolocalisation) o;

        if (geolocId != null ? !geolocId.equals(that.geolocId) : that.geolocId != null)
            return false;
        if (geolocPays != null ? !geolocPays.equals(that.geolocPays) : that.geolocPays != null)
            return false;
        if (geolocVille != null ? !geolocVille.equals(that.geolocVille) : that.geolocVille != null)
            return false;
        if (geolocCode != null ? !geolocCode.equals(that.geolocCode) : that.geolocCode != null)
            return false;
        return geolocAdresse != null ? geolocAdresse.equals(that.geolocAdresse) : that.geolocAdresse == null;
    }

    @Override
    public String toString() {
        return "Geolocalisation{" +
                "geolocId=" + geolocId +
                ", geolocPays='" + geolocPays + '\'' +
                ", geolocVille='" + geolocVille + '\'' +
                ", geolocCode='" + geolocCode + '\'' +
                ", geolocAdresse='" + geolocAdresse + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = geolocId != null ? geolocId.hashCode() : 0;
        result = 31 * result + (geolocPays != null ? geolocPays.hashCode() : 0);
        result = 31 * result + (geolocVille != null ? geolocVille.hashCode() : 0);
        result = 31 * result + (geolocCode != null ? geolocCode.hashCode() : 0);
        result = 31 * result + (geolocAdresse != null ? geolocAdresse.hashCode() : 0);
        return result;
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        if (geolocId != null) {
            bundle.putLong(GEOLOC_ID, geolocId);
        }

        bundle.putString(GEOLOC_PAYS, geolocPays);
        bundle.putString(GEOLOC_VILLE, geolocVille);
        bundle.putString(GEOLOC_CP, geolocCode);
        bundle.putString(GEOLOC_ADR, geolocAdresse);
        return bundle;
    }
}
