package com.example.dim.licence.entities;

public class AppUser {

    private Integer appUserId;
    private Integer appUserCaveId;
    private String appUserMail;
    private String appUserApiKey;


    public AppUser() {
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Integer getAppUserCaveId() {
        return appUserCaveId;
    }

    public void setAppUserCaveId(Integer appUserCaveId) {
        this.appUserCaveId = appUserCaveId;
    }

    public String getAppUserMail() {
        return appUserMail;
    }

    public void setAppUserMail(String appUserMail) {
        this.appUserMail = appUserMail;
    }

    public String getAppUserApiKey() {
        return appUserApiKey;
    }

    public void setAppUserApiKey(String appUserApiKey) {
        this.appUserApiKey = appUserApiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser appUser = (AppUser) o;

        if (appUserId != null ? !appUserId.equals(appUser.appUserId) : appUser.appUserId != null)
            return false;
        if (appUserCaveId != null ? !appUserCaveId.equals(appUser.appUserCaveId) : appUser.appUserCaveId != null)
            return false;
        if (appUserMail != null ? !appUserMail.equals(appUser.appUserMail) : appUser.appUserMail != null)
            return false;
        return appUserApiKey != null ? appUserApiKey.equals(appUser.appUserApiKey) : appUser.appUserApiKey == null;
    }

    @Override
    public int hashCode() {
        int result = appUserId != null ? appUserId.hashCode() : 0;
        result = 31 * result + (appUserCaveId != null ? appUserCaveId.hashCode() : 0);
        result = 31 * result + (appUserMail != null ? appUserMail.hashCode() : 0);
        result = 31 * result + (appUserApiKey != null ? appUserApiKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", appUserCaveId=" + appUserCaveId +
                ", appUserMail='" + appUserMail + '\'' +
                ", appUserApiKey='" + appUserApiKey + '\'' +
                '}';
    }
}
