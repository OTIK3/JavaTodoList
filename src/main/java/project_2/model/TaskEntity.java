package project_2.model;

import lombok.*;
import project_2.enums.TaskStatusEnum;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TaskEntity {

    private String name;
    private String description;
    private LocalDate timeLimit;
    @Builder.Default
    private TaskStatusEnum status = TaskStatusEnum.TODO;

    public TaskEntity(TaskEntity task) {
        this.name = task.getName();
        this.description = task.getDescription();
        this.timeLimit = task.getTimeLimit();
        this.status = task.getStatus();
    }

}
