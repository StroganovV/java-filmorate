package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private final List<String> emails = new ArrayList<>();

    @Override
    public User create(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {

            user.setName(user.getLogin());
        }

        if (emails.contains(user.getEmail())) {
            throw new ValidationException("Пользователь с таким email уже создан");
        }

        users.put(user.getId(), user);
        emails.add(user.getEmail());

        return user;
    }

    @Override
    public User update(User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);

        if(!(emails.contains(user.getEmail()))) {
            emails.add(user.getEmail());
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User getUser(long id) {
        return users.getOrDefault(id, null);
    }
}
