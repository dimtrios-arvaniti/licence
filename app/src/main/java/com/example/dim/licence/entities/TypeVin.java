package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.TVIN_ID;
import static com.example.dim.licence.utils.commons.Commons.TVIN_LABEL;

public class TypeVin implements EntityBundleInterface{

    private Long typeVinId;
    private String typeVinLibelle;

    public TypeVin() {
    }

    public TypeVin(Bundle bundle) {
        this.typeVinId = bundle.getLong(TVIN_ID);
        this.typeVinLibelle = bundle.getString(TVIN_LABEL);
    }

    public Long getTypeVinId() {
        return typeVinId;
    }

    public void setTypeVinId(Long typeVinId) {
        this.typeVinId = typeVinId;
    }

    public String getTypeVinLibelle() {
        return typeVinLibelle;
    }

    public void setTypeVinLibelle(String typeVinLibelle) {
        this.typeVinLibelle = typeVinLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeVin typeVin = (TypeVin) o;

        if (typeVinId != null ? !typeVinId.equals(typeVin.typeVinId) : typeVin.typeVinId != null)
            return false;
        return typeVinLibelle != null ? typeVinLibelle.equals(typeVin.typeVinLibelle) : typeVin.typeVinLibelle == null;
    }

    @Override
    public int hashCode() {
        int result = typeVinId != null ? typeVinId.hashCode() : 0;
        result = 31 * result + (typeVinLibelle != null ? typeVinLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TypeVin{" +
                "typeVinId=" + typeVinId +
                ", typeVinLibelle='" + typeVinLibelle + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(TVIN_ID, typeVinId);
        bundle.putString(TVIN_LABEL, typeVinLibelle);
        return bundle;
    }
}
