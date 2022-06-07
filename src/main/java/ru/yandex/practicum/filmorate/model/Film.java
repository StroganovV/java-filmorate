package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class Film {
    private Set<Long> likes = new HashSet<>();

    @NotBlank
    private final String name;

    @Positive
    private final int duration;

    private long id;

    @NotBlank
    @Size(max=200)
    private final String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void deleteLike(Long id) {
        likes.remove(id);
    }
}
