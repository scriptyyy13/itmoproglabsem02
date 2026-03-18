package tools;

import models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс для чтения коллекции City из .csv
 *
 * Формат:
 * id,name,x,y,area,population,metersAboveSeaLevel,agglomeration,government
 *
 * @author Ыскшзеннн
 * @version 1.0
 */
public class CsvReader {

    /**
     * Загружает коллекцию из .csv файла.
     *
     * @param fileName имя файла
     * @return коллекция ArrayDeque<City>
     * @throws FileNotFoundException если файл не найден
     *
     * author Ыскшзеннн
     * version 1.0
     */
    public static ArrayDeque<City> read(String fileName) throws FileNotFoundException {

        ArrayDeque<City> collection = new ArrayDeque<>();

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден");
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] fields = line.split(",");

            try {
                long id = Long.parseLong(fields[0]);
                String name = fields[1];
                Float x = Float.parseFloat(fields[2]);
                Integer y = Integer.parseInt(fields[3]);
                long area = Long.parseLong(fields[4]);
                Long population = Long.parseLong(fields[5]);

                Long metersAboveSeaLevel = fields[6].isEmpty() ? null :
                        Long.parseLong(fields[6]);

                long agglomeration = Long.parseLong(fields[7]);

                Government government = fields[8].isEmpty() ? null :
                        Government.valueOf(fields[8]);

                Coordinates coordinates = new Coordinates(x, y);

                City city = new City(
                        name,
                        coordinates,
                        area,
                        population,
                        metersAboveSeaLevel,
                        null,
                        agglomeration,
                        government,
                        null
                );

                IdGenerator.updateId(id);

                collection.add(city);

            } catch (Exception e) {
                System.out.println("Ошибка в строке: " + line);
            }
        }

        scanner.close();
        return collection;
    }
}