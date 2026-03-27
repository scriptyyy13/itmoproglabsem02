package tools;

import commands.Command;
import reader_manager.InputManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для управления командами.
 * Позволяет регистрировать и выполнять команды.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class CommandManager {

    /**
     * Хранилище зарегистрированных команд.
     */
    private final Map<String, Command> commands = new HashMap<>();
    private InputManager inputManager;

    /**
     * Устанавливаем менеджеру команд его инпут.
     */
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    /**
     * Получаем инпутменеджер для этого менеджера команд.
     */
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * Получаем список всех команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Регистрирует новую команду.
     *
     * @param name имя команды
     * @param command объект команды
     */
    public void register(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Выполняет команду по имени.
     *
     * @param name имя команды
     */
    public void execute(String name) {
        Command command = commands.get(name);

        if (command != null) {
            command.execute();
        } else {
            System.out.println("Такой команды нет! Используйте команду help, чтобы посмотреть список команд\n");
        }
    }
}