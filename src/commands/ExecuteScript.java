package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import reader_manager.InputManager;
import reader_manager.OutputManager;
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

    private boolean allowRecursion = true; // можно ли вызывать файл, который уже в стеке
    private final int MAX_DEPTH = 10; // максимальная глубина вложенности

    // состояние (общие для всех вызовов в рамках одной цепочки)
    private static final Set<String> activeScripts = new HashSet<>();
    private static int currentDepth = 0;

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
     * Сеттер для переключения режима рекурсии
     */
    public void setAllowRecursion(boolean allow) {
        this.allowRecursion = allow;
    }

    /**
     * Процессинг скрипта
     */
    private void processScript(String fileName) {
        File file = new File(fileName);
        String path = file.getAbsolutePath();

        // проверка лимита глубины (общая вложенность)
        if (currentDepth >= MAX_DEPTH) {
            OutputManager.errPrintln("Ошибка: Превышена максимальная глубина рекурсии (" + MAX_DEPTH + ")");
            return;
        }

        // проверка циклическую рекурсию (тот же файл)
        if (!allowRecursion && activeScripts.contains(path)) {
            OutputManager.errPrintln("Ошибка: Рекурсия запрещена флагом. Файл уже запущен: " + fileName);
            return;
        }

        if (!file.exists()) {
            OutputManager.errPrintln("Файл не найден: " + fileName);
            return;
        }

        // подготовка к выполнению
        activeScripts.add(path);
        currentDepth++;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(" ");
                String cmdName = parts[0];
                String[] args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, args.length);

                // OutputManager.println("[" + currentDepth + "] Выполняю: " + cmdName);

                // если это вложенный скрипт, вызываем этот же метод рекурсивно
                if (cmdName.equals("execute_script") && args.length > 0) {
                    processScript(args[0]);
                } else {
                    // Обычная команда
                    if (commandManager.getInputManager() != null) {
                        commandManager.getInputManager().setArgBuffer(args);
                    }
                    commandManager.execute(cmdName);
                }
            }
        } catch (FileNotFoundException e) {
            OutputManager.errPrintln("Ошибка доступа к файлу: " + e.getMessage());
        } finally {
            // выход из уровня вложенности
            currentDepth--;
            activeScripts.remove(path);
        }
    }

    @Override
    public void execute() {
        String fileName = inputManager.readNonEmptyString("Введите имя файла скрипта:");

        // сброс счетчиков при ручном запуске пользователем (уровень 0)
        if (currentDepth == 0) {
            activeScripts.clear();
        }

        processScript(fileName);
    }

    @Override
    public String getDescription() {
        return "выполнить команды из файла";
    }
}