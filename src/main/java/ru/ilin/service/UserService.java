package ru.ilin.service;

import ru.ilin.dto.RegisterUserRequest;
import ru.ilin.model.User;

public interface UserService {
    User register(RegisterUserRequest request);

    User addBalance(String userId, double count);

    double getBalance(String userId);
}
