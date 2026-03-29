package commands;

import reader_manager.OutputManager;
import tools.CommandManager;

/**
 * Команда help.
 * Выводит список доступных команд и их описание.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Help implements Command {

    private final CommandManager commandManager;

    /**
     * Конструктор команды Help.
     */
    public Help(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду.
     * Выводит список всех доступных команд с их описаниями.
     */
    @Override
    public void execute() {
        System.out.println("--- Список доступных команд ---");
        // Проходим по всем командам в мапе
        commandManager.getCommands().forEach((name, command) -> OutputManager.printf("%-30s : %s%n", name, command.getDescription()));
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}