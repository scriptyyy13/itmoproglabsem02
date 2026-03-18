package commands;

import models.City;
import reader_manager.InputManager;
import tools.CollectionManager;

import java.util.Comparator;
import java.util.Optional;

/**
 * Команда add_if_min {element}.
 * Добавляет новый элемент в коллекцию, если его значение меньше наименьшего элемента.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class AddIfMin implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды AddIfMin.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер для чтения пользовательского ввода
     */
    public AddIfMin(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Считывает новый объект City и добавляет его только если его population меньше всех в коллекции.
     */
    @Override
    public void execute() {
        try {
            City newCity = inputManager.readCity();

            City minCity = null;

            // ищем город с минимальным population
            for (City city : collectionManager.getCollection()) {
                if (minCity == null || city.getPopulation() < minCity.getPopulation()) {
                    minCity = city;
                }
            }

            // если коллекция пуста ИЛИ новый город меньше минимального
            if (minCity == null || newCity.getPopulation() < minCity.getPopulation()) {
                collectionManager.add(newCity);
                System.out.println("Город добавлен (он меньше всех): " + newCity);
            } else {
                System.out.println("Город не добавлен, так как его population превышает меньшего в коллекции.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка при добавлении города: " + e.getMessage());
        }
    }
}