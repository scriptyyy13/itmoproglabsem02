package reader_manager;

import models.City;
import tools.CsvReader;
import tools.CsvWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Класс для управления загрузкой и сохранением коллекции.
 * Работает с .csv
 *
 * author Ыскшзеннн
 * version 1.1
 */
public class FileManager {

    private final String fileName;

    /**
     * Конструктор FileManager.
     *
     * @param fileName имя файла .csv
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Возвращает имя файла.
     *
     * @return имя .csv
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Загружает коллекцию из .csv
     * Если файла нет, возвращает пустую коллекцию.
     *
     * @return коллекция объектов City
     */
    public ArrayDeque<City> load() {
        try {
            return CsvReader.read(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Коллекция будет пустой.");
            return new ArrayDeque<>();
        }
    }

    /**
     * Сохраняет коллекцию в .csv
     *
     * @param collection коллекция объектов City
     */
    public void save(ArrayDeque<City> collection) {
        try {
            CsvWriter.write(fileName, collection);
            System.out.println("Коллекция сохранена.");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}