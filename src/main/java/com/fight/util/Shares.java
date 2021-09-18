package com.fight.util;

/**
 * @className: Shares.java
 * @description: 股票实体
 * @author: cp3
 * @date: 2021/9/18 11:03
 * @version 1.0
 */
public class Shares {
    private String name;
    private String resPrice;
    private String supportPrice;
    private String endPrice;

    public String getName() {
        return name;
    }

    public Shares setName(String name) {
        this.name = name;
        return this;
    }

    public String getResPrice() {
        return resPrice;
    }

    public Shares setResPrice(String resPrice) {
        this.resPrice = resPrice;
        return this;
    }

    public String getSupportPrice() {
        return supportPrice;
    }

    public Shares setSupportPrice(String supportPrice) {
        this.supportPrice = supportPrice;
        return this;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public Shares setEndPrice(String endPrice) {
        this.endPrice = endPrice;
        return this;
    }

    @Override
    public String toString() {
        return "Shares{" +
                "name='" + name + '\'' +
                ", resPrice='" + resPrice + '\'' +
                ", endPrice='" + endPrice + '\'' +
                ", supportPrice='" + supportPrice + '\'' +
                '}';
    }
}
