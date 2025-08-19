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
public class TaskControllerImpl implements TaskController{

    private final TaskService taskService;
    private Scanner in = new Scanner(System.in);

    public void add() {
        TaskRequest taskRequest = this.createRequest();
        Integer id = taskService.addTask(taskRequest);
        log.info("Задача добавлена в репозиторий под идентификатором: {}", id);
    }

    public void list() {
        List<TaskRequest> tasks = taskService.getAll();
        tasks.forEach(System.out::println);
    }

    public void edit() {
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
        if (choiceNumber <= 0) {
            TaskRequest taskRequest = this.createRequest();
            taskService.changeByTaskRequest(taskRequest, index);
        } else {
            TaskStatusEnum taskStatusEnum = this.printfStatus();
            taskService.changeByStatus(taskStatusEnum, index);
        }
    }

    public void delete() {
        Integer index = this.printfInteger("Идентификатор");
        taskService.deleteTask(index);
    }

    public void filter() {
        TaskStatusEnum taskStatusEnum = this.printfStatus();
        List<TaskRequest> filteredTasks = taskService.getAllFilterByStatus(taskStatusEnum);
        filteredTasks.forEach(System.out::println);
    }

    public void sort() {
        System.out.println("""
                Интерфейс для сортировки задач
                По каким параметрам сортировать?
                <=0 - по сроку выполнения
                >=1 - по статусу
                """);
        Integer choiceNumber = this.printfInteger("Выберите значение");
        List<TaskRequest> sortedTasks = choiceNumber <= 0 ? taskService.getAllSortedByTimeLimit() :
                taskService.getAllSortedByStatus();
        sortedTasks.forEach(System.out::println);
    }

    public void exit() {
        log.info("Завершение работы программы");
        System.exit(0);
    }

    private TaskRequest createRequest() {
        System.out.print("""
                Интерфейс для создания запроса
                Введите необходимые параметры:
                """);
        String name = this.printf("Название");
        String description = this.printf("Описание");
        Supplier<LocalDate> supplier = () -> {
            System.out.print("Время в формате(yyyy-mm-dd): ");
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
        Runnable runnable = () -> log.warn("Конвертировать {} в целочисленное значение не получится, введите еще раз!", stringValue);
        return this.doWhileParsed(supplier, runnable);
    }

    private TaskStatusEnum printfStatus() {
        Supplier<TaskStatusEnum> supplier = () -> {
            String status = this.printf("Статус");
            return TaskStatusEnum.valueOf(status.toUpperCase());
        };
        Runnable runnable = () -> log.warn("Статус не найден. Попробуйте снова!");
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
