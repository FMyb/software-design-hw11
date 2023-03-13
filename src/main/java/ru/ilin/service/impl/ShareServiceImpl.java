package ru.ilin.service.impl;

import org.springframework.stereotype.Service;
import ru.ilin.dto.BuySharesRequest;
import ru.ilin.dto.SellSharesRequest;
import ru.ilin.dto.UserSharesResponse;
import ru.ilin.service.ShareService;
import ru.ilin.storage.ExchangeStorage;

@Service
public class ShareServiceImpl implements ShareService {
    private final ExchangeStorage exchangeStorage;

    public ShareServiceImpl(ExchangeStorage exchangeStorage) {
        this.exchangeStorage = exchangeStorage;
    }

    @Override
    public void buyShares(BuySharesRequest request) {
        exchangeStorage.buyShares(request.getUserId(), request.getCompanyId(), request.getAmount());
    }

    @Override
    public UserSharesResponse getUserShares(String userId) {
       return exchangeStorage.getUserShares(userId);
    }

    @Override
    public void sellShares(SellSharesRequest request) {
        exchangeStorage.sellShares(request.getUserId(), request.getCompanyId(), request.getAmount());
    }
}
