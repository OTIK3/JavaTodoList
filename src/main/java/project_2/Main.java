package project_2;

import project_2.aop.TaskControllerHandler;
import project_2.controller.TaskController;
import project_2.controller.TaskControllerImpl;
import project_2.mapper.TaskMapper;
import project_2.repository.TaskRepository;
import project_2.service.TaskService;

import java.util.Scanner;

public class Main {

    private static final TaskControllerImpl TASK_CONTROLLER_IMPL;

    static {
        TaskRepository taskRepository = new TaskRepository();
        TaskMapper taskMapper = new TaskMapper();
        TaskService taskService = new TaskService(taskRepository, taskMapper);
        TASK_CONTROLLER_IMPL = new TaskControllerImpl(taskService);
    }

    public static void main(String[] args) {
        TaskController loggingTaskController = TaskControllerHandler.createProxy(TASK_CONTROLLER_IMPL, TaskController.class);
        while (true) {
            System.out.print("\nВведите команду -> ");
            String command = new Scanner(System.in).nextLine();
            command(command, loggingTaskController);
        }
    }

    public static void command(String nameCommand, TaskController taskController) {
        switch (nameCommand) {
            case "add" -> taskController.add();
            case "list" -> taskController.list();
            case "edit" -> taskController.edit();
            case "delete" -> taskController.delete();
            case "filter" -> taskController.filter();
            case "sort" -> taskController.sort();
            case "exit" -> taskController.exit();
            default -> System.out.printf("Команда %s не найдена\n", nameCommand);
        }
    }
}