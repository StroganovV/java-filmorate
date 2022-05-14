package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc

public class UserControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test1_shouldStatusOkWhenCreateValidUser() throws Exception {
        String body = mapper.writeValueAsString(new User("fd@mail.ru", "name"));
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test2_shouldStatus4xxWhenCreateUserWithNoValidEmail() throws Exception {
        String body = mapper.writeValueAsString(new User("fdmail.ru", "name"));
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test3_shouldStatus4xxWhenCreateUserWithEmptyEmail() throws Exception {
        String body = mapper.writeValueAsString(new User(" ", "name"));
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test4_shouldStatus4xxWhenCreateUserWithEmptyName() throws Exception {
        String body = mapper.writeValueAsString(new User("email@email.ru", " "));
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test5_shouldStatus4xxWhenCreateUserWithSpaceInName() throws Exception {
        String body = mapper.writeValueAsString(new User("email@email.ru", "Im space"));
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test6_shouldStatus4xxWhenCreateUserWithFutureBirthday() throws Exception {
        User user = new User("mail@mail.ru", "login");
        user.setBirthday(LocalDate.of(2044, 11, 1));

        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test7_shouldStatusOkWhenCreateUserWithValidBirthday() throws Exception {
        User user = new User("mail@mail.ru", "login");
        user.setBirthday(LocalDate.now().minusDays(1));

        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}