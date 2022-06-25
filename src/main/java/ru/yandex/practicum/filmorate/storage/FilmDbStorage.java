package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public class FilmDbStorage implements FilmStorage {

    @Override
    public Film getFilm(long id) {
        return null;
    }

    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) throws UserNotFoundException {
        return null;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
