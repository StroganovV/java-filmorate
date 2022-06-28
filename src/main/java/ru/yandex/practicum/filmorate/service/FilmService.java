package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage storage) {
        this.storage = storage;
    }

    public Film create(Film film) {

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        return storage.create(film);
    }

    public Film update(Film film) {

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getId() <= 0) {
            throw new FilmNotFoundException("Некорректный ID фильма");
        }

        return storage.update(film);
    }

    public void delete(Long id) {
        storage.delete(id);
    }

    public List<Film> findAll() {
        return storage.findAll();
    }

    public Film addLike(long filmId, long userId) {
        Film film = null;
        if (storage.getFilm(filmId).isPresent()) {
            film = storage.getFilm(filmId).get();
            film.addLike(userId);
            return storage.update(film);
        } else {
            throw new FilmNotFoundException("фильм не найден");
        }
    }

    public Film deleteLike(long filmId, long userId) {
        if (userId <= 0) {
            throw new UserNotFoundException("Некорректный id пользователя");
        }

        if (filmId <= 0) {
            throw new UserNotFoundException("Некорректный id фильма");
        }

        Film film = storage.getFilm(filmId).get();
        film.deleteLike(userId);
        storage.update(film);
        return film;
    }

    public Film getFilm(Long id) {
        if (storage.getFilm(id).isPresent()) {
            return storage.getFilm(id).get();
        } else {
            throw new FilmNotFoundException("фильм не найден");
        }
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
