package commands;

import models.City;
import reader_manager.InputManager;
import tools.CollectionManager;

import java.util.stream.Collectors;

/**
 * Команда filter_contains_name name.
 * Выводит элементы коллекции, значение поля name которых содержит заданную подстроку.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class FilterContainsName implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды FilterContainsName.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер для чтения пользовательского ввода
     */
    public FilterContainsName(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Считывает подстроку и выводит все элементы коллекции, у которых name содержит эту подстроку.
     */
    @Override
    public void execute() {
        try {
            String substring = inputManager.readNonEmptyString("Введите подстроку для поиска в name:");

            boolean found = false;

            System.out.println("Элементы с name, содержащим '" + substring + "':");

            // ищем
            for (City city : collectionManager.getCollection()) {
                if (city.getName() != null && city.getName().contains(substring)) {
                    System.out.println(city);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Элементы с name, содержащим '"
                        + substring + "', не найдены.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка при фильтрации: " + e.getMessage());
        }
    }
}