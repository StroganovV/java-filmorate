package ru.yandex.practicum.filmorate.storage.daoImpl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {

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
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> getFilm(long id) {
        if (id <= 0 || !(films.containsKey(id))) {
            throw new FilmNotFoundException("Некорректный ID фильма");
        }

        return Optional.of(films.get(id));
    }

    @Override
    public void delete(Long id) {
        films.remove(id);
    }
}
