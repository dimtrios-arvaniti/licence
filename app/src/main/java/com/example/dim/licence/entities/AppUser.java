package com.example.dim.licence.entities;

public class AppUser {

    private Long appUserId; // ext id
    private long appUserCaveId; // user external cave id
    private String appUserMail; //
    private boolean appUserMaster;
    private String appUserPwd; // encrypted
    private String appUserApiKey; // for ws vignerons and vins use


    public AppUser() {
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public long getAppUserCaveId() {
        return appUserCaveId;
    }

    public void setAppUserCaveId(long appUserCaveId) {
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

    public boolean isAppUserMaster() {
        return appUserMaster;
    }

    public void setAppUserMaster(boolean appUserMaster) {
        this.appUserMaster = appUserMaster;
    }

    public String getAppUserPwd() {
        return appUserPwd;
    }

    public void setAppUserPwd(String appUserPwd) {
        this.appUserPwd = appUserPwd;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser appUser = (AppUser) o;

        if (appUserCaveId != appUser.appUserCaveId) return false;
        if (appUserMaster != appUser.appUserMaster) return false;
        if (appUserId != null ? !appUserId.equals(appUser.appUserId) : appUser.appUserId != null)
            return false;
        if (appUserMail != null ? !appUserMail.equals(appUser.appUserMail) : appUser.appUserMail != null)
            return false;
        if (appUserPwd != null ? !appUserPwd.equals(appUser.appUserPwd) : appUser.appUserPwd != null)
            return false;
        return appUserApiKey != null ? appUserApiKey.equals(appUser.appUserApiKey) : appUser.appUserApiKey == null;
    }

    @Override
    public int hashCode() {
        int result = appUserId != null ? appUserId.hashCode() : 0;
        result = 31 * result + (int) (appUserCaveId ^ (appUserCaveId >>> 32));
        result = 31 * result + (appUserMail != null ? appUserMail.hashCode() : 0);
        result = 31 * result + (appUserMaster ? 1 : 0);
        result = 31 * result + (appUserPwd != null ? appUserPwd.hashCode() : 0);
        result = 31 * result + (appUserApiKey != null ? appUserApiKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "appUserId=" + appUserId +
                ", appUserCaveId=" + appUserCaveId +
                ", appUserMail='" + appUserMail + '\'' +
                ", appUserMaster=" + appUserMaster +
                ", appUserPwd='" + appUserPwd + '\'' +
                ", appUserApiKey='" + appUserApiKey + '\'' +
                '}';
    }
}
