package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FilmService implements FilmStorage {
    InMemoryFilmStorage storage;

    @Autowired
    public FilmService(InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    @Override
    public Film create(Film film) {
            return storage.create(film);
    }

    @Override
    public Film update(Film film) {
        return storage.update(film);
    }

    @Override
    public List<Film> findAll() {
        return storage.findAll();
    }

    public Film addLike(long filmId, long userId) {
        Film film = storage.getFilm(filmId);
        film.addLike(userId);
        storage.update(film);
        return film;
    }

    public Film deleteLike(long filmId, long userId) {
        if(userId <= 0) {
            throw new UserNotFoundException("Некорректный id пользователя");
        }

        if(filmId <= 0) {
            throw new UserNotFoundException("Некорректный id фильма");
        }

        Film film = storage.getFilm(filmId);
        film.deletLike(userId);
        storage.update(film);
        return film;
    }

    public Film getFilm(Long id) {
        return storage.getFilm(id);
    }

    public List<Film> getTopCountFilms(Long count) {

        return storage.findAll().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compare(Film f0, Film f1) {
        return f1.getLikes().size() - f0.getLikes().size();
    }
}
