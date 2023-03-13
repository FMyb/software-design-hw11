package ru.ilin.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private double balance;

    private List<Share> shares;

    public User(String id, double balance, List<Share> shares) {
        this.id = id;
        this.balance = balance;
        this.shares = new ArrayList<>(shares);
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }
}
