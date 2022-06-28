package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.LikesStorage;

import java.util.List;

@Component
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jt;

    @Autowired
    public LikesDbStorage(JdbcTemplate jt) {
        this.jt = jt;
    }
    @Override
    public void updateLikesStorage(Long filmId, Long userId) {
        String sql = "INSERT INTO likes (film_id, user_id)\n" +
                "VALUES (?, ?)";
        jt.update(sql, filmId, userId);
    }

    @Override
    public List<Long> getLikes(long filmId) {
        String sql = "select user_id from likes where film_id=?";
        return jt.query(sql, (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }

    @Override
    public void deleteLikesByFilmId(Long filmId) {
        String sqlDelLikes = "delete from likes where film_id = ?";
        jt.update(sqlDelLikes, filmId);
    }
}
