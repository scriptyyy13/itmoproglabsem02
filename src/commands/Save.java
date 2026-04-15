package commands;

import models.City;
import reader_manager.OutputManager;
import tools.CollectionManager;
import reader_manager.FileManager;
import tools.LogOperations;

import java.util.ArrayDeque;

/**
 * Команда save.
 * Сохраняет текущую коллекцию объектов City в .csv
 * Если файл не существует, автоматически создается default.csv
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Save implements Command {

    private final CollectionManager collectionManager;
    private FileManager fileManager;
    private String fileName;
    private LogOperations logger;

    /**
     * Конструктор команды Save.
     *
     * @param collectionManager менеджер коллекции
     * @param fileManager менеджер для работы с файлом (может быть null)
     */
    public Save(CollectionManager collectionManager, FileManager fileManager, LogOperations logger) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        if (fileManager != null) {
            this.fileName = fileManager.getFileName();
        }
        this.logger = logger;
    }

    /**
     * Выполняет команду.
     * Сохраняет коллекцию.
     */
    @Override
    public void execute() {
        ArrayDeque<City> collection = collectionManager.getCollection();

        if (collection.isEmpty()) {
            OutputManager.errPrintln("Коллекция пуста. Сохранять нечего.");
            return;
        } else {
            if (logger != null) {
                logger.clearLog();
                OutputManager.println("Лог операций очищен.");
            }
        }

        fileManager.save(collection);
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}