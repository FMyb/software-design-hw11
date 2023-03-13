package ru.ilin.storage;

import ru.ilin.dto.UserSharesResponse;
import ru.ilin.model.Company;
import ru.ilin.model.User;

public interface ExchangeStorage {
    Company saveCompany(Company company);

    Company getCompany(String name);

    User saveUser(User user);

    void buyShares(String userId, String companyId, int amount);

    void sellShares(String userId, String companyId, int amount);

    User addBalance(String userId, double count);

    UserSharesResponse getUserShares(String userId);

    double getBalance(String userId);

    Company changeSharesPrice(String companyId, double newPrice);
}
