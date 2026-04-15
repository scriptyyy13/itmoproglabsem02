package tools;

import models.*;
import reader_manager.OutputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
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
    public static ArrayDeque<City> read(String fileName, boolean correctIds) throws FileNotFoundException {

        ArrayDeque<City> collection = new ArrayDeque<>();

        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден");
        }

        Scanner scanner = new Scanner(file);

        int missedLines = 0;
        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] fields = line.split(",");
            try {
                long id = Long.parseLong(fields[0]);
                City city = City.parseCity(line);
                if (!correctIds) {
                    IdGenerator.updateId(id);
                }
                collection.add(city);
                if (correctIds) {
                    collection.peekLast().setId(id);
                    if (IdGenerator.getCurrentId() < id) {
                        IdGenerator.updateId(id);
                    }
                }

            } catch (Exception e) {
                missedLines++;
            }
        }

        if (missedLines > 0) {
            OutputManager.errPrintln("Не удалось прочитать " + missedLines + " строк, неудачные элементы были пропущены");
        }

        scanner.close();
        return collection;
    }
}