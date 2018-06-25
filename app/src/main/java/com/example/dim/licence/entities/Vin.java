package com.example.dim.licence.entities;

import java.util.Date;

public class Vin {

    private Integer vinId;
    private String vinLibelle;
    private Domaine vinDomaine;
    private TypeVin vinType;
    private Vigneron vinVigneron;
    private Double vinPrix;
    private Date vinAnnee;
    private Boolean vinFavoris;
    private Date vinAnneeMax;
    private String vinCommentaire;
    private Integer vinQte;

    public Vin() {
    }

    public Integer getVinId() {
        return vinId;
    }

    public void setVinId(Integer vinId) {
        this.vinId = vinId;
    }

    public String getVinLibelle() {
        return vinLibelle;
    }

    public void setVinLibelle(String vinLibelle) {
        this.vinLibelle = vinLibelle;
    }

    public Domaine getVinDomaine() {
        return vinDomaine;
    }

    public void setVinDomaine(Domaine vinDomaine) {
        this.vinDomaine = vinDomaine;
    }

    public TypeVin getVinType() {
        return vinType;
    }

    public void setVinType(TypeVin vinType) {
        this.vinType = vinType;
    }

    public Vigneron getVinVigneron() {
        return vinVigneron;
    }

    public void setVinVigneron(Vigneron vinVigneron) {
        this.vinVigneron = vinVigneron;
    }

    public Double getVinPrix() {
        return vinPrix;
    }

    public void setVinPrix(Double vinPrix) {
        this.vinPrix = vinPrix;
    }

    public Date getVinAnnee() {
        return vinAnnee;
    }

    public void setVinAnnee(Date vinAnnee) {
        this.vinAnnee = vinAnnee;
    }

    public Boolean getVinFavoris() {
        return vinFavoris;
    }

    public void setVinFavoris(Boolean vinFavoris) {
        this.vinFavoris = vinFavoris;
    }

    public Date getVinAnneeMax() {
        return vinAnneeMax;
    }

    public void setVinAnneeMax(Date vinAnneeMax) {
        this.vinAnneeMax = vinAnneeMax;
    }

    public String getVinCommentaire() {
        return vinCommentaire;
    }

    public void setVinCommentaire(String vinCommentaire) {
        this.vinCommentaire = vinCommentaire;
    }

    public Integer getVinQte() {
        return vinQte;
    }

    public void setVinQte(Integer vinQte) {
        this.vinQte = vinQte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vin vin = (Vin) o;

        if (vinId != null ? !vinId.equals(vin.vinId) : vin.vinId != null) return false;
        if (vinLibelle != null ? !vinLibelle.equals(vin.vinLibelle) : vin.vinLibelle != null)
            return false;
        if (vinDomaine != null ? !vinDomaine.equals(vin.vinDomaine) : vin.vinDomaine != null)
            return false;
        if (vinType != null ? !vinType.equals(vin.vinType) : vin.vinType != null) return false;
        if (vinVigneron != null ? !vinVigneron.equals(vin.vinVigneron) : vin.vinVigneron != null)
            return false;
        if (vinPrix != null ? !vinPrix.equals(vin.vinPrix) : vin.vinPrix != null) return false;
        if (vinAnnee != null ? !vinAnnee.equals(vin.vinAnnee) : vin.vinAnnee != null) return false;
        if (vinFavoris != null ? !vinFavoris.equals(vin.vinFavoris) : vin.vinFavoris != null)
            return false;
        if (vinAnneeMax != null ? !vinAnneeMax.equals(vin.vinAnneeMax) : vin.vinAnneeMax != null)
            return false;
        if (vinCommentaire != null ? !vinCommentaire.equals(vin.vinCommentaire) : vin.vinCommentaire != null)
            return false;
        return vinQte != null ? vinQte.equals(vin.vinQte) : vin.vinQte == null;
    }

    @Override
    public int hashCode() {
        int result = vinId != null ? vinId.hashCode() : 0;
        result = 31 * result + (vinLibelle != null ? vinLibelle.hashCode() : 0);
        result = 31 * result + (vinDomaine != null ? vinDomaine.hashCode() : 0);
        result = 31 * result + (vinType != null ? vinType.hashCode() : 0);
        result = 31 * result + (vinVigneron != null ? vinVigneron.hashCode() : 0);
        result = 31 * result + (vinPrix != null ? vinPrix.hashCode() : 0);
        result = 31 * result + (vinAnnee != null ? vinAnnee.hashCode() : 0);
        result = 31 * result + (vinFavoris != null ? vinFavoris.hashCode() : 0);
        result = 31 * result + (vinAnneeMax != null ? vinAnneeMax.hashCode() : 0);
        result = 31 * result + (vinCommentaire != null ? vinCommentaire.hashCode() : 0);
        result = 31 * result + (vinQte != null ? vinQte.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vin{" +
                "vinId=" + vinId +
                ", vinLibelle='" + vinLibelle + '\'' +
                ", vinDomaine=" + vinDomaine +
                ", vinType=" + vinType +
                ", vinVigneron=" + vinVigneron +
                ", vinPrix=" + vinPrix +
                ", vinAnnee=" + vinAnnee +
                ", vinFavoris=" + vinFavoris +
                ", vinAnneeMax=" + vinAnneeMax +
                ", vinCommentaire='" + vinCommentaire + '\'' +
                ", vinQte=" + vinQte +
                '}';
    }
}
