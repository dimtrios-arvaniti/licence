package com.example.dim.licence.entities;
public class Action {

    private Integer actionId;
    private String actionLibelle;

    public Action() {
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
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
}
