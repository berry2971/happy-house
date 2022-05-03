package com.ssafy.happyhousemay5.domain.entity;

public class User {

    private String id;
    private String pw;
    private String name;
    private String tel;
    private String bookmarkedAddressState;
    private String bookmarkedAddressCity;
    private String bookmarkedAddressTown;
    private String bookmarkedAddressVillage;
    private String bookmarkedAddressHamlet;

    public User() { }

    public User(String id, String pw, String name, String tel, String bookmarkedAddressState,
                String bookmarkedAddressCity, String bookmarkedAddressTown, String bookmarkedAddressVillage, String bookmarkedAddressHamlet) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.tel = tel;
        this.bookmarkedAddressState = bookmarkedAddressState;
        this.bookmarkedAddressCity = bookmarkedAddressCity;
        this.bookmarkedAddressTown = bookmarkedAddressTown;
        this.bookmarkedAddressVillage = bookmarkedAddressVillage;
        this.bookmarkedAddressHamlet = bookmarkedAddressHamlet;
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

    public String getBookmarkedAddressState() {
        return bookmarkedAddressState;
    }

    public void setBookmarkedAddressState(String bookmarkedAddressState) {
        this.bookmarkedAddressState = bookmarkedAddressState;
    }

    public String getBookmarkedAddressCity() {
        return bookmarkedAddressCity;
    }

    public void setBookmarkedAddressCity(String bookmarkedAddressCity) {
        this.bookmarkedAddressCity = bookmarkedAddressCity;
    }

    public String getBookmarkedAddressTown() {
        return bookmarkedAddressTown;
    }

    public void setBookmarkedAddressTown(String bookmarkedAddressTown) {
        this.bookmarkedAddressTown = bookmarkedAddressTown;
    }

    public String getBookmarkedAddressVillage() {
        return bookmarkedAddressVillage;
    }

    public void setBookmarkedAddressVillage(String bookmarkedAddressVillage) {
        this.bookmarkedAddressVillage = bookmarkedAddressVillage;
    }

    public String getBookmarkedAddressHamlet() {
        return bookmarkedAddressHamlet;
    }

    public void setBookmarkedAddressHamlet(String bookmarkedAddressHamlet) {
        this.bookmarkedAddressHamlet = bookmarkedAddressHamlet;
    }

}
