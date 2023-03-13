package ru.ilin.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilin.dto.BuySharesRequest;
import ru.ilin.dto.SellSharesRequest;
import ru.ilin.dto.UserSharesResponse;
import ru.ilin.service.ShareService;

@RestController
@RequestMapping("/shares")
public class SharesController {
    private final ShareService shareService;

    public SharesController(ShareService shareService) {this.shareService = shareService;}

    @PostMapping(value = "/buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void buyShares(@RequestBody BuySharesRequest buySharesRequest) {
        shareService.buyShares(buySharesRequest);
    }

    @PostMapping(value = "sell", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sellShares(@RequestBody SellSharesRequest sellSharesRequest) {
        shareService.sellShares(sellSharesRequest);
    }

    @GetMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSharesResponse getUserShares(@PathVariable("user_id") String userId) {
        return shareService.getUserShares(userId);
    }
}
