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
            writer.write(cityToCsv(city) + "\n");
        }

        writer.close();
    }

    /**
     * Преобразует City в строку .csv
     */
    private static String cityToCsv(City city) {

        return city.getId() + "," + city.getName() + "," + city.getCoordinates().getX() + "," + city.getCoordinates().getY() + "," + city.getArea() + "," + city.getPopulation() + "," + (city.getMetersAboveSeaLevel() == null ? "" : city.getMetersAboveSeaLevel()) + "," + city.getAgglomeration() + "," + (city.getGovernment() == null ? "" : city.getGovernment().name());
    }
}