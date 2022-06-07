package ru.yandex.practicum.filmorate.exception;

public class IncorrectEmailException extends RuntimeException{
    public IncorrectEmailException(String message) {
        super(message);
    }
}
