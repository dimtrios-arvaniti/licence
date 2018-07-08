package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.R_ID;
import static com.example.dim.licence.utils.commons.Commons.R_LIBELLE;

public class Region implements EntityBundleInterface {

    private long regionId;
    private String regionLibelle;

    public Region() {
    }

    public Region(Bundle bundle) {
        if (bundle != null) {
            regionId = bundle.getLong(R_ID);
            regionLibelle = bundle.getString(R_LIBELLE);
        }

    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRegionLibelle() {
        return regionLibelle;
    }

    public void setRegionLibelle(String regionLibelle) {
        this.regionLibelle = regionLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        if (regionId != region.regionId) return false;
        return regionLibelle != null ? regionLibelle.equals(region.regionLibelle) : region.regionLibelle == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (regionId ^ (regionId >>> 32));
        result = 31 * result + (regionLibelle != null ? regionLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionId=" + regionId +
                ", regionLibelle='" + regionLibelle + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(R_ID, regionId);
        bundle.putString(R_LIBELLE, regionLibelle);
        return bundle;
    }
}
