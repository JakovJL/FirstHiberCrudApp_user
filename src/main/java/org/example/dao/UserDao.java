package org.example.dao;

import org.example.model.User;
import java.util.List;

/**
 * Интерфейс для управления операциями с пользователями в базе данных.
 * Определяет основные CRUD-операции (создание, чтение, обновление, удаление).
 */
    public interface UserDao {

    /**
     * Создает и сохраняет нового пользователя в БД.
     *
     * @param user объект пользователя для сохранения (не может быть null).
     * @throws RuntimeException если возникает ошибка при сохранении.
     */
    void create(User user);

    /**
     * Находит пользователя по уникальному идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return объект User или null, если пользователь не найден.
     * @throws RuntimeException если возникает ошибка при получении данных.
     */
    User getById(int id);

    /**
     * Возвращает список всех пользователей из БД.
     *
     * @return список User (может быть пустым).
     * @throws RuntimeException если возникает ошибка при получении данных.
     */
    List<User> getAll();

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param user объект пользователя с обновленными данными (не может быть null).
     * @throws RuntimeException если возникает ошибка при обновлении.
     */
    void update(User user);

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param user объект пользователя для удаления (не может быть null).
     * @throws RuntimeException если возникает ошибка при удалении.
     */
    void delete(User user);

    /**
     * Удаляет пользователя по уникальному идентификатору.
     *
     * @param id идентификатор пользователя.
     * @throws RuntimeException если возникает ошибка при удалении.
     */
    void deleteById(int id);
}
