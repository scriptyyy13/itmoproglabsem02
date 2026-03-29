package commands;

import reader_manager.OutputManager;
import tools.CollectionManager;

import java.time.ZonedDateTime;

/**
 * Команда info.
 * Выводит информацию о коллекции: тип, дата инициализации, количество элементов.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Info implements Command {

    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Info.
     *
     * @param collectionManager менеджер коллекции
     */
    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * Выводит тип коллекции, дату инициализации и количество элементов.
     */
    @Override
    public void execute() {
        OutputManager.println("Тип коллекции: " + collectionManager.getCollection().getClass().getSimpleName());
        OutputManager.println("Дата инициализации: " + ZonedDateTime.now());
        OutputManager.println("Количество элементов: " + collectionManager.size());
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}