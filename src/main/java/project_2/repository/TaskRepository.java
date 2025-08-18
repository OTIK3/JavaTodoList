package project_2.repository;

import project_2.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final static List<Task> TASKS = new ArrayList<>();

    public Integer save(Task task) {
        TASKS.add(task);
        return TASKS.size();
    }

    //TODO логика изменения задачи по индексу
    public void changeById(Task task, Integer index) {
        this.validIndex(index);
        TASKS.set(index, task);
    }

    //TODO логика по удалению задачи по индексу
    public void deleteById(Integer index) {
        this.validIndex(index);
        TASKS.remove(TASKS.get(index));
    }

    public Task findById(Integer index) {
        this.validIndex(index);
        return new Task(TASKS.get(index));
    }

    public List<Task> findAll() {
        return new ArrayList<>(TASKS);
    }

    private void validIndex(Integer index) {
        if (TASKS.size() > index) {
            //TODO вызов исключения
        }
    }

}
