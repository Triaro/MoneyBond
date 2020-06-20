package com.example.moneybond40.model;

public class LentName {

    private  int id;
    private  String lentName;
    private String lentMoney;

    public LentName(int id) {
        this.id=id;
    }
    public LentName( String Lentame, String lentMoney) {
        this.lentName = lentName;
        this.lentMoney = lentMoney;
    }
    public LentName(int id, String lentName, String lentMoney) {
        this.id=id;
        this.lentName = lentName;
        this.lentMoney = lentMoney;
    }
    public LentName()
    {}

    public int getId() {
        return id;
    }

    public String getLentName() {
        return lentName;
    }

    public String getLentMoney() {
        return lentMoney;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLentName(String lentName) {
        this.lentName = lentName;
    }

    public void setLentMoney(String lentMoney) {
        this.lentMoney = lentMoney;
    }
}
