package ru.ilin.dto;

public class BuySharesRequest {
    private String userId;
    private String companyId;
    private int amount;


    public BuySharesRequest(String userId, String companyId, int amount) {
        this.userId = userId;
        this.companyId = companyId;
        this.amount = amount;
    }

    public BuySharesRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
