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
    private final ZonedDateTime creationDate; // не null, генерируется автоматически
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

        this.id = IdGenerator.generateId();
        this.creationDate = ZonedDateTime.now();
        setData(name, coordinates, area, population, metersAboveSeaLevel,
                establishmentDate, agglomeration, government, governor);
    }

    /**
     * Сеттер города.
     */
    public void setData(String name, Coordinates coordinates, long area, Long population,
                        Long metersAboveSeaLevel, Date establishmentDate,
                        long agglomeration, Government government, Human governor) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name не может быть пустым");
        if (coordinates == null) throw new IllegalArgumentException("Coordinates не могут быть null");
        if (area <= 0) throw new IllegalArgumentException("Area должен быть > 0");
        if (population == null || population <= 0) throw new IllegalArgumentException("Population > 0");

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

    /**
     * Изменение ID города.
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(City other) {
        return this.population.compareTo(other.population);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", population=" + population +
                ", metersAboveSeaLevel=" + metersAboveSeaLevel +
                ", establishmentDate=" + establishmentDate +
                ", agglomeration=" + agglomeration +
                ", government=" + government +
                ", governor=" + governor +
                '}';
    }

    /**
     * Преобразует csv строку в объект City.
     */
    public static City parseCity(String csvLine) {
        String[] fields = csvLine.split(",");
        try {
            long id = Long.parseLong(fields[0]);
            Float x = Float.parseFloat(fields[2]);
            Integer y = Integer.parseInt(fields[3]);
            Coordinates coordinates = new Coordinates(x, y);

            City city = new City(
                    fields[1],
                    coordinates,
                    Long.parseLong(fields[4]),
                    Long.parseLong(fields[5]),
                    (!fields[6].trim().isEmpty()) ? Long.valueOf(fields[6].trim()) : (Long) null,
                    null,
                    Long.parseLong(fields[7]),
                    (fields.length > 8 && !fields[8].isEmpty()) ? Government.valueOf(fields[8]) : (Government) null,
                    null
            );
            return city;
        } catch (Exception e) {
            System.err.println("Ошибка при чтении города: " + e.getMessage());
        }
        return null;
    }

    /**
     * Преобразует объект City в csv строку.
     */
    public String toCsv() {
        return id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + "," + area + "," + population + "," + (metersAboveSeaLevel == null ? "" : metersAboveSeaLevel) + "," + agglomeration + "," + (government == null ? "" : government.name());
    }
}