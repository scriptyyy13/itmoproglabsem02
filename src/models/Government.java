package models;

/**
 * Перечисление форм правления.
 * Используется в классе City для обозначения типа правительства города.
 *
 * Возможные значения:
 * - GERONTOCRACY — геронтократия
 * - DICTATORSHIP — диктатура
 * - TOTALITARIANISM — тоталитаризм
 *
 * author Ыскшзеннн
 * version 1.0
 */
public enum Government {
    /** Геронтократия */
    GERONTOCRACY,
    /** Диктатура */
    DICTATORSHIP,
    /** Тоталитаризм */
    TOTALITARIANISM;
}