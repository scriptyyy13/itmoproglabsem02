package commands;

import models.City;
import tools.CollectionManager;
import reader_manager.InputManager;

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

    /**
     * Конструктор команды RemoveById.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер пользовательского ввода
     */
    public RemoveById(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
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
                collectionManager.getCollection().remove(city);
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Элемент с id " + id + " удалён.");
        } else {
            System.out.println("Элемент с id " + id + " не найден.");
        }
    }
}