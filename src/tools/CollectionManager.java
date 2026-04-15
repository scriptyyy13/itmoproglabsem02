package tools;

import models.City;

import java.util.ArrayDeque;

/**
 * Класс для управления коллекцией объектов City.
 * Содержит методы добавления, обновления, удаления и очистки коллекции.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class CollectionManager {

    /**
     * Основная коллекция, которая хранит объекты City.
     */
    private final ArrayDeque<City> collection = new ArrayDeque<>();
    private LogOperations logger;

    /**
     * Установить логгер.
     */
    public void setLogger(LogOperations logger) {
        this.logger = logger;
    }

    /**
     * Возвращает коллекцию.
     *
     * @return коллекция City
     */
    public ArrayDeque<City> getCollection() {//todo setter + no static method
        return collection;
    }

    /**
     * Добавляет новый объект City в коллекцию.
     *
     * @param city объект City для добавления
     */
    public void add(City city) {
        if (logger != null) logger.logAdd(city);
        collection.add(city);
    }

    /**
     * Обновляет элемент коллекции по его id.
     * Старый объект удаляется, а на его место добавляется новый.
     *
     * @param id идентификатор элемента для обновления
     * @param newCity новый объект City для замены
     * @throws IllegalArgumentException если элемент с таким id не найден
     */
    public void update(long id, City newCity) {
        City toRemove = null;

        for (City city : collection) {
            if (city.getId() == id) {
                toRemove = city;
                break;
            }
        }

        if (toRemove != null) {
            if (logger != null) logger.logUpdate(id, newCity);
            newCity.setId(id);

            collection.remove(toRemove);
            collection.add(newCity);
        } else {
            throw new IllegalArgumentException("Элемент с id " + id + " не найден.");
        }
    }

    /**
     * Удаляет первый элемент коллекции.
     * Если коллекция пуста, ничего не делает.
     */
    public void removeFirst() {
        logger.logRemove(collection.getFirst().getId());
        collection.pollFirst();
    }

    /**
     * Очищает всю коллекцию.
     */
    public void clear() {
        if (logger != null) logger.logClear();
        collection.clear();
    }

    /**
     * Возвращает количество элементов в коллекции.
     *
     * @return размер коллекции
     */
    public int size() {
        return collection.size();
    }
}