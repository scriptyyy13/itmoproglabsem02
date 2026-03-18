package commands;

import models.City;
import reader_manager.InputManager;
import tools.CollectionManager;

import java.util.Optional;

/**
 * Команда update id {element}.
 * Обновляет существующий элемент коллекции (City) по его id.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Update implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды Update.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер для чтения пользовательского ввода
     */
    public Update(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Считывает id у пользователя, находит элемент по нему и обновляет его новыми значениями.
     */
    @Override
    public void execute() {
        long id = inputManager.readLong("Введите ID элемента для обновления:", false);

        City foundCity = null;

        // ищем город вручную
        for (City city : collectionManager.getCollection()) {
            if (city.getId() == id) {
                foundCity = city;
                break;
            }
        }

        if (foundCity == null) {
            System.out.println("Элемент с id " + id + " не найден.");
            return;
        }

        try {
            City newCity = inputManager.readCity();
            collectionManager.update(id, newCity);
            System.out.println("Элемент с id " + id + " обновлен: " + newCity);
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении города: " + e.getMessage());
        }
    }
}