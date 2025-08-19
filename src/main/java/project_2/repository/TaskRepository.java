package project_2.repository;

import project_2.exceptions.TaskNotFoundException;
import project_2.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final static List<TaskEntity> TASKS = new ArrayList<>();

    public Integer save(TaskEntity task) {
        TASKS.add(task);
        return TASKS.size();
    }

    public void changeById(TaskEntity task, Integer index) {
        this.validIndex(index);
        TASKS.set(index, task);
    }

    public void deleteById(Integer index) {
        this.validIndex(index);
        TASKS.remove(TASKS.get(index));
    }

    public TaskEntity findById(Integer index) {
        this.validIndex(index);
        return new TaskEntity(TASKS.get(index));
    }

    public List<TaskEntity> findAll() {
        return new ArrayList<>(TASKS);
    }

    public void clear() {
        TASKS.clear();
    }

    private void validIndex(Integer index) {
        if (index < 0 || TASKS.size() <= index) {
            throw new TaskNotFoundException(index);
        }
    }



}
