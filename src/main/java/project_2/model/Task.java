package project_2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project_2.enums.StatusTaskEnum;

import java.io.Serializable;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class Task implements Comparable<Task> {

    public Task(Task task) {
        this.name = task.name;
        this.description = task.description;
        this.timeLimit = task.timeLimit;
        this.status = task.status;
    }

    private final String name;
    private final String description;
    private final LocalDate timeLimit;
    private final StatusTaskEnum status;

    @Override
    public int compareTo(Task o) {
        return timeLimit.compareTo(o.timeLimit);
    }
}
