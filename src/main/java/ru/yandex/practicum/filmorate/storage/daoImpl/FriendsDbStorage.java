package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;

import java.util.List;

@Component
public class FriendsDbStorage implements FriendStorage {
    private final JdbcTemplate jt;

    @Autowired
    public FriendsDbStorage(JdbcTemplate jt) {
        this.jt = jt;
    }

    @Override
    public List<Long> getFriendsByUserId(Long userId) {
        String sql = "select * from friends where user_id=?";
        return jt.query(sql, (rs, rowNum) -> rs.getLong("friend_id"), userId);
    }

    @Override
    public void updateFriendsByUserId(Long userId, Long friendId) {
            String sql = "INSERT INTO friends (user_id, friend_id)\n" +
                    "VALUES (?, ?)";
            jt.update(sql, userId, friendId);
    }

    @Override
    public void deleteFriendsByUserId(Long userId) {
            String sqlDel = "delete from friends where user_id = ?";
            jt.update(sqlDel, userId);
    }
}
