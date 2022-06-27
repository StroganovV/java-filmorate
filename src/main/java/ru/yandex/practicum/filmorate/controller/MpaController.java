package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
public class MpaController {
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllGenres() {
        log.debug("Запрос списка всех MPA");
        return mpaService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getFilm(@PathVariable("id") Long id) {
        log.debug("Запрос MPA - id " + id);
        if (mpaService.getMpa(id).isPresent()) {
            return mpaService.getMpa(id).get();
        } else {
            throw new MpaNotFoundException("MPA не найден");
        }
    }

}
