package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import reader_manager.InputManager;
import tools.CommandManager;

/**
 * Команда execute_script file_name.
 * Выполняет команды из указанного файла, будто бы они вводились пользователем (как в интерактивном режиме)
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class ExecuteScript implements Command {

    private final CommandManager commandManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды ExecuteScript.
     *
     * @param commandManager менеджер команд для выполнения команд из файла
     */
    public ExecuteScript(CommandManager commandManager, InputManager inputManager) {
        this.commandManager = commandManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду.
     * Пользователю предлагается ввести имя файла скрипта.
     * Затем команды из файла выполняются последовательно.
     */
    @Override
    public void execute() {
        System.out.println("Введите имя файла скрипта:");
        String scriptFileName = inputManager.readNonEmptyString("Введите название скрипта:");

        File file = new File(scriptFileName);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Файл не найден: " + scriptFileName);
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) continue; // пропуск пустых строк и комментариев

                String[] parts = line.split(" ");
                String commandName = parts[0];
                String[] cmdArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, cmdArgs, 0, cmdArgs.length);

                System.out.println("> " + line);

                // передаём аргументы в InputManager, если команда их использует
                if (commandManager.getInputManager() != null) {
                    commandManager.getInputManager().setArgBuffer(cmdArgs);
                }

                // выполняем команду
                commandManager.execute(commandName);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}