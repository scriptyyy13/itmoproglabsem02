package commands;

import reader_manager.OutputManager;

/**
 * Команда exit.
 * Завершает работу программы без сохранения коллекции.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Exit implements Command {
    /**
     * Выполняет команду.
     * Завершает работу программы.
     */
    @Override
    public void execute() {
        OutputManager.println("Завершение работы программы...");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "завершить программу";
    }
}