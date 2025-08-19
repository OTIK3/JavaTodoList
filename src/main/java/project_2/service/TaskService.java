package project_2.service;

import lombok.RequiredArgsConstructor;
import project_2.enums.TaskStatusEnum;
import project_2.mapper.TaskMapper;
import project_2.model.TaskEntity;
import project_2.model.TaskRequest;
import project_2.repository.TaskRepository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Integer addTask(TaskRequest taskRequest) {
        TaskEntity task = taskMapper.toEntity(taskRequest);
        return taskRepository.save(task);
    }

    public List<TaskRequest> getAll() {
        List<TaskEntity> tasks = taskRepository.findAll();
        return taskMapper.toListRequest(tasks);
    }

    public List<TaskRequest> getAllFilterByStatus(TaskStatusEnum status) {
        List<TaskEntity> tasks = taskRepository.findAll().stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
        return taskMapper.toListRequest(tasks);
    }

    public List<TaskRequest> getAllSortedByTimeLimit() {
        return this.getAllSortedByComparator(taskRepository.findAll(), TaskEntity::getTimeLimit);
    }

    public List<TaskRequest> getAllSortedByStatus() {
        return this.getAllSortedByComparator(taskRepository.findAll(), TaskEntity::getStatus);
    }

    public void changeByTaskRequest(TaskRequest taskRequest, Integer id) {
        TaskEntity taskEntity = taskRepository.findById(id);
        taskEntity = taskMapper.toEntity(taskRequest, taskEntity);
        taskRepository.changeById(taskEntity, id);
    }

    public void changeByStatus(TaskStatusEnum status, Integer id) {
        TaskEntity taskEntity = taskRepository.findById(id);
        taskEntity.setStatus(status);
        taskRepository.changeById(taskEntity, id);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    //TODO в идеале вынести в отдельную utility библиотеку
    private <U extends Comparable<? super U>> List<TaskRequest> getAllSortedByComparator(List<TaskEntity> tasks,
                                                                                        Function<TaskEntity, ? extends U> function) {
        List<TaskEntity> sortedTasks = tasks.stream()
                .sorted(Comparator.comparing(function))
                .toList();
        return taskMapper.toListRequest(sortedTasks);
    }

}
