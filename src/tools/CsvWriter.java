package tools;

import models.City;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Класс для сохранения коллекции City в .csv файл.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class CsvWriter {

    /**
     * Сохраняет коллекцию в файл.
     *
     * @param fileName имя файла
     * @param collection коллекция городов
     * @throws IOException если ошибка записи
     */
    public static void write(String fileName, ArrayDeque<City> collection) throws IOException {

        FileWriter writer = new FileWriter(fileName);

        for (City city : collection) {
            writer.write(city.toCsv() + "\n");
        }

        writer.close();
    }
}