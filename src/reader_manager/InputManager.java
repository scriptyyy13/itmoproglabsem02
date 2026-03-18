package reader_manager;

import models.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс для безопасного чтения пользовательского ввода.
 * Обрабатывает ошибки и повторяет ввод при некорректных данных.
 * Используется для создания объектов City, Coordinates, Human и Government.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class InputManager {

    /** Сканер для интерактивного ввода */
    private final Scanner scanner;

    /** Буфер аргументов команды (если команда передана в одной строке) */
    private String[] argBuffer = null;

    /** Индекс текущего аргумента в буфере */
    private int argIndex = 0;

    /**
     * Конструктор InputManager.
     *
     * @param scanner сканер для консольного ввода
     */
    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Устанавливает буфер аргументов команды.
     * Если установлен, все вызовы методов чтения будут сначала пытаться
     * брать значения из этого буфера, а не спрашивать пользователя.
     *
     * @param args массив аргументов команды
     */
    public void setArgBuffer(String[] args) {
        this.argBuffer = args;
        this.argIndex = 0;
    }

    /**
     * Берет следующий аргумент из буфера.
     * Если буфер пуст или все аргументы использованы, возвращает null.
     *
     * @return следующий аргумент команды или null
     */
    private String nextArg() {
        if (argBuffer != null && argIndex < argBuffer.length) {
            return argBuffer[argIndex++];
        }
        return null;
    }

    /**
     * Читает непустую строку.
     *
     * @param message сообщение пользователю при интерактивном вводе
     * @return введенная строка
     */
    public String readNonEmptyString(String message) {
        String arg = nextArg();
        if (arg != null) return arg;
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();
            if (!input.isBlank()) return input;
            System.out.println("Строка не может быть пустой.");
        }
    }

    /**
     * Читает long-значение.
     *
     * @param message сообщение пользователю
     * @param nullable допускается ли null
     * @return введенное значение или null
     */
    public Long readLong(String message, boolean nullable) {
        String arg = nextArg();
        if (arg != null) {
            if (nullable && arg.isBlank()) return null;
            try {
                long value = Long.parseLong(arg);
                if (value <= 0) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение в аргументе команды: " + arg);
            }
        }
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();
            if (nullable && input.isBlank()) return null;
            try {
                long value = Long.parseLong(input);
                if (value <= 0) {
                    System.out.println("Число должно быть > 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }

    /**
     * Читает float-значение.
     *
     * @param message сообщение пользователю
     * @return введенное значение
     */
    public Float readFloat(String message) {
        String arg = nextArg();
        if (arg != null) {
            try {
                return Float.parseFloat(arg);
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение в аргументе команды: " + arg);
            }
        }
        while (true) {
            System.out.println(message);
            try {
                return Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }

    /**
     * Читает int-значение с ограничением по максимальному значению.
     *
     * @param message сообщение пользователю
     * @param maxValue максимальное допустимое значение
     * @return введенное значение
     */
    public Integer readInt(String message, int maxValue) {
        String arg = nextArg();
        if (arg != null) {
            try {
                int value = Integer.parseInt(arg);
                if (value > maxValue) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение в аргументе команды: " + arg);
            }
        }
        while (true) {
            System.out.println(message);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > maxValue) {
                    System.out.println("Значение не должно превышать " + maxValue);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }
    }

    /**
     * Читает значение enum Government.
     *
     * @return выбранное значение Government или null
     */
    public Government readGovernment() {
        String arg = nextArg();
        if (arg != null && !arg.isBlank()) {
            try {
                return Government.valueOf(arg);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение в аргументе команды: " + arg);
            }
        }
        while (true) {
            System.out.println("Выберите Government (пусто = null):");
            for (Government g : Government.values()) {
                System.out.println(g.name());
            }
            String input = scanner.nextLine();
            if (input.isBlank()) return null;
            try {
                return Government.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверное значение enum.");
            }
        }
    }

    /**
     * Читает объект Human.
     *
     * @return введенный Human или null
     */
    public Human readHuman() {
        String arg = nextArg();
        if (arg != null && !arg.isBlank()) {
            String name = arg;
            int height = readInt("Введите height (>0):", Integer.MAX_VALUE);
            return new Human(name, height, ZonedDateTime.now());
        }

        System.out.println("Введите governor (пусто = null):");
        String name = scanner.nextLine();
        if (name.isBlank()) return null;

        int height = readInt("Введите height (>0):", Integer.MAX_VALUE);
        return new Human(name, height, ZonedDateTime.now());
    }

    /**
     * Читает объект City по полям.
     *
     * @return введенный City
     */
    public City readCity() {
        String name = readNonEmptyString("Введите name:");
        Float x = readFloat("Введите coordinates.x:");
        Integer y = readInt("Введите coordinates.y (<=150):", 150);
        Coordinates coordinates = new Coordinates(x, y);
        long area = readLong("Введите area (>0):", false);
        Long population = readLong("Введите population (>0):", false);
        Long meters = readLong("Введите metersAboveSeaLevel (пусто = null):", true);
        long agglomeration = readLong("Введите agglomeration (>0):", false);
        Government government = readGovernment();
        Human governor = readHuman();

        return new City(name, coordinates, area, population, meters, new Date(), agglomeration, government, governor);
    }
}