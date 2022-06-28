package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jt;
    private final FriendsDbStorage friendsDbStorage;


    @Autowired
    public UserDbStorage(JdbcTemplate jt, FriendsDbStorage friendsDbStorage) {
        this.jt = jt;
        this.friendsDbStorage = friendsDbStorage;
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jt);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("login", user.getLogin())
                .addValue("email", user.getEmail())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday())
                .addValue("count_friends", user.getFriends().size());
        Number num = jdbcInsert.executeAndReturnKey(parameters);
        user.setId(num.intValue());

        updateFriendsTable(user);

        return user;
    }


    @Override
    public User update(User user) {
        String sql = "UPDATE users set login =?,name=?, email=?, birthday=?\n" +
                "where id=?";
        jt.update(sql, user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        if (user.getFriends() != null && user.getFriends().size() > 0) {
            String sql2 = "UPDATE users set count_friends=?\n" +
                    "where id=?";
            jt.update(sql2, user.getFriends().size(), user.getId());
        }

        updateFriendsTable(user);

        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return jt.query(sql, this::mapRow);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from users cascade WHERE id = ?";
        Object[] args = new Object[]{id};

        jt.update(sql, args);
    }

    @Override
    public Optional<User> getUser(Long id) {
        String sql = "select * from users where id=?";
        try {
            return Optional.ofNullable(jt.queryForObject(sql, this::mapRow, id));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User(
                rs.getString("email"),
                rs.getString("login")
        );

        user.setId(rs.getLong("id"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        user.setName(rs.getString("name"));
        user.setFriends(new HashSet<>(friendsDbStorage.getFriendsByUserId(user.getId())));

        return user;
    }
    private void updateFriendsTable(User user) {
        if (user.getFriends() != null && user.getFriends().size() > 0) {
            friendsDbStorage.deleteFriendsByUserId(user.getId());
        }

        assert user.getFriends() != null;
        for (Long friendId : user.getFriends()) {
            friendsDbStorage.updateFriendsByUserId(user.getId(), friendId);
        }
    }
}
