package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectEmailException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private final List<String> emails = new ArrayList<>();
    long id = 0;

    @Override
    public User create(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {

            user.setName(user.getLogin());
        }

        if (emails.contains(user.getEmail())) {
            throw new IncorrectEmailException("Пользователь с таким email уже создан");
        }

        user.setId(++id);
        users.put(user.getId(), user);
        emails.add(user.getEmail());

        return user;
    }

    @Override
    public User update(User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getId() <= 0) {
            throw new UserNotFoundException("Неккоректный ID пользователя");
        }

        if (!(emails.contains(user.getEmail()))) {
            emails.remove(users.get(user.getId()).getEmail());
            emails.add(user.getEmail());
        }

        users.put(user.getId(), user);

        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }


    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    @Override
    public Optional<User> getUser(Long id) {
        if (id <= 0 || !(users.containsKey(id))) {
            throw new UserNotFoundException("Неккоректный ID пользователя");
        }
        return Optional.of(users.get(id));
    }

}
