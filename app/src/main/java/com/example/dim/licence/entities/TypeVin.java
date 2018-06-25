package com.example.dim.licence.entities;

public class TypeVin {

    private Integer typeVinId;
    private String typeVinLibelle;

    public TypeVin() {
    }


    public Integer getTypeVinId() {
        return typeVinId;
    }

    public void setTypeVinId(Integer typeVinId) {
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
}
