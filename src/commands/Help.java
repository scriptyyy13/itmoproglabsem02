package commands;

import tools.CommandManager;

/**
 * Команда help.
 * Выводит список доступных команд и их описание.
 *
 * author Ыскшзеннн
 * version 1.0
 */
public class Help implements Command {

    /**
     * Конструктор команды Help.
     */
    public Help() {
        // Пусто, т.к. ничего не требуется
    }

    /**
     * Выполняет команду.
     * Выводит список всех доступных команд с их описаниями.
     */
    @Override
    public void execute() {
        System.out.println("Список команд:");
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести информацию о коллекции");
        System.out.println("show : вывести все элементы коллекции");
        System.out.println("add {element} : добавить новый элемент в коллекцию");
        System.out.println("update id {element} : обновить элемент по id");
        System.out.println("remove_by_id id : удалить элемент по id");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script file_name : выполнить команды из файла");
        System.out.println("exit : завершить программу");
        System.out.println("remove_first : удалить первый элемент коллекции");
        System.out.println("add_if_max {element} : добавить элемент, если он больше всех");
        System.out.println("add_if_min {element} : добавить элемент, если он меньше всех");
        System.out.println("count_greater_than_governor governor : вывести количество элементов с governor больше заданного");
        System.out.println("filter_by_government government : вывести элементы с заданной формой правления");
        System.out.println("filter_contains_name name : вывести элементы, содержащие подстроку в имени");
    }
}