# java-filmorate
![dia](https://dbdiagram.io/d/62b0824869be0b672c041971)

https://dbdiagram.io/d/62b0824869be0b672c041971

### *Примеры запросов*

1. Получить топ 10 фильмов:
    ```
    SELECT *
    FROM films
    ORDER BY count_likes DESC
    LIMIT 10;
   ``` 
2. Получить список друзей (список общих друзей производим в приложении с помощью 2х select):
    ```
   SELECT *
   FROM users AS u
   INNER JOIN friends AS f ON u.user_id = f.user_id
   WHERE u.user_id = index;
   ```
3. Получить все фильмы:
    ```
    SELECT * FROM films;
    ```
4. Получить всех пользователей:
    ```
    SELECT * FROM users;
    ```
5. Получить фильм по индексу:
    ```
    SELECT * FROM films WHERE film_id = index;
    ```
6. Получить пользователя по индексу:
    ```
    SELECT * FROM users WHERE user_id = index;
    ```