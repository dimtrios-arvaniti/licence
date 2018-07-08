package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.D_ID;
import static com.example.dim.licence.utils.commons.Commons.D_LIBELLE;
import static com.example.dim.licence.utils.commons.Commons.D_REGION;

public class Departement implements EntityBundleInterface {

    private long departementId;
    private String departementLibelle;
    private Region departementRegion;

    public Departement() {
        departementRegion = new Region();
    }

    public Departement(Bundle bundle) {
        departementRegion = new Region();
        if (bundle !=  null) {
            departementId = bundle.getLong(D_ID);
            departementLibelle = bundle.getString(D_LIBELLE);
            departementRegion = new Region(bundle.getBundle(D_REGION));
        }

    }

    public long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(long departementId) {
        this.departementId = departementId;
    }

    public String getDepartementLibelle() {
        return departementLibelle;
    }

    public void setDepartementLibelle(String departementLibelle) {
        this.departementLibelle = departementLibelle;
    }

    public Region getDepartementRegion() {
        return departementRegion;
    }

    public void setDepartementRegion(Region departementRegion) {
        this.departementRegion = departementRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Departement that = (Departement) o;

        if (departementId != that.departementId) return false;
        if (departementLibelle != null ? !departementLibelle.equals(that.departementLibelle) : that.departementLibelle != null)
            return false;
        return departementRegion != null ? departementRegion.equals(that.departementRegion) : that.departementRegion == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (departementId ^ (departementId >>> 32));
        result = 31 * result + (departementLibelle != null ? departementLibelle.hashCode() : 0);
        result = 31 * result + (departementRegion != null ? departementRegion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Departement{" +
                "departementId=" + departementId +
                ", departementLibelle='" + departementLibelle + '\'' +
                ", departementRegion=" + departementRegion +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(D_ID, departementId);
        bundle.putString(D_LIBELLE, departementLibelle);
        bundle.putBundle(D_REGION, departementRegion.entityToBundle());
        return bundle;
    }
}
