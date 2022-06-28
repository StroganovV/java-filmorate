package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;

public interface LikesStorage {

    void updateLikesStorage(Long filmId, Long userId);
    void deleteLikesByFilmId(Long filmId);
    List<Long> getLikes(long filmId);
}
