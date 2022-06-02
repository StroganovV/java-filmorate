package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping ("/films")
    public List<Film> findAll() {
        log.debug("Запрос списка всех фильмов");
        return filmService.findAll();
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable("id") Long id) {
        log.debug("Запрос фильма - id " + id);
       return filmService.getFilm(id);
    }

    @PostMapping ("/films")
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Добавить фильм" + film.getName());
        return filmService.create(film);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Обновить данные фильма" + film.getName());
        return filmService.update(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") Long id,
                        @PathVariable("userId") Long userId) {
        log.debug("Пользователь (" + userId + ") поствил like фильму (" + id + ")");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable("id") Long id,
                           @PathVariable("userId") Long userId) {
        log.debug("Пользователь (" + userId + ") удалил like к фильму (" + id + ")");
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getTopCountFilms(@RequestParam(defaultValue = "10", required = false) Long count) {
        if (count <= 0) {
            throw new IncorrectParameterException("count");
        }

        log.debug("Показать " + count + " лучших фильмов");
        return filmService.getTopCountFilms(count);
    }
}


