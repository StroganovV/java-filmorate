package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Mappers.GenreMapper;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jt;

    @Autowired
    public GenreDbStorage(JdbcTemplate jt) {
        this.jt = jt;
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "select * from genres";
        return jt.query(sql, new GenreMapper());
    }

    @Override
    public Optional<Genre> getGenre(Long id) {
        String sql = "select * from genres where id=?";
        return Optional.ofNullable(jt.queryForObject(sql, new GenreMapper(), id));
    }
}
