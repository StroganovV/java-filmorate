package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genre implements Comparable<Genre> {
    private final Long id;
    private final String name;

    @Override
    public int compareTo(Genre o) {
        return (int) (o.getId() - this.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre that = (Genre) o;

        if (id != that.id) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id == null ? 0 : id.hashCode();
        result = 31 * result;
        result = 31 * result;
        return result;
    }
}
