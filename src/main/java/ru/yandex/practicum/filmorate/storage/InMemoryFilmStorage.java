package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final HashMap<Long, Film> films = new HashMap<>();
    long id = 0;


    @Override
    public Film create(Film film) throws ValidationException {
        if (film.getReleaseDate() != null &&
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {

            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");

        } else {
            film.setId(++id);
            films.put(film.getId(), film);
            return film;
        }
    }

    @Override
    public Film update(Film film) {

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getId() <= 0) {
            throw new FilmNotFoundException("Некорректный ID фильма");
        }

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilm(long id) {
        if (id <= 0 || !(films.containsKey(id))) {
            throw new FilmNotFoundException("Некорректный ID фильма");
        }

        return films.get(id);
    }

    @Override
    public void delete(Long id) {
        films.remove(id);
    }
}
