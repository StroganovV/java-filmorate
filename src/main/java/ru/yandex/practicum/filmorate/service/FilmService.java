package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;


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
}
