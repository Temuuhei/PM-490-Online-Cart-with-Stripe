package com.pm490.PM490.model;

public class ProductReport {
    private String username;
    private Double salesamount;

    public ProductReport(String username, Double salesamount) {
        this.username = username;
        this.salesamount = salesamount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getSalesamount() {
        return salesamount;
    }

    public void setSalesamount(Double salesamount) {
        this.salesamount = salesamount;
    }

    @Override
    public String toString() {
        return "ProductReport{" +
                "username='" + username + '\'' +
                ", salesamount=" + salesamount +
                '}';
    }
}
