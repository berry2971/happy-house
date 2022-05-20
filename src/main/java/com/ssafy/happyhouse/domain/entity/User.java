package com.ssafy.happyhouse.domain.entity;

public class User {

    private String id;
    private String pw;
    private String name;
    private String tel;
    private String bmk_addr_lv1;
    private String bmk_addr_lv2;
    private String bmk_addr_lv3;
    private String bmk_addr_lv4;
    private EnumRole role;

    public User() { }

    public User(String id, String pw, String name, String tel, String bmk_addr_lv1, String bmk_addr_lv2, String bmk_addr_lv3, String bmk_addr_lv4, EnumRole role) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.tel = tel;
        this.bmk_addr_lv1 = bmk_addr_lv1;
        this.bmk_addr_lv2 = bmk_addr_lv2;
        this.bmk_addr_lv3 = bmk_addr_lv3;
        this.bmk_addr_lv4 = bmk_addr_lv4;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBmk_addr_lv1() {
        return bmk_addr_lv1;
    }

    public void setBmk_addr_lv1(String bmk_addr_lv1) {
        this.bmk_addr_lv1 = bmk_addr_lv1;
    }

    public String getBmk_addr_lv2() {
        return bmk_addr_lv2;
    }

    public void setBmk_addr_lv2(String bmk_addr_lv2) {
        this.bmk_addr_lv2 = bmk_addr_lv2;
    }

    public String getBmk_addr_lv3() {
        return bmk_addr_lv3;
    }

    public void setBmk_addr_lv3(String bmk_addr_lv3) {
        this.bmk_addr_lv3 = bmk_addr_lv3;
    }

    public String getBmk_addr_lv4() {
        return bmk_addr_lv4;
    }

    public void setBmk_addr_lv4(String bmk_addr_lv4) {
        this.bmk_addr_lv4 = bmk_addr_lv4;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

}
