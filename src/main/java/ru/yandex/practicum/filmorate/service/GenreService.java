package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreStorage storage;

    public GenreService(@Qualifier("genreDbStorage") GenreStorage storage) {
        this.storage = storage;
    }

    public List<Genre> getAllGenres() {
        return storage.getAllGenres();
    }

    public Optional<Genre> getGenre(Long id) {
        if (id <= 0) {
            throw new GenreNotFoundException("Некорректный ID жанра");
        }
        return storage.getGenre(id);
    }
}
