package com.fight.util;

public class Shares {
    private String name;
    private String resNumber;
    private String supportNumber;

    public String getName() {
        return name;
    }

    public Shares setName(String name) {
        this.name = name;
        return this;
    }

    public String getResNumber() {
        return resNumber;
    }

    public Shares setResNumber(String resNumber) {
        this.resNumber = resNumber;
        return this;
    }

    public String getSupportNumber() {
        return supportNumber;
    }

    public Shares setSupportNumber(String supportNumber) {
        this.supportNumber = supportNumber;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "name:'" + name + '\'' +
                ", resNumber:'" + resNumber + '\'' +
                ", supportNumber:'" + supportNumber + '\'' +
                '}';
    }
}
