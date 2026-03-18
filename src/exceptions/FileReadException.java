package exceptions;

/**
 * Исключение, возникающее при ошибках чтения файла.
 *
 * Используется в FileManager и CsvReader, когда:
 * Файл не найден,
 * Нет прав доступа,
 * Произошла ошибка при чтении данных.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class FileReadException extends Exception {

    /**
     * Создает исключение без сообщения.
     */
    public FileReadException() {
        super();
    }

    /**
     * Создает исключение с описанием ошибки.
     *
     * @param message текстовое описание причины ошибки
     */
    public FileReadException(String message) {
        super(message);
    }

    /**
     * Создает исключение с описанием ошибки и причиной.
     *
     * @param message текстовое описание ошибки
     * @param cause исходное исключение, вызвавшее данную ошибку
     */
    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Создает исключение с указанием причины.
     *
     * @param cause исходное исключение, вызвавшее данную ошибку
     */
    public FileReadException(Throwable cause) {
        super(cause);
    }
}