package com.example.dim.licence.entities;

public class Historique {

    private Integer historiqueId;
    private AppUser historiqueAppUser;
    private String historiqueDate;
    private boolean etat;
    private Action historiqueAction;
    private Vin historiqueAncienVin;
    private Vin historiqueNouveauVin;
    private Vigneron historiqueAncienVigneron;
    private Vigneron historiqueNouveauVigneron;

    public Historique() {
    }

    public Integer getHistoriqueId() {
        return historiqueId;
    }

    public void setHistoriqueId(Integer historiqueId) {
        this.historiqueId = historiqueId;
    }

    public AppUser getHistoriqueAppUser() {
        return historiqueAppUser;
    }

    public void setHistoriqueAppUser(AppUser historiqueAppUser) {
        this.historiqueAppUser = historiqueAppUser;
    }

    public String getHistoriqueDate() {
        return historiqueDate;
    }

    public void setHistoriqueDate(String historiqueDate) {
        this.historiqueDate = historiqueDate;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Action getHistoriqueAction() {
        return historiqueAction;
    }

    public void setHistoriqueAction(Action historiqueAction) {
        this.historiqueAction = historiqueAction;
    }

    public Vin getHistoriqueAncienVin() {
        return historiqueAncienVin;
    }

    public void setHistoriqueAncienVin(Vin historiqueAncienVin) {
        this.historiqueAncienVin = historiqueAncienVin;
    }

    public Vin getHistoriqueNouveauVin() {
        return historiqueNouveauVin;
    }

    public void setHistoriqueNouveauVin(Vin historiqueNouveauVin) {
        this.historiqueNouveauVin = historiqueNouveauVin;
    }

    public Vigneron getHistoriqueAncienVigneron() {
        return historiqueAncienVigneron;
    }

    public void setHistoriqueAncienVigneron(Vigneron historiqueAncienVigneron) {
        this.historiqueAncienVigneron = historiqueAncienVigneron;
    }

    public Vigneron getHistoriqueNouveauVigneron() {
        return historiqueNouveauVigneron;
    }

    public void setHistoriqueNouveauVigneron(Vigneron historiqueNouveauVigneron) {
        this.historiqueNouveauVigneron = historiqueNouveauVigneron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Historique that = (Historique) o;

        if (etat != that.etat) return false;
        if (historiqueId != null ? !historiqueId.equals(that.historiqueId) : that.historiqueId != null)
            return false;
        if (historiqueAppUser != null ? !historiqueAppUser.equals(that.historiqueAppUser) : that.historiqueAppUser != null)
            return false;
        if (historiqueDate != null ? !historiqueDate.equals(that.historiqueDate) : that.historiqueDate != null)
            return false;
        if (historiqueAction != null ? !historiqueAction.equals(that.historiqueAction) : that.historiqueAction != null)
            return false;
        if (historiqueAncienVin != null ? !historiqueAncienVin.equals(that.historiqueAncienVin) : that.historiqueAncienVin != null)
            return false;
        if (historiqueNouveauVin != null ? !historiqueNouveauVin.equals(that.historiqueNouveauVin) : that.historiqueNouveauVin != null)
            return false;
        if (historiqueAncienVigneron != null ? !historiqueAncienVigneron.equals(that.historiqueAncienVigneron) : that.historiqueAncienVigneron != null)
            return false;
        return historiqueNouveauVigneron != null ? historiqueNouveauVigneron.equals(that.historiqueNouveauVigneron) : that.historiqueNouveauVigneron == null;
    }

    @Override
    public int hashCode() {
        int result = historiqueId != null ? historiqueId.hashCode() : 0;
        result = 31 * result + (historiqueAppUser != null ? historiqueAppUser.hashCode() : 0);
        result = 31 * result + (historiqueDate != null ? historiqueDate.hashCode() : 0);
        result = 31 * result + (etat ? 1 : 0);
        result = 31 * result + (historiqueAction != null ? historiqueAction.hashCode() : 0);
        result = 31 * result + (historiqueAncienVin != null ? historiqueAncienVin.hashCode() : 0);
        result = 31 * result + (historiqueNouveauVin != null ? historiqueNouveauVin.hashCode() : 0);
        result = 31 * result + (historiqueAncienVigneron != null ? historiqueAncienVigneron.hashCode() : 0);
        result = 31 * result + (historiqueNouveauVigneron != null ? historiqueNouveauVigneron.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Historique{" +
                "historiqueId=" + historiqueId +
                ", historiqueAppUser=" + historiqueAppUser +
                ", historiqueDate='" + historiqueDate + '\'' +
                ", etat=" + etat +
                ", historiqueAction=" + historiqueAction +
                ", historiqueAncienVin=" + historiqueAncienVin +
                ", historiqueNouveauVin=" + historiqueNouveauVin +
                ", historiqueAncienVigneron=" + historiqueAncienVigneron +
                ", historiqueNouveauVigneron=" + historiqueNouveauVigneron +
                '}';
    }
}
