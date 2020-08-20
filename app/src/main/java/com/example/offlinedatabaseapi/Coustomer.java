package com.example.offlinedatabaseapi;

public class Coustomer {
    private int id;
    private String name;
    private int mobile;

    public Coustomer() {
    }

    public Coustomer(int id, String name, int mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMobile() {
        return mobile;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
}
