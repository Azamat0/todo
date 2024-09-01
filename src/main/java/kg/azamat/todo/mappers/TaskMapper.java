package kg.azamat.todo.mappers;


import kg.azamat.todo.dto.TaskCreateRequest;
import kg.azamat.todo.dto.TaskDto;
import kg.azamat.todo.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task getTask(TaskCreateRequest request) {
        Task task = new Task();
        task.setDescription(request.getDescription());
        return task;
    }


    public TaskDto getTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getTaskStatus().getDescription());
        return dto;
    }

}
