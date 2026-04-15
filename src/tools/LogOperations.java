package tools;

import models.City;
import reader_manager.InputManager;
import reader_manager.OutputManager;

import java.io.*;
import java.util.Iterator;

/**
 * Класс для логирования операций (WAL).
 * Все операции записываются после применения команд, изменяющих коллекцию.
 */
public class LogOperations {
    private final String logFile;
    private boolean isLogActive = false;
    private final InputManager input;

    public LogOperations(String fileName, InputManager inputManager) {
        this.logFile = fileName.replace(".csv", ".log");
        this.input = inputManager;
    }

    /**
     * Переключение логирования
     */
    public void changeIsLogActive(boolean isLogActive) {
        this.isLogActive = isLogActive;
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
     * Читает лог и заново выполняет все операции.
     */
    public void recoverFromLog(CollectionManager collectionManager) {
        File file = new File(logFile);
        if (!file.exists()) return;
        OutputManager.println("При последнем выполнении программа прервалась до сохранения.");
        String answer = input.readNonEmptyString("Восстановить несохраненные изменения? (да/нет): ");

        if (!(answer.startsWith("д") || answer.startsWith("y"))) {
            clearLog();
            return;
        };
        // при получении положительного ответа восстанавливаем
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Integer operationsCount = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 2); // разделяем команду и данные
                String command = parts[0];

                if (command.equals("ADD")) {
                    collectionManager.add(City.parseCity(parts[1]));
                }
                else if (command.equals("UPDATE")) {
                    String[] info = parts[1].split("\\|", 2); // т.к. формат UPDATE|id|City
                    long id = Long.parseLong(info[0]);
                    collectionManager.update(id, City.parseCity(info[1]));
                }
                else if (command.equals("REMOVE")) {
                    long id = Long.parseLong(parts[1]);
                    // используем итератор, чтобы не было ошибки при удалении
                    Iterator<City> it = collectionManager.getCollection().iterator();
                    while (it.hasNext()) {
                        if (it.next().getId() == id) {
                            it.remove();
                            break;
                        }
                    }
                }
                else if (command.equals("CLEAR")) {
                    collectionManager.clear();
                }
                operationsCount++;
            }
            if (operationsCount > 0) {
                OutputManager.println("Восстановлено операций: " + operationsCount + "\n" +
                        "Не забудьте сохраниться! (команда save)");
            }
        } catch (Exception e) {
            OutputManager.errPrintln("Ошибка при чтении лога: " + e.getMessage());
        } finally {
            clearLog(); // удаляем лог после восстановления
        }
    }

    /**
     * Очищает лог (после успешного сохранения)
     */
    public void clearLog() {
        new File(logFile).delete();
    }

    private void writeLog(String entry) {
        if (!isLogActive) return;
        try (FileWriter fw = new FileWriter(logFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(entry);
                bw.newLine();
                bw.flush();
        } catch (IOException e) {
            OutputManager.errPrintln("Ошибка записи лога: " + e.getMessage());
        }
    }

    private String serializeCity(City city) {
        return city.toCsv();
    }

    public String getLogFile() {
        return logFile;
    }
}