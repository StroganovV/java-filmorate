package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) throws ValidationException {
        if (film.getReleaseDate() != null &&
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {

            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");

        } else if (films.containsKey(film.hashCode())) {

            throw new ValidationException("Такой фильм уже был добавлен");

        } else {

            films.put(film.hashCode(), film);
            return film;
        }
    }

    @Override
    public Film update(Film film) {
        films.put(film.hashCode(), film);
        return film;
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

}
