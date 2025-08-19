package project_2.mapper;

import project_2.model.TaskEntity;
import project_2.model.TaskRequest;

import java.util.List;

public class TaskMapper {

    public List<TaskRequest> toListRequest(List<TaskEntity> tasks) {
        return tasks.stream()
                .map(TaskRequest::new)
                .toList();
    }

    public TaskEntity toEntity(TaskRequest taskRequest) {
        return TaskEntity.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .timeLimit(taskRequest.getTimeLimit())
                .build();
    }

    public TaskEntity toEntity(TaskRequest taskRequest, TaskEntity taskEntity) {
        TaskEntity task = this.toEntity(taskRequest);
        task.setStatus(taskEntity.getStatus());
        return task;
    }

}
