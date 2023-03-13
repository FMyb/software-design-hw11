package ru.ilin.model;

public class Share {
    private String companyId;

    private String userId;

    private int amount;

    public Share(String companyId, String userId, int amount) {
        this.companyId = companyId;
        this.userId = userId;
        this.amount = amount;
    }

    public Share() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
