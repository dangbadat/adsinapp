package com.sdk.ads.model;

public class ScreenShot {
    private String ss1;
    private String ss2;
    private String ss3;

    public ScreenShot() {
    }

    public String getSs1() {
        return ss1;
    }

    public void setSs1(String ss1) {
        this.ss1 = ss1;
    }

    public String getSs2() {
        return ss2;
    }

    public void setSs2(String ss2) {
        this.ss2 = ss2;
    }

    public String getSs3() {
        return ss3;
    }

    public void setSs3(String ss3) {
        this.ss3 = ss3;
    }

    @Override
    public String toString() {
        return "ScreenShot{" +
                "ss1='" + ss1 + '\'' +
                ", ss2='" + ss2 + '\'' +
                ", ss3='" + ss3 + '\'' +
                '}';
    }
}
