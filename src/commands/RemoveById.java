package commands;

import models.City;
import reader_manager.OutputManager;
import tools.CollectionManager;
import reader_manager.InputManager;
import tools.LogOperations;

/**
 * Команда remove_by_id id.
 * Удаляет элемент коллекции по заданному айди.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class RemoveById implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final LogOperations logger;

    /**
     * Конструктор команды RemoveById.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер пользовательского ввода
     */
    public RemoveById(CollectionManager collectionManager, InputManager inputManager, LogOperations logger) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.logger = logger;
    }

    /**
     * Выполняет команду.
     * Удаляет объект City с указанным id из коллекции.
     */
    @Override
    public void execute() {
        long id = inputManager.readLong("Введите id элемента для удаления:", false);

        boolean found = false;

        // ищем
        for (City city : collectionManager.getCollection()) {
            if (city.getId() == id) {
                logger.logRemove(city.getId());
                collectionManager.getCollection().remove(city);
                found = true;
                break;
            }
        }

        if (found) {
            OutputManager.println("Элемент с id " + id + " удалён.");
        } else {
            OutputManager.errPrintln("Элемент с id " + id + " не найден.");
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент по id";
    }
}