package ru.ilin.dto;

public class AddCompanyRequest {
    private String name;
    private int sharesCount;
    private double sharesPrice;

    public AddCompanyRequest() {
    }

    public AddCompanyRequest(String name, int sharesCount, double sharesPrice) {
        this.name = name;
        this.sharesCount = sharesCount;
        this.sharesPrice = sharesPrice;
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
