package ru.ilin.service.impl;

import org.springframework.stereotype.Service;
import ru.ilin.dto.RegisterUserRequest;
import ru.ilin.model.User;
import ru.ilin.service.UserService;
import ru.ilin.storage.ExchangeStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final ExchangeStorage exchangeStorage;

    public UserServiceImpl(ExchangeStorage exchangeStorage) {
        this.exchangeStorage = exchangeStorage;
    }

    @Override
    public User register(RegisterUserRequest request) {
        return exchangeStorage.saveUser(new User("", request.getBalance(), List.of()));
    }

    @Override
    public User addBalance(String userId, double count) {
        return exchangeStorage.addBalance(userId, count);
    }

    @Override
    public double getBalance(String userId) {
        return exchangeStorage.getBalance(userId);
    }
}
