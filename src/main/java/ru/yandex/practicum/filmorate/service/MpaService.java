package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaStorage;

import java.util.List;
import java.util.Optional;

@Service
public class MpaService {
    private final MpaStorage storage;

    public MpaService(@Qualifier("mpaDbStorage") MpaStorage storage) {
        this.storage = storage;
    }

    public List<Mpa> getAllMpa() {
        return storage.getAllMpa();
    }

    public Optional<Mpa> getMpa(Long id) {
        if (id <= 0) {
            throw new MpaNotFoundException("Некорректный ID MPA");
        }

        return storage.getMpa(id);
    }
}
