package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.VILLE_DEPARTEMENT;
import static com.example.dim.licence.utils.commons.Commons.VILLE_ID;
import static com.example.dim.licence.utils.commons.Commons.VILLE_LATITUDE;
import static com.example.dim.licence.utils.commons.Commons.VILLE_LIBELLE;
import static com.example.dim.licence.utils.commons.Commons.VILLE_LONGITUDE;
import static com.example.dim.licence.utils.commons.Commons.VILLE_ZIP_CODE;

public class Ville implements EntityBundleInterface {

    //`id`, `departement_code`, `zipcode`, `libelle`, `longitude`, `latitude`,
    private long villeId;
    private String villeLibelle;
    private String villeZipCode;
    private String villeLongitude;
    private String villeLatitude;
    private Departement villeDepartement;

    public Ville() {
        villeDepartement = new Departement();
    }

    public Ville(Bundle bundle) {
        villeDepartement = new Departement();
        if (bundle != null) {
            villeId = bundle.getLong(VILLE_ID);
            villeLibelle = bundle.getString(VILLE_LIBELLE);
            villeZipCode = bundle.getString(VILLE_ZIP_CODE);
            villeLatitude = bundle.getString(VILLE_LATITUDE);
            villeLongitude = bundle.getString(VILLE_LONGITUDE);
            villeDepartement = new Departement(bundle.getBundle(VILLE_DEPARTEMENT));
        }
    }

    public long getVilleId() {
        return villeId;
    }

    public void setVilleId(long villeId) {
        this.villeId = villeId;
    }

    public String getVilleLibelle() {
        return villeLibelle;
    }

    public void setVilleLibelle(String villeLibelle) {
        this.villeLibelle = villeLibelle;
    }

    public String getVilleZipCode() {
        return villeZipCode;
    }

    public void setVilleZipCode(String villeZipCode) {
        this.villeZipCode = villeZipCode;
    }

    public String getVilleLongitude() {
        return villeLongitude;
    }

    public void setVilleLongitude(String villeLongitude) {
        this.villeLongitude = villeLongitude;
    }

    public String getVilleLatitude() {
        return villeLatitude;
    }

    public void setVilleLatitude(String villeLatitude) {
        this.villeLatitude = villeLatitude;
    }

    public Departement getVilleDepartement() {
        return villeDepartement;
    }

    public void setVilleDepartement(Departement villeDepartement) {
        this.villeDepartement = villeDepartement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ville ville = (Ville) o;

        if (villeId != ville.villeId) return false;
        if (villeLibelle != null ? !villeLibelle.equals(ville.villeLibelle) : ville.villeLibelle != null)
            return false;
        if (villeZipCode != null ? !villeZipCode.equals(ville.villeZipCode) : ville.villeZipCode != null)
            return false;
        if (villeLongitude != null ? !villeLongitude.equals(ville.villeLongitude) : ville.villeLongitude != null)
            return false;
        if (villeLatitude != null ? !villeLatitude.equals(ville.villeLatitude) : ville.villeLatitude != null)
            return false;
        return villeDepartement != null ? villeDepartement.equals(ville.villeDepartement) : ville.villeDepartement == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (villeId ^ (villeId >>> 32));
        result = 31 * result + (villeLibelle != null ? villeLibelle.hashCode() : 0);
        result = 31 * result + (villeZipCode != null ? villeZipCode.hashCode() : 0);
        result = 31 * result + (villeLongitude != null ? villeLongitude.hashCode() : 0);
        result = 31 * result + (villeLatitude != null ? villeLatitude.hashCode() : 0);
        result = 31 * result + (villeDepartement != null ? villeDepartement.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "villeId=" + villeId +
                ", villeLibelle='" + villeLibelle + '\'' +
                ", villeZipCode='" + villeZipCode + '\'' +
                ", villeLongitude='" + villeLongitude + '\'' +
                ", villeLatitude='" + villeLatitude + '\'' +
                ", villeDepartement=" + villeDepartement +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(VILLE_ID, villeId);
        bundle.putString(VILLE_LIBELLE, villeLibelle);
        bundle.putString(VILLE_ZIP_CODE, villeZipCode);
        bundle.putString(VILLE_LATITUDE, villeLatitude);
        bundle.putString(VILLE_LONGITUDE, villeLongitude);
        if (villeDepartement != null) {
            bundle.putBundle(VILLE_DEPARTEMENT, villeDepartement.entityToBundle());
        }

        return bundle;
    }
}
