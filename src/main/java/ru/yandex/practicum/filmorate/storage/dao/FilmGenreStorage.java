package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;

public interface FilmGenreStorage {

    void updateFilmGenreDb(Long filmId, Long genreId);
    void deleteGenre(Long filmId);
    List<Long> getGenresByFilmId(Long filmId);
}
