package project_2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project_2.enums.TaskStatusEnum;
import project_2.model.TaskRequest;
import project_2.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;
    private Scanner in = new Scanner(System.in);

    public void command(String nameCommand) {
        switch (nameCommand) {
            case "add" -> add();
            case "list" -> list();
            case "edit" -> edit();
            case "delete" -> delete();
            case "filter" -> filter();
            case "sort" -> sort();
            case "exit" -> exit();
            default -> log.warn("Команда {} не найдена", nameCommand);
        }
    }

    private void add() {
        log.info("Добавление задачи в репозиторий");
        TaskRequest taskRequest = this.createRequest();
        Integer id = taskService.addTask(taskRequest);
        log.info("Задача добавлена в репозиторий под идентификатором: {}", id);
    }

    private void list() {
        log.info("Вывод всех задач начался");
        List<TaskRequest> tasks = taskService.getAll();
        tasks.forEach(System.out::println);
        log.info("Вывод всех задач закончился");
    }

    private void edit() {
        System.out.println("""
                Интерфейс для изменения существующей задачи
                Введите необходимые параметры:
                """);
        Integer index = this.printfInteger("Идентификатор");
        System.out.println("""
                По каким параметрам производить изменения?
                <=0 - по первым трём
                >=1 - только по статусу
                """);
        Integer choiceNumber = this.printfInteger("Выберите значение");
        log.info("Изменение данных задачи по идентификатору началось");
        if (choiceNumber <= 0) {
            TaskRequest taskRequest = this.createRequest();
            taskService.changeByTaskRequest(taskRequest, index);
        } else {
            Supplier<TaskStatusEnum> supplier = () -> {
                String status = this.printf("Статус");
                return TaskStatusEnum.valueOf(status.toUpperCase());
            };
            Runnable runnable = () -> {
                log.warn("Статус не найден. Попробуйте снова!");
            };
            TaskStatusEnum taskStatusEnum = this.doWhileParsed(supplier, runnable);
            taskService.changeByStatus(taskStatusEnum, index);
        }
        log.info("Изменение данные задачи по идентификатору закончилось");
    }

    private void delete() {

    }

    private void filter() {

    }

    private void sort() {

    }

    private void exit() {

    }

    private TaskRequest createRequest() {
        System.out.println("""
                Интерфейс для создания запроса
                Введите необходимые параметры:
                """);
        String name = this.printf("Название");
        String description = this.printf("Описание");
        Supplier<LocalDate> supplier = () -> {
            System.out.print("Время в формате(): ");
            String timeLimitString = in.nextLine();
            return LocalDate.parse(timeLimitString);
        };
        Runnable runnable = () -> log.warn("Время не в нужном формате!");
        LocalDate timeLimit = this.doWhileParsed(supplier, runnable);
        return new TaskRequest(name, description, timeLimit);
    }

    private String printf(String name) {
        System.out.printf("%s: ", name);
        return in.nextLine();
    }

    private Integer printfInteger(String name) {
        String stringValue = this.printf(name);
        Supplier<Integer> supplier = () -> Integer.parseInt(stringValue);
        Runnable runnable = () -> {
            log.warn("Конвертировать {} в целочисленное значение не получится, введите еще раз!", stringValue);
        };
        return this.doWhileParsed(supplier, runnable);
    }

    private <T> T doWhileParsed(Supplier<T> supplier, Runnable runnable) {
        boolean isParsed;
        T t = null;
        do {
            isParsed = true;
            try {
                t = supplier.get();
            } catch (RuntimeException e) {
                isParsed = false;
                runnable.run();
            }
        } while (!isParsed);
        return t;
    }

}
