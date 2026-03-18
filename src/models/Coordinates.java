package models;

/**
 * Класс, описывающий координаты города.
 * Используется внутри класса City для хранения положения города.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Coordinates {

    /** Координата X (не null) */
    private Float x;

    /** Координата Y (не null, <=150) */
    private Integer y;

    /**
     * Конструктор координат.
     *
     * @param x координата X (не null)
     * @param y координата Y (не null, должно быть <= 150)
     * @throws IllegalArgumentException если x или y некорректны
     */
    public Coordinates(Float x, Integer y) {
        if (x == null) throw new IllegalArgumentException("x не может быть null");
        if (y == null || y > 150) throw new IllegalArgumentException("y не может быть null и <=150");

        this.x = x;
        this.y = y;
    }

    /** @return координату X */
    public Float getX() { return x; }

    /** @return координату Y */
    public Integer getY() { return y; }

    /**
     * Строковое представление координат.
     *
     * @return строка в формате "x,y"
     */
    @Override
    public String toString() {
        return x + "," + y;
    }
}