package com.example.moneybond40.model;

public class Name {

    private  int id;
    private  int idH;
    private  String name;
    private String money;
    private String number;
    private String amount;
    private String time;

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
    public int getIdH() {
        return idH;
    }

    public String getName() {
        return name;
    }

    public String getMoney() {
        return money;
    }

    public String getNumber() { return number; }

    public String getAmount() { return amount; }

    public String getTime() { return time; }

    public void setId(int id) {this.id = id;  }

    public void setIdH(int idH) {this.idH = idH;  }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(String money) {this.money = money; }

    public void setNumber(String number) {this.number = number; }

    public void setAmount(String amount) {this.amount = amount; }

    public void setTime(String time) {this.time = time; }
}
