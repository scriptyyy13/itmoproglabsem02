package commands;

import models.City;
import reader_manager.InputManager;
import tools.CollectionManager;

/**
 * Команда add {element}.
 * Добавляет новый элемент в коллекцию.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Add implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды Add.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер для чтения ввода
     */
    public Add(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Считывает новый объект City и добавляет его в коллекцию.
     */
    @Override
    public void execute() {
        try {
            City city = inputManager.readCity();
            collectionManager.add(city);
            System.out.println("Город добавлен: " + city);
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении города: " + e.getMessage());
        }
    }
}