package commands;

import tools.CollectionManager;

/**
 * Команда remove_first.
 * Удаляет первый элемент из коллекции City.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class RemoveFirst implements Command {

    private final CollectionManager collectionManager;

    /**
     * Конструктор команды RemoveFirst.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirst(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * Удаляет первый элемент коллекции и сообщает пользователю.
     */
    @Override
    public void execute() {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста. Удалять нечего.");
        } else {
            collectionManager.removeFirst();
            System.out.println("Первый элемент коллекции удалён.");
        }
    }
}