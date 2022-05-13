package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<String, User> users = new HashMap<>();


    @GetMapping
    public List<User> findAll() {

        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        try {
            if (user.getLogin().contains(" ")) {
                throw new ValidationException("Login не должен содержать пробелов");
            } else {

                if (user.getName() == null || user.getName().isBlank()) {

                    user.setName(user.getLogin());
                }

                if (users.containsKey(user.getEmail())) {
                    throw new ValidationException("Пользователь с таким email уже создан");
                }

                users.put(user.getEmail(), user);
                log.debug(String.valueOf(user));

                return user;
            }
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {

        try {
            if (user.getLogin().contains(" ")) {
                throw new ValidationException("Login не должен содержать пробелов");
            } else {

                if (user.getName() == null || user.getName().isBlank()) {

                    user.setName(user.getLogin());
                }

                users.put(user.getEmail(), user);
                log.debug(String.valueOf(user));

                return user;
            }
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
