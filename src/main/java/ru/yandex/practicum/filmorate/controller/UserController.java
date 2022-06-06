package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userStorage) {
        this.userService = userStorage;
    }

    @GetMapping
    public List<User> findAll() {
        log.debug("Запрос списка всех пользователей");
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable("id") Long id) {
        log.debug("Запрос пользователя - id " + id);
        return userService.getUser(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Создать пользователя");
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.debug("Обновить данные пользователя");
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Long id,
                          @PathVariable("friendId") Long friendId) {
        log.debug("Добавить Пользователя (" + friendId + ") в друзья к Пользователю (" + id + ")");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Long id,
                             @PathVariable("friendId") Long friendId) {
        log.debug("Удалить Пользователя (" + friendId + ") из друзей Пользователя (" + id + ")");
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriendsList(@PathVariable("id") Long id) {
        log.debug("Запрошен список всех друзей пользователя");
        return userService.getUserFriendList(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable("id") Long id,
                                       @PathVariable("otherId") Long otherId) {
        log.debug("Запрошен список общих друзей Пользователей " + id + " и " + otherId);
        return userService.mutualFriends(id, otherId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        log.debug("Удаление пользователя - id " + id);
        userService.delete(id);
    }
}
