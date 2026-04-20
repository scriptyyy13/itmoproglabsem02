package models;

import java.time.ZonedDateTime;

/**
 * Класс, описывающий человека, например губернатора города.
 * Используется в классе City для хранения информации о governor.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Human {

    /** Имя человека (не null, не пустое) */
    private final String name;

    /** Рост человека (>0) */
    private final int height;

    /** Дата рождения человека (может быть null) */
    private final ZonedDateTime birthday;

    /**
     * Конструктор человека.
     *
     * @param name имя человека, не null и не пустое
     * @param height рост человека (>0)
     * @param birthday дата рождения (может быть null)
     * @throws IllegalArgumentException если name пустое или height <= 0
     */
    public Human(String name, int height, ZonedDateTime birthday) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Имя не может быть пустым");
        if (height <= 0)
            throw new IllegalArgumentException("Рост должен быть > 0");

        this.name = name;
        this.height = height;
        this.birthday = birthday;
    }

    /** @return имя человека */
    public String getName() { return name; }

    /** @return рост человека */
    public int getHeight() { return height; }

    /** @return дату рождения человека */
    public ZonedDateTime getBirthday() { return birthday; }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", birthday=" + birthday +
                '}';
    }
}