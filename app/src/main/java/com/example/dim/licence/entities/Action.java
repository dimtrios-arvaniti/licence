package com.example.dim.licence.entities;

import android.os.Bundle;

import com.example.dim.licence.utils.interfaces.EntityBundleInterface;

import static com.example.dim.licence.utils.commons.Commons.ACT_ID;
import static com.example.dim.licence.utils.commons.Commons.ACT_LABEL;

public class Action implements EntityBundleInterface {

    private Long actionId;
    private String actionLibelle;

    public Action() {
    }

    public Action(Bundle bundle) {
        this.actionId = bundle.getLong(ACT_ID);
        this.actionLibelle = (bundle.getString(ACT_LABEL));
    }


    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionLibelle() {
        return actionLibelle;
    }

    public void setActionLibelle(String actionLibelle) {
        this.actionLibelle = actionLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        if (actionId != null ? !actionId.equals(action.actionId) : action.actionId != null)
            return false;
        return actionLibelle != null ? actionLibelle.equals(action.actionLibelle) : action.actionLibelle == null;
    }

    @Override
    public int hashCode() {
        int result = actionId != null ? actionId.hashCode() : 0;
        result = 31 * result + (actionLibelle != null ? actionLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionId=" + actionId +
                ", actionLibelle='" + actionLibelle + '\'' +
                '}';
    }

    @Override
    public Bundle entityToBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(ACT_ID, actionId);
        bundle.putString(ACT_LABEL, actionLibelle);
        return bundle;
    }
}
