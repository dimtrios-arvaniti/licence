package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.EntityBundleInterface;


public class Geolocalisation implements EntityBundleInterface<Geolocalisation> {

    private Integer geolocId;
    private String geolocPays;
    private String geolocVille;
    private String geolocCode;
    private String geolocAdresse;

    public Geolocalisation() {
    }

    public Integer getGeolocId() {
        return geolocId;
    }

    public void setGeolocId(Integer geolocId) {
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
        bundle.putInt(commons.GEOLOC_ID, geolocId);
        bundle.putString(commons.GEOLOC_PAYS, geolocPays);
        bundle.putString(commons.GEOLOC_VILLE, geolocVille);
        bundle.putString(commons.GEOLOC_CP, geolocCode);
        bundle.putString(commons.GEOLOC_ADR, geolocAdresse);
        return bundle;
    }

    @Override
    public void entityFromBundle(Bundle bundle) {
        this.geolocId = bundle.getInt(commons.GEOLOC_ID, 0);
        this.geolocPays = bundle.getString(commons.GEOLOC_PAYS, "");
        this.geolocVille = bundle.getString(commons.GEOLOC_VILLE, "");
        this.geolocCode = bundle.getString(commons.GEOLOC_CP, "");
        this.geolocAdresse = bundle.getString(commons.GEOLOC_ADR, "");
    }
}
