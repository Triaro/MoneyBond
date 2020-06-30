package com.example.moneybond40.model;

public class Name {

    private  int id;

    private  String name;
    private String money;
    private String number;
    private int colorStatus;
    private String status;

    public Name(int id) {
        this.id=id;
    }
    public Name(String name, String money) {
        this.name = name;
        this.money = money;
    }

    public Name(int id, String name, String number) {
        this.id=id;
        this.name = name;
        this.number = number;
    }
    public Name(int id, String name, String money, String number) {
        this.id=id;
        this.name = name;
        this.money = money;
        this.number=number;
    }
    public Name()
    {}

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getMoney() {
        return money;
    }

    public String getNumber() { return number; }

    public int getColorStatus() { return colorStatus; }

    public String getStatus() { return status; }

    public void setId(int id) {this.id = id;  }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(String money) {this.money = money; }

    public void setNumber(String number) {this.number = number; }

    public void setColorStatus(int colorStatus) {this.colorStatus = colorStatus; }

    public void setStatus(String status) {this.status = status; }
}
