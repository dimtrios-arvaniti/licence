package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.V_COMMENT;
import static com.example.dim.licence.utils.commons.Commons.V_DOMAINE;
import static com.example.dim.licence.utils.commons.Commons.V_FAX;
import static com.example.dim.licence.utils.commons.Commons.V_GEOLOC;
import static com.example.dim.licence.utils.commons.Commons.V_ID;
import static com.example.dim.licence.utils.commons.Commons.V_LABEL;
import static com.example.dim.licence.utils.commons.Commons.V_MAIL;
import static com.example.dim.licence.utils.commons.Commons.V_MOBILE;
import static com.example.dim.licence.utils.commons.Commons.V_TEL;


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
        this.vigneronId = bundle.getLong(V_ID, 0);
        this.vigneronLibelle= bundle.getString(V_LABEL, "");
        this.vigneronDomaine = bundle.getString(V_DOMAINE, "");
        this.vigneronFixe = bundle.getString(V_TEL, "");
        this.vigneronMobile = bundle.getString(V_MOBILE, "");
        this.vigneronMail = bundle.getString(V_MAIL, "");
        this.vigneronFax = bundle.getString(V_FAX, "");
        this.vigneronComment = bundle.getString(V_COMMENT, "");

        vigneronGeoloc = new Geolocalisation();
        if (bundle.getBundle(V_GEOLOC) != null) {
            Geolocalisation geoloc = new Geolocalisation(bundle.getBundle(V_GEOLOC));
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
        bundle.putLong(V_ID, vigneronId);
        bundle.putString(V_LABEL, vigneronLibelle);
        bundle.putString(V_DOMAINE, vigneronDomaine);
        bundle.putString(V_TEL, vigneronFixe);
        bundle.putString(V_MOBILE, vigneronMobile);
        bundle.putString(V_MAIL, vigneronMail);
        bundle.putString(V_FAX, vigneronFax);
        bundle.putString(V_COMMENT, vigneronComment);
        if (vigneronGeoloc != null) {
            bundle.putBundle(V_GEOLOC, vigneronGeoloc.entityToBundle());
        }

        return bundle;
    }

}

