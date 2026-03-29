package commands;

import models.City;
import reader_manager.OutputManager;
import tools.CollectionManager;

/**
 * Команда show.
 * Выводит все элементы коллекции в стандартный поток вывода.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Show implements Command {

    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Show.
     *
     * @param collectionManager менеджер коллекции
     */
    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * Выводит строковое представление всех элементов коллекции.
     */
    @Override
    public void execute() {
        if (collectionManager.getCollection().isEmpty()) {
            OutputManager.errPrintln("Коллекция пуста.");
            return;
        }
        for (City city : collectionManager.getCollection()) {
            OutputManager.println(String.valueOf(city));
        }
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}