package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    UserStorage storage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage storage) {
        this.storage = storage;
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        try {
            return storage.create(user);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    public User update(User user) {
        if (user.getId() <= 0) {
            throw new UserNotFoundException("Некорректный id");
        }
        try {
            return storage.update(user);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    public List<User> findAll() {
        return storage.findAll();
    }


    public void delete(Long id) {
        storage.delete(id);
    }

    public User getUser(Long id) {
        if (storage.getUser(id).isPresent()) {
            return storage.getUser(id).get();
        } else {
            throw new UserNotFoundException("User не найден");
        }
    }

    public User addFriend(long userId, long newFriendId) {
        if (userId <= 0 || newFriendId <= 0) {
            throw new UserNotFoundException("User не найден");
        }
        User user;
        if (storage.getUser(userId).isPresent()) {
            user = storage.getUser(userId).get();
        } else {
            throw new UserNotFoundException("User не найден");
        }
        user.addFriend(newFriendId);
        return storage.update(user);
    }

    public User deleteFriend(long userId, long delFriendId) {
        User user = storage.getUser(userId).get();
        User friend = storage.getUser(delFriendId).get();
        friend.deleteFriend(userId);
        user.deleteFriend(delFriendId);
        storage.update(friend);
        return storage.update(user);
    }

    public List<User> getUserFriendList(Long id) {
        User user = storage.getUser(id).get();
        List<User> friends = new ArrayList<>();
        for (Long l : new ArrayList<>(user.getFriends())) {
            friends.add(storage.getUser(l).get());
        }

        return friends;
    }

    public List<User> mutualFriends(long user1, long user2) {
        List<User> mutualFriends = new ArrayList<>();
        List<Long> user1Friends = new ArrayList<>(storage.getUser(user1).get().getFriends());
        user1Friends.retainAll(new ArrayList<>(storage.getUser(user2).get().getFriends()));

        if (user1Friends.size() > 0) {
            for (Long id : user1Friends) {
                mutualFriends.add(storage.getUser(id).get());
            }
        }
        return mutualFriends;
    }

}
