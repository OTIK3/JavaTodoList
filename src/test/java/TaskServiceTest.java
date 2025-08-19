import org.junit.jupiter.api.Test;
import project_2.enums.TaskStatusEnum;
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

    private static final TaskService taskService;
    private List<TaskRequest> taskRequestList;

    static {
        TaskMapper taskMapper = new TaskMapper();
        TaskRepository taskRepository = new TaskRepository();
        taskService = new TaskService(taskRepository, taskMapper);
    }

    public void init() {
        new TaskRepository().clear();
        taskRequestList = getRandomTasks();
    }

    @Test
    public void createTask() {
        this.init();
        taskRequestList.forEach(taskService::addTask);
        int sizeAfterAdd = taskService.getAll().size();
        assertEquals(sizeAfterAdd, taskRequestList.size());
    }

    @Test
    public void changeTask() {
        assertThrows(TaskNotFoundException.class, () -> taskService.changeByStatus(TaskStatusEnum.DONE, 0));

        taskService.addTask(this.getRandomTask());
        TaskRequest taskRequestBefore = taskService.getAll().get(0);
        assertDoesNotThrow(() -> taskService.changeByStatus(TaskStatusEnum.DONE, 0));

        TaskRequest taskRequestAfter = taskService.getAll().get(0);
        assertNotEquals(taskRequestBefore.getStatus(), taskRequestAfter.getStatus());

        TaskRequest randomTask = this.getRandomTask();
        taskService.changeByTaskRequest(randomTask, 0);
        TaskRequest taskRequest = taskService.getAll().get(0);
        assertEquals(randomTask.getName(), taskRequest.getName());
        assertEquals(randomTask.getDescription(), taskRequest.getDescription());
        assertEquals(randomTask.getTimeLimit(), taskRequest.getTimeLimit());
    }

    @Test
    public void deleteTask() {
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(-1));
        taskService.addTask(this.getRandomTask());
        assertDoesNotThrow(() -> taskService.deleteTask(0));
    }

    @Test
    public void filterAndSorted() {
        this.init();
        taskRequestList.forEach(taskService::addTask);
        TaskStatusEnum inProgress = TaskStatusEnum.DONE;
        taskService.changeByStatus(inProgress, 0);
        taskService.changeByStatus(inProgress, 1);
        taskService.changeByStatus(inProgress, 2);
        int size = taskService.getAllFilterByStatus(inProgress).size();
        assertEquals(3, size);

        List<TaskRequest> sortedTaskRequestsByStatus = taskService.getAllSortedByStatus()
                .stream()
                .limit(3)
                .toList();
        assertFalse(sortedTaskRequestsByStatus.stream().allMatch(task -> task.getStatus().equals(inProgress)));

        List<LocalDate> initSortedTimeLimitList = taskRequestList.stream()
                .map(TaskRequest::getTimeLimit)
                .sorted()
                .toList();
        List<LocalDate> sortedTimeLimitList = taskService.getAllSortedByTimeLimit().stream()
                .map(TaskRequest::getTimeLimit)
                .toList();
        assertIterableEquals(initSortedTimeLimitList, sortedTimeLimitList);
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
