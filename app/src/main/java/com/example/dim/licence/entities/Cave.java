package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.CAVE_FAVORIS;
import static com.example.dim.licence.utils.commons.Commons.CAVE_ID;
import static com.example.dim.licence.utils.commons.Commons.CAVE_QTE;
import static com.example.dim.licence.utils.commons.Commons.CAVE_VIN;

public class Cave implements EntityBundleInterface {

    private Long caveId;
    private Vin caveVin;
    private Integer caveQuantite;
    private boolean caveFavoris;

    public Cave() {
        caveVin = new Vin();
    }

    public Cave(Bundle bundle) {
        caveVin = new Vin();
        this.caveId = bundle.getLong(CAVE_ID);
        this.caveVin = new Vin(bundle.getBundle(CAVE_VIN));
        this.caveFavoris = bundle.getBoolean(CAVE_FAVORIS);
        this.caveQuantite = bundle.getInt(CAVE_QTE);
    }

    public Long getCaveId() {
        return caveId;
    }

    public void setCaveId(Long caveId) {
        this.caveId = caveId;
    }

    public Vin getCaveVin() {
        return caveVin;
    }

    public void setCaveVin(Vin caveVin) {
        this.caveVin = caveVin;
    }

    public Integer getCaveQuantite() {
        return caveQuantite;
    }

    public void setCaveQuantite(Integer caveQuantite) {
        this.caveQuantite = caveQuantite;
    }

    public boolean isCaveFavoris() {
        return caveFavoris;
    }

    public void setCaveFavoris(boolean caveFavoris) {
        this.caveFavoris = caveFavoris;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cave cave = (Cave) o;

        if (caveFavoris != cave.caveFavoris) return false;
        if (caveId != null ? !caveId.equals(cave.caveId) : cave.caveId != null) return false;
        if (caveVin != null ? !caveVin.equals(cave.caveVin) : cave.caveVin != null) return false;
        return caveQuantite != null ? caveQuantite.equals(cave.caveQuantite) : cave.caveQuantite == null;
    }

    @Override
    public int hashCode() {
        int result = caveId != null ? caveId.hashCode() : 0;
        result = 31 * result + (caveVin != null ? caveVin.hashCode() : 0);
        result = 31 * result + (caveQuantite != null ? caveQuantite.hashCode() : 0);
        result = 31 * result + (caveFavoris ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cave{" +
                "caveId=" + caveId +
                ", caveVin=" + caveVin +
                ", caveQuantite=" + caveQuantite +
                ", caveFavoris=" + caveFavoris +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(CAVE_ID, caveId);
        bundle.putBundle(CAVE_VIN, caveVin.entityToBundle());
        bundle.putBoolean(CAVE_FAVORIS, caveFavoris);
        bundle.putInt(CAVE_QTE, caveQuantite);
        return bundle;
    }
}
