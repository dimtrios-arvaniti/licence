package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.EntityBundleInterface;


public class Vigneron implements EntityBundleInterface<Vigneron> {

    private Integer vigneronId;
    private String vigneronLibelle;
    private String vigneronDomaine;
    private Geolocalisation vigneronGeoloc;
    private String vigneronFixe;
    private String vigneronMobile;
    private String vigneronMail;
    private String vigneronFax;
    private String vigneronComment;

    public Vigneron() {
    }

    public Integer getVigneronId() {
        return vigneronId;
    }

    public void setVigneronId(Integer vigneronId) {
        this.vigneronId = vigneronId;
    }

    public String getVigneronLibelle() {
        return vigneronLibelle;
    }

    public void setVigneronLibelle(String vigneronLibelle) {
        this.vigneronLibelle = vigneronLibelle;
    }

    public String getVigneronDomaine() {
        return vigneronDomaine;
    }

    public void setVigneronDomaine(String vigneronDomaine) {
        this.vigneronDomaine = vigneronDomaine;
    }

    public Geolocalisation getVigneronGeoloc() {
        return vigneronGeoloc;
    }

    public void setVigneronGeoloc(Geolocalisation vigneronGeoloc) {
        this.vigneronGeoloc = vigneronGeoloc;
    }

    public String getVigneronFixe() {
        return vigneronFixe;
    }

    public void setVigneronFixe(String vigneronFixe) {
        this.vigneronFixe = vigneronFixe;
    }

    public String getVigneronMobile() {
        return vigneronMobile;
    }

    public void setVigneronMobile(String vigneronMobile) {
        this.vigneronMobile = vigneronMobile;
    }

    public String getVigneronMail() {
        return vigneronMail;
    }

    public void setVigneronMail(String vigneronMail) {
        this.vigneronMail = vigneronMail;
    }

    public String getVigneronFax() {
        return vigneronFax;
    }

    public void setVigneronFax(String vigneronFax) {
        this.vigneronFax = vigneronFax;
    }

    public String getVigneronComment() {
        return vigneronComment;
    }

    public void setVigneronComment(String vigneronComment) {
        this.vigneronComment = vigneronComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vigneron vigneron = (Vigneron) o;

        if (vigneronId != null ? !vigneronId.equals(vigneron.vigneronId) : vigneron.vigneronId != null)
            return false;
        if (vigneronLibelle != null ? !vigneronLibelle.equals(vigneron.vigneronLibelle) : vigneron.vigneronLibelle != null)
            return false;
        if (vigneronDomaine != null ? !vigneronDomaine.equals(vigneron.vigneronDomaine) : vigneron.vigneronDomaine != null)
            return false;
        if (vigneronGeoloc != null ? !vigneronGeoloc.equals(vigneron.vigneronGeoloc) : vigneron.vigneronGeoloc != null)
            return false;
        if (vigneronFixe != null ? !vigneronFixe.equals(vigneron.vigneronFixe) : vigneron.vigneronFixe != null)
            return false;
        if (vigneronMobile != null ? !vigneronMobile.equals(vigneron.vigneronMobile) : vigneron.vigneronMobile != null)
            return false;
        if (vigneronMail != null ? !vigneronMail.equals(vigneron.vigneronMail) : vigneron.vigneronMail != null)
            return false;
        if (vigneronFax != null ? !vigneronFax.equals(vigneron.vigneronFax) : vigneron.vigneronFax != null)
            return false;
        return vigneronComment != null ? vigneronComment.equals(vigneron.vigneronComment) : vigneron.vigneronComment == null;
    }

    @Override
    public int hashCode() {
        int result = vigneronId != null ? vigneronId.hashCode() : 0;
        result = 31 * result + (vigneronLibelle != null ? vigneronLibelle.hashCode() : 0);
        result = 31 * result + (vigneronDomaine != null ? vigneronDomaine.hashCode() : 0);
        result = 31 * result + (vigneronGeoloc != null ? vigneronGeoloc.hashCode() : 0);
        result = 31 * result + (vigneronFixe != null ? vigneronFixe.hashCode() : 0);
        result = 31 * result + (vigneronMobile != null ? vigneronMobile.hashCode() : 0);
        result = 31 * result + (vigneronMail != null ? vigneronMail.hashCode() : 0);
        result = 31 * result + (vigneronFax != null ? vigneronFax.hashCode() : 0);
        result = 31 * result + (vigneronComment != null ? vigneronComment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vigneron{" +
                "vigneronId=" + vigneronId +
                ", vigneronLibelle='" + vigneronLibelle + '\'' +
                ", vigneronDomaine='" + vigneronDomaine + '\'' +
                ", vigneronGeoloc=" + vigneronGeoloc +
                ", vigneronFixe='" + vigneronFixe + '\'' +
                ", vigneronMobile='" + vigneronMobile + '\'' +
                ", vigneronMail='" + vigneronMail + '\'' +
                ", vigneronFax='" + vigneronFax + '\'' +
                ", vigneronComment='" + vigneronComment + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(commons.V_ID, vigneronId);
        bundle.putString(commons.V_LABEL, vigneronLibelle);
        bundle.putString(commons.V_DOMAINE, vigneronDomaine);
        bundle.putString(commons.V_TEL, vigneronFixe);
        bundle.putString(commons.V_MOBILE, vigneronMobile);
        bundle.putString(commons.V_MAIL, vigneronMail);
        bundle.putString(commons.V_FAX, vigneronFax);
        bundle.putString(commons.V_COMMENT, vigneronComment);
        if (vigneronGeoloc != null) {
            bundle.putBundle(commons.V_GEOLOC, vigneronGeoloc.entityToBundle());
        }

        return bundle;
    }

    @Override
    public void entityFromBundle(Bundle bundle) {
        this.vigneronId = bundle.getInt(commons.V_ID, 0);
        this.vigneronLibelle= bundle.getString(commons.V_LABEL, "");
        this.vigneronDomaine = bundle.getString(commons.V_DOMAINE, "");
        this.vigneronFixe = bundle.getString(commons.V_TEL, "");
        this.vigneronMobile = bundle.getString(commons.V_MOBILE, "");
        this.vigneronMail = bundle.getString(commons.V_MAIL, "");
        this.vigneronFax = bundle.getString(commons.V_FAX, "");
        this.vigneronComment = bundle.getString(commons.V_COMMENT, "");

        if (bundle.getBundle(commons.V_GEOLOC) != null) {
            Geolocalisation geoloc = new Geolocalisation();
            geoloc.entityFromBundle(bundle.getBundle(commons.V_GEOLOC));
            this.vigneronGeoloc = geoloc;
        }

    }
}

