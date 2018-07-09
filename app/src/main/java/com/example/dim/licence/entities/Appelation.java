package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.APLN_ID;
import static com.example.dim.licence.utils.commons.Commons.APLN_LABEL;

public class Appelation implements EntityBundleInterface {

    private Long appelationId;
    private String appelationLibelle;

    public Appelation() {

    }

    public Appelation(Bundle bundle) {
        this.appelationId = bundle.getLong(APLN_ID);
        this.appelationLibelle = bundle.getString(APLN_LABEL);
    }

    public Long getAppelationId() {
        return appelationId;
    }

    public void setAppelationId(Long appelationId) {
        this.appelationId = appelationId;
    }

    public String getAppelationLibelle() {
        return appelationLibelle;
    }

    public void setAppelationLibelle(String appelationLibelle) {
        this.appelationLibelle = appelationLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appelation appelation = (Appelation) o;

        if (appelationId != null ? !appelationId.equals(appelation.appelationId) : appelation.appelationId != null)
            return false;
        return appelationLibelle != null ? appelationLibelle.equals(appelation.appelationLibelle) : appelation.appelationLibelle == null;
    }

    @Override
    public int hashCode() {
        int result = appelationId != null ? appelationId.hashCode() : 0;
        result = 31 * result + (appelationLibelle != null ? appelationLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Appelation{" +
                "appelationId=" + appelationId +
                ", appelationLibelle='" + appelationLibelle + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(APLN_ID, appelationId);
        bundle.putString(APLN_LABEL, appelationLibelle);
        return bundle;
    }
}
