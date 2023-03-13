package ru.ilin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.ilin.dto.RegisterUserRequest;
import ru.ilin.model.User;
import ru.ilin.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest registerUserRequest) {
        return new ResponseEntity<>(userService.register(registerUserRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{user_id}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public User addBalance(@PathVariable("user_id") String userId, @RequestParam("count") double count) {
        if (count <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "count must be greater then zero");
        }
        return userService.addBalance(userId, count);
    }

    @GetMapping("/{user_id}/balance")
    public double getBalance(@PathVariable("user_id") String userId) {
        return userService.getBalance(userId);
    }
}
