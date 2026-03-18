package tools;

/**
 * Класс для генерации уникальных id.
 * Используется для автоматического присвоения id объектам City.
 *
 * id:
 * Всегда > 0
 * Уникальны
 * Генерируются автоматически
 *
 * @author Ыскшзеннн
 * @version 1.0
 */
public class IdGenerator {

    /**
     * Текущий максимальный id.
     */
    private static long currentId = 1;

    /**
     * Генерирует новый уникальный id.
     *
     * @return уникальный id > 0
     */
    public static long generateId() {
        return currentId++;
    }

    /**
     * Обновляет текущий id при загрузке из файла.
     * Нужно, чтобы новые элементы не повторяли старые id.
     *
     * @param existingId id из файла
     */
    public static void updateId(long existingId) {
        if (existingId >= currentId) {
            currentId = existingId + 1;
        }
    }

    /**
     * Сбрасывает генератор.
     */
    public static void reset() {
        currentId = 1;
    }
}