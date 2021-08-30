package com.fight.util;

public class Future {
    private String name;
    private String resNumber;
    private String supportNumber;

    public String getName() {
        return name;
    }

    public Future setName(String name) {
        this.name = name;
        return this;
    }

    public String getResNumber() {
        return resNumber;
    }

    public Future setResNumber(String resNumber) {
        this.resNumber = resNumber;
        return this;
    }

    public String getSupportNumber() {
        return supportNumber;
    }

    public Future setSupportNumber(String supportNumber) {
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
