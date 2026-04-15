package tools;

import models.City;
import java.io.*;

/**
 * Класс для логирования операций (WAL).
 * Каждая операция записывается в отдельный файл перед применением.
 */
public class LogOperations {
    private final String logFile;

    public LogOperations(String fileName) {
        this.logFile = fileName.replace(".csv", ".log");
    }

    /**
     * Логирует операцию добавления
     */
    public void logAdd(City city) {
        writeLog("ADD|" + serializeCity(city));
    }

    /**
     * Логирует операцию обновления
     */
    public void logUpdate(long id, City city) {
        writeLog("UPDATE|" + id + "|" + serializeCity(city));
    }

    /**
     * Логирует операцию удаления
     */
    public void logRemove(long id) {
        writeLog("REMOVE|" + id);
    }

    /**
     * Логирует очистку
     */
    public void logClear() {
        writeLog("CLEAR");
    }

    /**
     * Очищает лог (после успешного сохранения)
     */
    public void clearLog() {
        new File(logFile).delete();
    }

    private void writeLog(String entry) {
        try (FileWriter fw = new FileWriter(logFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(entry);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            System.err.println("Ошибка записи лога: " + e.getMessage());
        }
    }

    private String serializeCity(City city) {
        return city.toCsv();
    }

    public String getLogFile() {
        return logFile;
    }
}