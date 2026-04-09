package reader_manager;

import models.City;
import tools.CsvReader;
import tools.CsvWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для управления загрузкой и сохранением коллекции с восстановлением при сбое.
 * Работает с .csv
 *
 * author Ыскшзеннн
 * version 1.2
 */
public class FileManager {


    private final String fileName;
    private final String backupFile;
    private final String tempFile;
    private final Boolean isFileCreated;
    private final InputManager input;

    /**
     * Конструктор FileManager.
     *
     * @param fileName имя файла .csv
     * @param im менеджер для чтения пользовательского ввода
     */
    public FileManager(String fileName, InputManager im, boolean isFileCreated) {
        this.fileName = fileName;
        this.input = im;
        this.isFileCreated = isFileCreated;
        this.backupFile = fileName.replace(".csv", ".backup");
        this.tempFile = fileName.replace(".csv", ".temp");
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
     * Если основной файл недоступен - загружает из backup.
     *
     * @return коллекция объектов City
     */
    public ArrayDeque<City> load() {
        try {
            return CsvReader.read(fileName, false);
        } catch (FileNotFoundException e) {
            if (!isFileCreated) {
                OutputManager.println("Основной файл не найден, загружаю из резервной копии...");
            }
            try {
                return CsvReader.read(backupFile, false);
            } catch (FileNotFoundException e2) {
                OutputManager.println("Коллекция будет пустой.");
                return new ArrayDeque<>();
            }
        }
    }

    /**
     * Сохраняет коллекцию в .csv
     *
     * @param collection коллекция объектов City
     */
    public void save(ArrayDeque<City> collection) {
        // шаг 1: пишем backup перед основным файлом
        try {
            CsvWriter.write(backupFile, collection);
        } catch (IOException e) {
            OutputManager.errPrintln("Ошибка создания резервной копии: " + e.getMessage());
            return;
        }

        // шаг 2: пытаемся писать в основной файл
        try {
            CsvWriter.write(fileName, collection);
            OutputManager.println("Коллекция сохранена.");
        } catch (IOException e) {
            OutputManager.errPrintln("Ошибка доступа к файлу: " + e.getMessage());

            // если временного файла нет спрашиваем, иначе молчим
            if (!new File(tempFile).exists()) {
                handleUnavailableFile(collection);
            } else {
                OutputManager.println("Коллекция сохранена в временный файл: " + tempFile);
            }
        }
    }

    /**
     * Обработка недоступности основного файла.
     */
    private void handleUnavailableFile(ArrayDeque<City> collection) {
        OutputManager.println("Файл недоступен или произошла ошибка записи.");

        String answer = input.readNonEmptyString("Создать временный файл для сохранения? (да/нет) ");

        if (answer.startsWith("д") || answer.startsWith("y")) {
            try {
                CsvWriter.write(tempFile, collection);
                OutputManager.println("Временный файл создан: " + tempFile);
                OutputManager.println("Работа продолжается с временным файлом.");
            } catch (IOException ex) {
                OutputManager.errPrintln("Ошибка создания временного файла: " + ex.getMessage());
            }
        }
    }

    /**
     * Проверяет при загрузке, восстановлен ли доступ к основному файлу.
     * Если восстановлен и есть временный - мержит данные.
     */
    public void attemptMerge(ArrayDeque<City> collection) {
        File tempFileObj = new File(tempFile);

        // временного файла нет - просто загружаем
        if (!tempFileObj.exists()) {
            try {
                ArrayDeque<City> mainData = CsvReader.read(fileName, true);
                collection.clear();
                collection.addAll(mainData);
            } catch (FileNotFoundException e) {
                // Нет основного файла - ничего не делаем
            }
            return;
        }

        // временный файл есть - мержим
        try {
            ArrayDeque<City> mainData = CsvReader.read(fileName, true);
            ArrayDeque<City> tempData = CsvReader.read(tempFile, true);

            OutputManager.println(mainData.toString());
            OutputManager.println(tempData.toString());

            // берем все из основного + новые из временного
            Set<Long> mainIds = new HashSet<>();
            for (City city : mainData) {
                mainIds.add(city.getId());
            }

            OutputManager.println(mainIds.toString());
            collection.clear();
            collection.addAll(mainData);

            for (City city : tempData) {
                if (!mainIds.contains(city.getId())) {
                    collection.add(city);
                }
            }

            tempFileObj.delete();
            OutputManager.println("Доступ восстановлен! Данные объединены.");

        } catch (FileNotFoundException e) {
            // основной недоступен - загружаем из временного
            try {
                ArrayDeque<City> tempData = CsvReader.read(tempFile, true);
                collection.clear();
                collection.addAll(tempData);
            } catch (FileNotFoundException e2) {
                OutputManager.errPrintln("Временный и основной файлы недоступны.");
            }
        }
    }
}