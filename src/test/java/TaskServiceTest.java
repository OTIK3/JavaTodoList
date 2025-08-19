import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import project_2.exceptions.TaskNotFoundException;
import project_2.mapper.TaskMapper;
import project_2.model.TaskRequest;
import project_2.repository.TaskRepository;
import project_2.service.TaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private static TaskService taskService;
    private static List<TaskRequest> taskRequestList;

    @BeforeAll
    public static void init() {
        TaskMapper taskMapper = new TaskMapper();
        TaskRepository taskRepository = new TaskRepository();
        taskService = new TaskService(taskRepository, taskMapper);
        taskRequestList = getRandomTasks();
    }

    @Test
    public void createTask() {
        taskRequestList.forEach(taskService::addTask);
        int sizeAfterAdd = taskService.getAll().size();
        assertEquals(sizeAfterAdd, taskRequestList.size());
    }

    public void changeTask() {

    }

    @Test
    public void deleteTask() {
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(0));
        taskService.addTask(this.getRandomTask());
        assertDoesNotThrow(() -> taskService.deleteTask(0));
    }

    private TaskRequest getRandomTask() {
        String name = "test";
        String description = "test";
        LocalDate timeLimit = LocalDate.now();
        return new TaskRequest(name, description, timeLimit);
    }

    private static List<TaskRequest> getRandomTasks() {
        List<TaskRequest> taskRequests = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String name = "test%s".formatted(i + 1);
            String description = "test%s".formatted(i - 1);
            String day = (i < 9 ? "0" : "") + (i + 1);
            LocalDate timeLimit = LocalDate.parse("2025-08-%s".formatted(day));
            taskRequests.add(new TaskRequest(name, description, timeLimit));
        }
        return taskRequests;
    }

}
