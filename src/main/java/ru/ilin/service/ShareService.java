package ru.ilin.service;

import ru.ilin.dto.BuySharesRequest;
import ru.ilin.dto.SellSharesRequest;
import ru.ilin.dto.UserSharesResponse;

public interface ShareService {
    void buyShares(BuySharesRequest request);
    UserSharesResponse getUserShares(String userId);
    void sellShares(SellSharesRequest sellSharesRequest);
}
