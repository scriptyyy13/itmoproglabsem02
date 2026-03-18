package exceptions;

/**
 * Исключение, возникающее при ошибках пользовательского ввода.
 *
 * Используется в InputManager и других классах, когда введенные данные не соответствуют требованиям
 * (например: пустая строка, неверный формат числа и т.д.)
 *
 * @author Ыскшзеннн
 * @version 1.0
 */
public class InvalidInputException extends Exception {

    /**
     * Создает исключение без сообщения.
     * Используется, когда не требуется дополнительное описание ошибки.
     */
    public InvalidInputException() {
        super();
    }

    /**
     * Создает исключение с описанием ошибки.
     *
     * @param message текстовое описание причины ошибки
     */
    public InvalidInputException(String message) {
        super(message);
    }

    /**
     * Создает исключение с описанием ошибки и причиной.
     *
     * @param message текстовое описание ошибки
     * @param cause исходное исключение, вызвавшее данную ошибку
     */
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Создает исключение с указанием причины.
     *
     * @param cause исходное исключение, вызвавшее данную ошибку
     */
    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}