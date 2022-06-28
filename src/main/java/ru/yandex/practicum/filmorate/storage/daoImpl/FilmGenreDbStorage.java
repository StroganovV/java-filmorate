package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.FilmGenreStorage;

import java.util.List;

@Component
public class FilmGenreDbStorage implements FilmGenreStorage {
    private final JdbcTemplate jt;

    @Autowired
    public FilmGenreDbStorage(JdbcTemplate jt) {
        this.jt = jt;
    }

    @Override
    public void updateFilmGenreDb(Long filmId, Long genreId) {
        String sql = "INSERT INTO film_genre (film_id, genre_id)\n" +
                "VALUES (?, ?)";
        jt.update(sql, filmId, genreId);
    }

    @Override
    public void deleteGenre(Long filmId) {
        String sqlDelGenres = "delete from film_genre where film_id = ?";
        jt.update(sqlDelGenres, filmId);
    }

    @Override
    public List<Long> getGenresByFilmId(Long filmId) {
        String sql = "select * from film_genre where film_id=?";
        return jt.query(sql, (rs, rowNum) -> rs.getLong("genre_id"), filmId);
    }
}
