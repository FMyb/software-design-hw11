package ru.ilin.model;

public class Company {
    private String id;

    private String name;

    private int sharesCount;

    private double sharesPrice;

    public Company(String id, String name, int sharesCount, double sharesPrice) {
        this.id = id;
        this.name = name;
        this.sharesCount = sharesCount;
        this.sharesPrice = sharesPrice;
    }

    public Company() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
    }

    public double getSharesPrice() {
        return sharesPrice;
    }

    public void setSharesPrice(double sharesPrice) {
        this.sharesPrice = sharesPrice;
    }
}
