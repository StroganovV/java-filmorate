package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Optional<Film> getFilm(long id);

    Film create(Film film);

    Film update(Film film) throws UserNotFoundException;

    List<Film> findAll();

    void delete(Long id);


}
