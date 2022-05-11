package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {

        log.debug("Текущее количество сохраненных фильмов: {}", films.size());

        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {

            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");

        } else {

            films.put(film.getId(), film);
            log.debug(String.valueOf(film));

            return film;
        }
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {

        films.put(film.getId(), film);
        log.debug(String.valueOf(film));

        return film;
    }
}
