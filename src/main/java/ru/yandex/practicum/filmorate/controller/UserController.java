package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
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

        log.debug("Текущее количество пользователей: {}", users.size());

        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        if(user.getName() == null || user.getName().isBlank()) {

            user.setName(user.getLogin());

        }

        users.put(user.getEmail(), user);
        log.debug(String.valueOf(user));

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {

        users.put(user.getEmail(), user);
        log.debug(String.valueOf(user));

        return user;
    }
}
