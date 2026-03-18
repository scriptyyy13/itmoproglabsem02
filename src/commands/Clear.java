package commands;

import tools.CollectionManager;

/**
 * Команда clear.
 * Очищает всю коллекцию объектов City.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Clear implements Command {

    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Clear.
     *
     * @param collectionManager менеджер коллекции
     */
    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * Очищает все элементы коллекции и выводит сообщение пользователю.
     */
    @Override
    public void execute() {
        collectionManager.clear();
        System.out.println("Коллекция очищена.");
    }
}