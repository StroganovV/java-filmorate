package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserStorage {
    InMemoryUserStorage storage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.storage = storage;
    }


    @Override
    public User create(User user) {
        try {
            return storage.create(user);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Override
    public User update(User user) {
        try {
            return storage.update(user);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() {
        return storage.findAll();
    }

    public User getUser(Long id) {
        return storage.getUser(id);
    }

    public User addFriend(long userId, long newFriendId) {
        User user = storage.getUser(userId);
        User friend = storage.getUser(newFriendId);
        friend.addFriend(userId);
        user.addFriend(newFriendId);
        storage.update(friend);
        return storage.update(user);
    }

    public User deleteFriend(long userId, long delFriendId) {
        User user = storage.getUser(userId);
        User friend = storage.getUser(delFriendId);
        friend.deleteFriend(userId);
        user.deleteFriend(delFriendId);
        storage.update(friend);
        return storage.update(user);
    }

    public List<User> getUserFriendList(Long id) {
        User user = storage.getUser(id);
        List<User> friends = new ArrayList<>();
        for (Long l : user.getAllFriends()) {
            friends.add(storage.getUser(l));
        }

        return friends;
    }

    public List<User> mutualFriends(long user1, long user2) {
        List<User> mutualFriends = new ArrayList<>();
        List<Long> user1Friends = storage.getUser(user1).getAllFriends();
        user1Friends.retainAll(storage.getUser(user2).getAllFriends());

        if (user1Friends.size() > 0) {
            for (Long id : user1Friends) {
                mutualFriends.add(storage.getUser(id));
            }
        }
        return mutualFriends;
    }

}
