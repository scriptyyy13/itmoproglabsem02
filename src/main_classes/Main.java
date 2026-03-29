package main_classes;

import reader_manager.FileManager;
import reader_manager.InputManager;
import reader_manager.OutputManager;
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
        FileManager fileManager = initFileManager(fileName, collectionManager);

        // создаем ввод
        Scanner scanner = new Scanner(System.in);
        InputManager inputManager = new InputManager(scanner);

        // менеджер команд
        CommandManager commandManager = new CommandManager();
        commandManager.setInputManager(inputManager);
        // регистрация всех команд
        registerAllCommands(commandManager, collectionManager, inputManager, fileManager);

        // запускаем программу
        App app = new App(commandManager, inputManager);
        app.run();
    }

    private static FileManager initFileManager(String fileName, CollectionManager col) {
        if (fileName == null) {
            OutputManager.println("Файл не передан. Коллекция пуста.");
            return null;
        }
        try {
            FileManager fm = new FileManager(fileName);
            col.getCollection().addAll(fm.load());
            OutputManager.println("Данные загружены.");
            return fm;
        } catch (Exception e) { // Здесь лучше заменить на конкретные типы
            OutputManager.errPrintln("Ошибка загрузки: " + e.getMessage());
            return null;
        }
    }

    private static void registerAllCommands(CommandManager cm, CollectionManager col, InputManager in, FileManager fm) {
        cm.register("help", new Help(cm));
        cm.register("add", new Add(col, in));
        cm.register("update", new Update(col, in));
        cm.register("remove_by_id", new RemoveById(col, in));
        cm.register("show", new Show(col));
        cm.register("info", new Info(col));
        cm.register("clear", new Clear(col));
        cm.register("remove_first", new RemoveFirst(col));
        cm.register("save", new Save(col, fm));
        cm.register("execute_script", new ExecuteScript(cm, in));
        cm.register("add_if_max", new AddIfMax(col, in));
        cm.register("add_if_min", new AddIfMin(col, in));
        cm.register("count_greater_than_governor", new CountGreaterThanGovernor(col, in));
        cm.register("filter_by_government", new FilterByGovernment(col, in));
        cm.register("filter_contains_name", new FilterContainsName(col, in));
        cm.register("exit", new Exit());
    }

}