package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
public class GenreController {
    GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        log.debug("Запрос списка всех жанров");
        return genreService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getFilm(@PathVariable("id") Long id) {
        log.debug("Запрос жанра - id " + id);
        if (genreService.getGenre(id).isPresent()) {
            return genreService.getGenre(id).get();
        } else {
            throw new GenreNotFoundException("Жанр не найден");
        }
    }
}
