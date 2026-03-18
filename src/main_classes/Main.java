package main_classes;

import models.City;
import reader_manager.FileManager;
import reader_manager.InputManager;
import tools.CollectionManager;
import tools.CommandManager;
import commands.*;

import java.util.Scanner;

/**
 * Главный класс приложения.
 *
 * Отвечает за запуск программы, загрузку данных из .csv файла (он не обязателен),
 * инициализацию менеджеров коллекции и команд, а также
 * обработку пользовательского ввода в интерактивном режиме.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * Если передан аргумент командной строки, пытается загрузить коллекцию из .csv
     * Если файл не передан или не удалось загрузить, коллекция создаётся пустой.
     *
     * @param args аргументы командной строки (args[0] — имя файла, опционально)
     */
    public static void main(String[] args) {

        String fileName = null;

        if (args.length > 0) {
            fileName = args[0];
        }

        // создание менеджеров
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = null;

        if (fileName != null) {
            try {
                fileManager = new FileManager(fileName);
                collectionManager.getCollection().addAll(fileManager.load());
                System.out.println("Данные загружены из файла: " + fileName);
            } catch (Exception e) {
                System.out.println("Не удалось загрузить файл: " + fileName + ". Коллекция будет пустой.");
            }
        } else {
            System.out.println("Файл не передан. Коллекция будет создана пустой.");
        }

        // создаем ввод
        Scanner scanner = new Scanner(System.in);
        InputManager inputManager = new InputManager(scanner);

        // менеджер команд
        CommandManager commandManager = new CommandManager();
        commandManager.setInputManager(inputManager);

        // регистрация всех команд
        commandManager.register("help", new Help());
        commandManager.register("add", new Add(collectionManager, inputManager));
        commandManager.register("update", new Update(collectionManager, inputManager));
        commandManager.register("remove_by_id", new RemoveById(collectionManager, inputManager));
        commandManager.register("show", new Show(collectionManager));
        commandManager.register("info", new Info(collectionManager));
        commandManager.register("clear", new Clear(collectionManager));
        commandManager.register("remove_first", new RemoveFirst(collectionManager));
        commandManager.register("save", new Save(collectionManager, fileManager));
        commandManager.register("execute_script", new ExecuteScript(commandManager, inputManager));
        commandManager.register("add_if_max", new AddIfMax(collectionManager, inputManager));
        commandManager.register("add_if_min", new AddIfMin(collectionManager, inputManager));
        commandManager.register("count_greater_than_governor", new CountGreaterThanGovernor(collectionManager, inputManager));
        commandManager.register("filter_by_government", new FilterByGovernment(collectionManager, inputManager));
        commandManager.register("filter_contains_name", new FilterContainsName(collectionManager, inputManager));
        commandManager.register("exit", new Exit());

        System.out.println("Программа запущена. Введите 'help', чтобы получить список команд.");

        // основной цикл интерактивного ввода
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            String commandName = parts[0];

            // подготовка аргументов для команды
            String[] cmdArgs = new String[parts.length - 1];
            System.arraycopy(parts, 1, cmdArgs, 0, cmdArgs.length);

            // передаем буфер аргументов в InputManager
            inputManager.setArgBuffer(cmdArgs);

            // выполнение команды
            commandManager.execute(commandName);

            // завершение программы
            if (commandName.equals("exit")) break;
        }
    }
}