package ru.ilin.dto;

public class RegisterUserRequest {
    private double balance;

    public RegisterUserRequest(double balance) {
        this.balance = balance;
    }

    public RegisterUserRequest() {
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
