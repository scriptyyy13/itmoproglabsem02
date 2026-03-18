package commands;

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
        System.out.println("Тип коллекции: " + collectionManager.getCollection().getClass().getSimpleName());
        System.out.println("Дата инициализации: " + ZonedDateTime.now());
        System.out.println("Количество элементов: " + collectionManager.getCollection().size());
    }
}