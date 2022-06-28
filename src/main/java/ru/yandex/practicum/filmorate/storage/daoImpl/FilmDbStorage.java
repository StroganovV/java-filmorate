package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jt;
    private final LikesDbStorage likesDbStorage;
    private final FilmGenreStorage filmGenreStorage;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jt,
                         LikesDbStorage likesDbStorage,
                         FilmGenreStorage filmGenreStorage,
                         MpaDbStorage mpaDbStorage,
                         GenreDbStorage genreDbStorage
    ) {
        this.jt = jt;
        this.likesDbStorage = likesDbStorage;
        this.filmGenreStorage = filmGenreStorage;
        this.mpaDbStorage = mpaDbStorage;
        this.genreDbStorage = genreDbStorage;
    }

    @Override
    public Optional<Film> getFilm(long id) {
        String sql = "select * from films where id=?";
        try {
            return Optional.ofNullable(jt.queryForObject(sql, this::mapRow, id));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jt);
        jdbcInsert.withTableName("films").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("release_date", film.getReleaseDate())
                .addValue("duration", film.getDuration());

        Number num = jdbcInsert.executeAndReturnKey(parameters);
        film.setId(num.intValue());

        if (film.getMpa() != null && film.getMpa().getId() != null) {
            String sql2 = "UPDATE films set mpa_id=?\n" +
                    "where id=?";
            jt.update(sql2, film.getMpa().getId(), film.getId());
        }

        updateLikesAndFilmGenreTables(film);

        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = "UPDATE films SET name=?, description=?, release_date=?, duration=?, mpa_id=?\n" +
                " where id=?";
        jt.update(sql, film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        if (film.getLikes() != null && film.getLikes().size() > 0) {
            String sql3 = "UPDATE films SET count_likes=?\n" +
                    "where id=?";
            jt.update(sql3, film.getLikes().size(), film.getId());
        }

        filmGenreStorage.deleteGenre(film.getId());
        likesDbStorage.deleteLikesByFilmId(film.getId());
        updateLikesAndFilmGenreTables(film);

        if (film.getGenres() != null && film.getGenres().size() > 0) {
            film.setGenres(new HashSet<>(Objects.requireNonNull(getGenres(filmGenreStorage.getGenresByFilmId(film.getId())))));
        }

        return film;
    }

    @Override
    public List<Film> findAll() {
        String sql = "select * from films";
        List<Film> films = jt.query(sql, this::mapRow);

        if (films == null) {
            return new ArrayList<>();
        } else {
            return films;
        }
    }


    @Override
    public void delete(Long id) {
        String sql = "delete from films cascade WHERE id = ?";
        Object[] args = new Object[]{id};

        jt.update(sql, args);
    }

    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film(
                rs.getString("name"),
                rs.getInt("duration"),
                rs.getString("description")
        );

        film.setId(rs.getLong("id"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());

        film.setLikes(new HashSet<>(likesDbStorage.getLikes(film.getId())));

        List<Genre> genres = getGenres(filmGenreStorage.getGenresByFilmId(film.getId()));
        if (genres != null && genres.size() > 0) {
            film.setGenres(new HashSet<>(genres));
        }

        long mpaId = rs.getLong("mpa_id");

        if (mpaId != 0) {
            film.setMpa(mpaDbStorage.getMpa(mpaId).get());
        }


        return film;
    }

    private List<Genre> getGenres(List<Long> genr) {
        List<Genre> genres = new ArrayList<>();
        if (genr != null && genr.size() > 0) {
            for (Long idg : genr) {
                Genre genre = genreDbStorage.getGenre(idg).get();
                genres.add(genre);
            }
        }
        if (genres.size() == 0) {
            return null;
        } else {
            return genres;
        }
    }

    private void updateLikesAndFilmGenreTables(Film film) {
        if (film.getGenres() != null && film.getGenres().size() > 0) {
            for (Genre genre : film.getGenres()) {
                filmGenreStorage.updateFilmGenreDb(film.getId(), genre.getId());
            }
        }

        if (film.getLikes() != null && film.getLikes().size() > 0) {
            for (Long id : film.getLikes()) {
                likesDbStorage.updateLikesStorage(film.getId(), id);
            }
        }
    }
}
