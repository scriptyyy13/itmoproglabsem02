package main_classes;

import reader_manager.InputManager;
import tools.CommandManager;
import reader_manager.OutputManager;
import java.util.NoSuchElementException;

/**
 * Основной класс приложения.
 *
 * Содержит основной цикл обработки ввода.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class App {
    private final CommandManager commandManager;
    private final InputManager inputManager;

    /**
     * Конструктор основного класса приложения.
     *
     * @param commandManager менеджер команд для выполнения команд из файла
     * @param inputManager менеджер для чтения пользовательского ввода
     */
    public App(CommandManager commandManager, InputManager inputManager) {
        this.commandManager = commandManager;
        this.inputManager = inputManager;
    }

    /**
     * Метод с основным циклом программы
     */
    public void run() {
        OutputManager.println("Программа запущена. Введите 'help', чтобы получить список команд.");

        // основной цикл интерактивного ввода
        while (true) {
            OutputManager.print("> ");

            // проверка на закрытие потока ввода
            if (!inputManager.getScanner().hasNextLine()) {
                OutputManager.println("\nВвод завершен. Выход из программы...");
                break;
            }

            try {
                String line = inputManager.getScanner().nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String commandName = parts[0];

                // подготовка аргументов для команды
                String[] cmdArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, cmdArgs, 0, cmdArgs.length);

                // передаем буфер аргументов в InputManager
                inputManager.setArgBuffer(cmdArgs);

                // выполнение команды
                commandManager.execute(commandName);

            } catch (NoSuchElementException e) {
                OutputManager.errPrintln("Ввод завершен.");
                break;
            } catch (Exception e) {
                OutputManager.errPrintln("Произошла ошибка: " + e.getMessage());
            }
        }
    }
}