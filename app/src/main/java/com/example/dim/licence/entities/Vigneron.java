package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.VIGN_COMMENT;
import static com.example.dim.licence.utils.commons.Commons.VIGN_DOMAINE;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FAX;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FIXE;
import static com.example.dim.licence.utils.commons.Commons.VIGN_GEOLOC;
import static com.example.dim.licence.utils.commons.Commons.VIGN_ID;
import static com.example.dim.licence.utils.commons.Commons.VIGN_LABEL;
import static com.example.dim.licence.utils.commons.Commons.VIGN_MAIL;
import static com.example.dim.licence.utils.commons.Commons.VIGN_MOBILE;
import static com.example.dim.licence.utils.commons.Commons.VIGN_TEL;


public class Vigneron implements EntityBundleInterface {

    private Long vigneronId;
    private String vigneronLibelle;
    private String vigneronDomaine;
    private Geolocalisation vigneronGeoloc;
    private String vigneronFixe;
    private String vigneronMobile;
    private String vigneronMail;
    private String vigneronFax;
    private String vigneronComment;

    public Vigneron() {
        vigneronGeoloc = new Geolocalisation();
    }


    public Vigneron(Bundle bundle) {
        vigneronId = bundle.getLong(VIGN_ID, 0);
        vigneronLibelle= bundle.getString(VIGN_LABEL, "");
        vigneronDomaine = bundle.getString(VIGN_DOMAINE, "");
        vigneronFixe = bundle.getString(VIGN_FIXE, "");
        vigneronMobile = bundle.getString(VIGN_MOBILE, "");
        vigneronMail = bundle.getString(VIGN_MAIL, "");
        vigneronFax = bundle.getString(VIGN_FAX, "");
        vigneronComment = bundle.getString(VIGN_COMMENT, "");

        vigneronGeoloc = new Geolocalisation();
        if (bundle.getBundle(VIGN_GEOLOC) != null) {
            Geolocalisation geoloc = new Geolocalisation(bundle.getBundle(VIGN_GEOLOC));
            this.vigneronGeoloc = geoloc;
        }
    }

    public Long getVigneronId() {
        return vigneronId;
    }

    public void setVigneronId(Long vigneronId) {
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
        bundle.putLong(VIGN_ID, vigneronId);
        bundle.putString(VIGN_LABEL, vigneronLibelle);
        bundle.putString(VIGN_DOMAINE, vigneronDomaine);
        bundle.putString(VIGN_TEL, vigneronFixe);
        bundle.putString(VIGN_MOBILE, vigneronMobile);
        bundle.putString(VIGN_MAIL, vigneronMail);
        bundle.putString(VIGN_FAX, vigneronFax);
        bundle.putString(VIGN_COMMENT, vigneronComment);
        if (vigneronGeoloc != null) {
            bundle.putBundle(VIGN_GEOLOC, vigneronGeoloc.entityToBundle());
        }

        return bundle;
    }

}

