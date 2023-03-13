package ru.ilin.dto;

import java.util.List;

public class UserSharesResponse {
    private List<ShareResponse> shareResponse;

    public UserSharesResponse(List<ShareResponse> shareResponse) {
        this.shareResponse = shareResponse;
    }

    public UserSharesResponse() {
    }

    public List<ShareResponse> getShareResponse() {
        return shareResponse;
    }

    public void setShareResponse(List<ShareResponse> shareResponse) {
        this.shareResponse = shareResponse;
    }

    public static class ShareResponse {
        private String userId;

        private String companyId;

        private int amount;

        private double price;

        public ShareResponse(String userId, String companyId, int amount, double price) {
            this.userId = userId;
            this.companyId = companyId;
            this.amount = amount;
            this.price = price;
        }

        public ShareResponse() {
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
