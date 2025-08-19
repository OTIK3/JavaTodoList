package project_2.exceptions;

public class TaskNotFoundException extends RuntimeException {

    private final static String NOT_FOUND_MESSAGE = "Задача по данному идентификатору (:%d) не найдена!";

    public TaskNotFoundException(Integer id) {
        super(NOT_FOUND_MESSAGE.formatted(id));
    }

}
