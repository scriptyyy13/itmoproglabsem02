package commands;

import models.City;
import reader_manager.OutputManager;
import tools.CollectionManager;
import reader_manager.FileManager;

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

    /**
     * Конструктор команды Save.
     *
     * @param collectionManager менеджер коллекции
     * @param fileManager менеджер для работы с файлом (может быть null)
     */
    public Save(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        if (fileManager != null) {
            this.fileName = fileManager.getFileName();
        }
    }

    /**
     * Выполняет команду.
     * Сохраняет все элементы коллекции в файл.
     * Если менеджер файла не передан, создаёт новый файл default.csv
     */
    @Override
    public void execute() {
        ArrayDeque<City> collection = collectionManager.getCollection();

        if (collection.isEmpty()) {
            OutputManager.errPrintln("Коллекция пуста. Сохранять нечего.");
            return;
        }

        fileManager.save(collection);
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}