package project_2.controller;

import project_2.annotations.LogBE;

public interface TaskController {

    @LogBE(message = "Добавление задачи в репозиторий")
    void add();

    @LogBE(message = "Вывод всех задач")
    void list();

    @LogBE(message = "Изменение данных задачи по идентификатору")
    void edit();

    @LogBE(message = "Удаление задачи по идентификатору")
    void delete();

    @LogBE(message = "Вывод всех задач с заданным статусом")
    void filter();

    @LogBE(message = "Вывод всех отсортированных задач")
    void sort();

    void exit();

}
