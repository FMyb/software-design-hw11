package ru.ilin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.ilin.dto.AddCompanyRequest;
import ru.ilin.model.Company;
import ru.ilin.service.CompanyService;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    private final CompanyService companyService;

    public ExchangeController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> addCompany(@RequestBody AddCompanyRequest company) {
        return new ResponseEntity<>(companyService.save(company), HttpStatus.CREATED);
    }

    @GetMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getCompany(@RequestParam("company_name") String name) {
        return companyService.get(name);
    }

    @PutMapping(value = "/{company_id}/shares", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company changeSharesPrice(
        @PathVariable("company_id") String companyId,
        @RequestParam("new_price") double newPrice
    ) {
        if (newPrice <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "price must be greater than zero");
        }
        return companyService.changeSharesPrice(companyId, newPrice);
    }
}
