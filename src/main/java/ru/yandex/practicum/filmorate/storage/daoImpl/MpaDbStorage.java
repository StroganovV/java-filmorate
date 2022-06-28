package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.Mappers.MpaMapper;
import ru.yandex.practicum.filmorate.storage.dao.MpaStorage;

import java.util.List;
import java.util.Optional;

@Component
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jt;

    @Autowired
    public MpaDbStorage(JdbcTemplate jt) {
        this.jt = jt;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "select * from mpa";
        return jt.query(sql, new MpaMapper());
    }

    @Override
    public Optional<Mpa> getMpa(Long id) {
        String sql = "select * from mpa where id=?";
        return Optional.ofNullable(jt.queryForObject(sql, new MpaMapper(), id));
    }
}
