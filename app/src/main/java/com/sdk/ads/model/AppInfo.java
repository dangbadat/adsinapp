package com.sdk.ads.model;


public class AppInfo {
    private int id_ads;
    private String package_name;
    private String title;
    private String short_des;
    private String icon;
    private String cover;
    private ScreenShot screenShot;
    private int is_more_apps;
    private int is_back_apps;
    private int is_full;
    private int is_popup;
    private int is_banner;

    public String getShort_des() {
        return short_des;
    }

    public void setShort_des(String short_des) {
        this.short_des = short_des;
    }

    public int getId_ads() {
        return id_ads;
    }

    public void setId_ads(int id_ads) {
        this.id_ads = id_ads;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ScreenShot getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(ScreenShot screenShot) {
        this.screenShot = screenShot;
    }

    public int getIs_more_apps() {
        return is_more_apps;
    }

    public void setIs_more_apps(int is_more_apps) {
        this.is_more_apps = is_more_apps;
    }

    public int getIs_back_apps() {
        return is_back_apps;
    }

    public void setIs_back_apps(int is_back_apps) {
        this.is_back_apps = is_back_apps;
    }

    public int getIs_full() {
        return is_full;
    }

    public void setIs_full(int is_full) {
        this.is_full = is_full;
    }

    public int getIs_popup() {
        return is_popup;
    }

    public void setIs_popup(int is_popup) {
        this.is_popup = is_popup;
    }

    public int getIs_banner() {
        return is_banner;
    }

    public void setIs_banner(int is_banner) {
        this.is_banner = is_banner;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id_ads=" + id_ads +
                ", package_name='" + package_name + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", cover='" + cover + '\'' +
                ", screenShot=" + screenShot.toString() +
                ", is_more_apps=" + is_more_apps +
                ", is_back_apps=" + is_back_apps +
                ", is_full=" + is_full +
                ", is_popup=" + is_popup +
                ", is_banner=" + is_banner +
                '}';
    }
}
