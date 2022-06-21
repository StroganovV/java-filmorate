package ru.yandex.practicum.filmorate.model;

public enum Genre {
    COMEDY ("Комедия"),
    DRAMA ("Драма"),
    CARTOON ("Мультфильм"),
    THRILLER ("Триллер"),
    DOCUMENTARY ("Документальный фильм"),
    ACTION ("Боевик"),
    FANTASY ("Фэнтези"),
    ROMANCE ("Фильм о любви"),
    ADVENTURE ("Прикоючения"),
    HORROR ("Ужасы"),
    CRIME ("Криминальный"),
    ANIMATION ("Анимационный"),
    MUSICAL ("Мюзикл"),
    SCI_FI ("Научная фантастика"),
    WESTERN ("Вестерн"),
    WAR ("Фильм о войне"),
    BIOGRAPHY ("Биография"),
    FAMILY ("Семейный")
    ;

    private final String title;

    Genre(String title) {
        this.title = title;
    }


}
