package ru.ilin.storage.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.ilin.dto.UserSharesResponse;
import ru.ilin.model.Company;
import ru.ilin.model.Share;
import ru.ilin.model.User;
import ru.ilin.storage.ExchangeStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class InMemExchangeStorage implements ExchangeStorage {
    private final HashMap<String, Company> companies;

    private final HashMap<String, User> users;

    public InMemExchangeStorage() {
        companies = new HashMap<>();
        users = new HashMap<>();
    }

    @Override
    public synchronized Company saveCompany(Company company) {
        if (companies.values().stream().anyMatch(it -> it.getName().equals(company.getName()))) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "company with name " + company.getName() + " already exist"
            );
        }
        String id = UUID.randomUUID().toString();
        Company toSave = new Company(id, company.getName(), company.getSharesCount(), company.getSharesPrice());
        companies.put(id, toSave);
        return toSave;
    }

    @Override
    public synchronized Company getCompany(String name) {
        return companies.entrySet()
            .stream()
            .filter(it -> it.getValue().getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "company not found"))
            .getValue();
    }

    public synchronized User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        User toSave = new User(id, user.getBalance(), user.getShares());
        users.put(id, toSave);
        return toSave;
    }

    @Override
    public synchronized void buyShares(String userId, String companyId, int amount) {
        User user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
        Company company = companies.get(companyId);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "company " + companyId + " not found");
        }
        if (amount > company.getSharesCount()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "company " + companyId + " has only " + company.getSharesCount() + " shares"
            );
        }
        double price = amount * company.getSharesPrice();
        if (user.getBalance() < amount * company.getSharesPrice()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "don't enough money. " + amount + " shares price: " + price
            );
        }
        user.setBalance(user.getBalance() - price);
        company.setSharesCount(company.getSharesCount() - amount);
        boolean f = true;
        for (Share share : user.getShares()) {
            if (share.getCompanyId().equals(companyId)) {
                share.setAmount(share.getAmount() + amount);
                f = false;
            }
        }
        if (f) {
            user.getShares().add(new Share(companyId, userId, amount));
        }
    }

    @Override
    public synchronized void sellShares(String userId, String companyId, int amount) {
        User user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
        Company company = companies.get(companyId);
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "company " + companyId + " not found");
        }
        boolean f = true;
        for (Share share : user.getShares()) {
            if (share.getCompanyId().equals(companyId)) {
                if (share.getAmount() < amount) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "don't enough share count");
                }
                share.setAmount(share.getAmount() - amount);
                user.setBalance(user.getBalance() + amount * company.getSharesPrice());
                if (share.getAmount() == 0) {
                    user.getShares().remove(share);
                }
                f = false;
            }
        }
        if (f) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "don't have company " + companyId + " shares");
        }
    }

    @Override
    public synchronized User addBalance(String userId, double count) {
        User user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
        user.setBalance(user.getBalance() + count);
        return user;
    }

    @Override
    public synchronized UserSharesResponse getUserShares(String userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
        List<UserSharesResponse.ShareResponse> shares = new ArrayList<>();
        user.getShares()
            .forEach(it -> shares.add(new UserSharesResponse.ShareResponse(
                userId,
                it.getCompanyId(),
                it.getAmount(),
                companies.get(it.getCompanyId()).getSharesPrice()
            )));
        return new UserSharesResponse(shares);
    }

    @Override
    public synchronized double getBalance(String userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + userId + " not found");
        }
        double ans = 0;
        for (Share share : user.getShares()) {
            Company company = companies.get(share.getCompanyId());
            ans += share.getAmount() * company.getSharesPrice();
        }
        return ans + user.getBalance();
    }

    @Override
    public synchronized Company changeSharesPrice(String companyId, double newPrice) {
        Company company = companies.get(companyId);
        company.setSharesPrice(newPrice);
        return company;
    }
}
