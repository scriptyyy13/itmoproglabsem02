package models;

import tools.IdGenerator;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Класс города. Хранится в коллекции.
 * Реализует Comparable для сортировки по population.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class City implements Comparable<City> {

    private long id; // >0, уникальный, генерируется автоматически
    private String name; // не null, не пустой
    private Coordinates coordinates; // не null
    private ZonedDateTime creationDate; // не null, генерируется автоматически
    private long area; // >0
    private Long population; // >0, не null
    private Long metersAboveSeaLevel; // может быть null
    private Date establishmentDate; // может быть null
    private long agglomeration; // >0
    private Government government; // может быть null
    private Human governor; // может быть null

    /**
     * Конструктор города.
     */
    public City(String name,
                Coordinates coordinates,
                long area,
                Long population,
                Long metersAboveSeaLevel,
                Date establishmentDate,
                long agglomeration,
                Government government,
                Human governor) {

        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name не может быть пустым");
        if (coordinates == null) throw new IllegalArgumentException("Coordinates не могут быть null");
        if (area <= 0) throw new IllegalArgumentException("Area должен быть > 0");
        if (population == null || population <= 0) throw new IllegalArgumentException("Population > 0");

        this.id = IdGenerator.generateId();
        this.creationDate = ZonedDateTime.now();

        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.establishmentDate = establishmentDate;
        this.agglomeration = agglomeration;
        this.government = government;
        this.governor = governor;
    }

    // гетеры
    public long getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public ZonedDateTime getCreationDate() { return creationDate; }
    public long getArea() { return area; }
    public Long getPopulation() { return population; }
    public Long getMetersAboveSeaLevel() { return metersAboveSeaLevel; }
    public Date getEstablishmentDate() { return establishmentDate; }
    public long getAgglomeration() { return agglomeration; }
    public Government getGovernment() { return government; }
    public Human getGovernor() { return governor; }

    @Override
    public int compareTo(City other) {
        return this.population.compareTo(other.population);
    }

    @Override
    public String toString() {
        return "City{id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", government=" + government +
                '}';
    }

    /**
     * Преобразует объект City в csv строку.
     */
    public String toCsv() {
        return id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + "," + area + "," + population + "," + (metersAboveSeaLevel == null ? "" : metersAboveSeaLevel) + "," + agglomeration + "," + (government == null ? "" : government.name());
    }
}