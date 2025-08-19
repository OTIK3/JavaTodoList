package project_2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project_2.enums.TaskStatusEnum;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class TaskRequest {

    private final String name;
    private final String description;
    private final LocalDate timeLimit;
    private TaskStatusEnum status;

    public TaskRequest(TaskEntity entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.timeLimit = entity.getTimeLimit();
        this.status = entity.getStatus();
    }

    @Override
    public String toString() {
        return "[Название: %s | Описание: %s | Срок: %s | Статус: %s]".formatted(name, description, timeLimit, status);
    }

}
