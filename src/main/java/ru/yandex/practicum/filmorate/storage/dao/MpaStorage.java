package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {

    public List<Mpa> getAllMpa();

    public Optional<Mpa> getMpa(Long id);

}
