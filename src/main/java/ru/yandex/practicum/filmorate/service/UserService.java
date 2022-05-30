package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

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
        return storage.create(user);
    }

    @Override
    public User update(User user) {
        return storage.update(user);
    }

    @Override
    public List<User> findAll() {
        return storage.findAll();
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

    public List<User> mutualFriends(long user1, long user2) {
        List<User> mutualFriends = new ArrayList<>();
        Set<Long> user1Friends = storage.getUser(user1).getAllFriends();
        user1Friends.retainAll(storage.getUser(user2).getAllFriends());

        if (user1Friends.size() > 0) {
            for (Long id : user1Friends) {
                mutualFriends.add(storage.getUser(id));
            }
            return mutualFriends;
        } else {return null;
        }
    }

}
