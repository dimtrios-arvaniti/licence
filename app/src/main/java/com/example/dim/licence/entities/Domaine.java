package com.example.dim.licence.entities;

public class Domaine {

    private Integer domaineId;
    private String domaineLibelle;

    public Domaine() {
    }

    public Integer getDomaineId() {
        return domaineId;
    }

    public void setDomaineId(Integer domaineId) {
        this.domaineId = domaineId;
    }

    public String getDomaineLibelle() {
        return domaineLibelle;
    }

    public void setDomaineLibelle(String domaineLibelle) {
        this.domaineLibelle = domaineLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domaine domaine = (Domaine) o;

        if (domaineId != null ? !domaineId.equals(domaine.domaineId) : domaine.domaineId != null)
            return false;
        return domaineLibelle != null ? domaineLibelle.equals(domaine.domaineLibelle) : domaine.domaineLibelle == null;
    }

    @Override
    public int hashCode() {
        int result = domaineId != null ? domaineId.hashCode() : 0;
        result = 31 * result + (domaineLibelle != null ? domaineLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Domaine{" +
                "domaineId=" + domaineId +
                ", domaineLibelle='" + domaineLibelle + '\'' +
                '}';
    }
}
