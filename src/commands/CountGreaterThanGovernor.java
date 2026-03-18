package commands;

import models.City;
import models.Human;
import reader_manager.InputManager;
import tools.CollectionManager;

/**
 * Команда count_greater_than_governor governor
 * Выводит количество элементов коллекции, у которых больше governor чем задано.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class CountGreaterThanGovernor implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды CountGreaterThanGovernor.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер для чтения пользовательского ввода
     */
    public CountGreaterThanGovernor(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Считывает объект Human и выводит количество городов, у которых governor больше заданного.
     */
    @Override
    public void execute() {
        try {
            System.out.println("Введите governor для сравнения:");
            Human referenceGovernor = inputManager.readHuman();

            if (referenceGovernor == null) {
                System.out.println("Governor не введён.");
                return;
            }

            long count = 0;

            // ищем по коллекции
            for (City city : collectionManager.getCollection()) {
                Human governor = city.getGovernor();

                if (governor != null && governor.getHeight() > referenceGovernor.getHeight()) {
                    count++;
                }
            }

            System.out.println("Количество городов с governor больше заданного: " + count);

        } catch (Exception e) {
            System.out.println("Ошибка при подсчёте: " + e.getMessage());
        }
    }
}