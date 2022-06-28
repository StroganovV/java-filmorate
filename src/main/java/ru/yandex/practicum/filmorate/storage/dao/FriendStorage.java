package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;

public interface FriendStorage {

    List<Long> getFriendsByUserId(Long userId);
    void updateFriendsByUserId(Long userId, Long friendId);
    void deleteFriendsByUserId(Long userId);
}
