package commands;

import models.City;
import reader_manager.InputManager;
import tools.CollectionManager;

import java.util.Comparator;
import java.util.Optional;

/**
 * Команда add_if_max {element}.
 * Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class AddIfMax implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды AddIfMax.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер для чтения пользовательского ввода
     */
    public AddIfMax(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Считывает новый объект City и добавляет его только если его population больше всех в коллекции.
     */
    @Override
    public void execute() {
        try {
            City newCity = inputManager.readCity();

            City maxCity = null;

            // ищем город с максимальным population
            for (City city : collectionManager.getCollection()) {
                if (maxCity == null || city.getPopulation() > maxCity.getPopulation()) {
                    maxCity = city;
                }
            }

            // если коллекция пуста ИЛИ новый город больше максимального
            if (maxCity == null || newCity.getPopulation() > maxCity.getPopulation()) {
                collectionManager.add(newCity);
                System.out.println("Город добавлен (он больше всех): " + newCity);
            } else {
                System.out.println("Город не добавлен, так как его population не превышает наибольшего в коллекции.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка при добавлении города: " + e.getMessage());
        }
    }
}