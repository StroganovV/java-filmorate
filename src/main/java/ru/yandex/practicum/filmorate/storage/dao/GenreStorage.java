package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    public List<Genre> getAllGenres();

    public Optional<Genre> getGenre(Long id);
}
