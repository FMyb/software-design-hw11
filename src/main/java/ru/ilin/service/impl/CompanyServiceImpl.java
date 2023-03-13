package ru.ilin.service.impl;

import org.springframework.stereotype.Service;
import ru.ilin.dto.AddCompanyRequest;
import ru.ilin.model.Company;
import ru.ilin.service.CompanyService;
import ru.ilin.storage.ExchangeStorage;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final ExchangeStorage exchangeStorage;

    public CompanyServiceImpl(ExchangeStorage exchangeStorage) {this.exchangeStorage = exchangeStorage;}

    @Override
    public Company save(AddCompanyRequest company) {
        return exchangeStorage.saveCompany(new Company(
            "",
            company.getName(),
            company.getSharesCount(),
            company.getSharesPrice()
        ));
    }

    @Override
    public Company get(String name) {
        return exchangeStorage.getCompany(name);
    }

    @Override
    public Company changeSharesPrice(String companyId, double newPrice) {
        return exchangeStorage.changeSharesPrice(companyId, newPrice);
    }
}
