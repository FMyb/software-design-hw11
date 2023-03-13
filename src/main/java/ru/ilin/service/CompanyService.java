package ru.ilin.service;

import ru.ilin.dto.AddCompanyRequest;
import ru.ilin.model.Company;

public interface CompanyService {
    Company save(AddCompanyRequest company);

    Company get(String name);
    Company changeSharesPrice(String companyId, double newPrice);
}
