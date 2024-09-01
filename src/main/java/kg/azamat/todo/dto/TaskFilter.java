package kg.azamat.todo.dto;

import kg.azamat.todo.models.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskFilter {

    private String description;
    private TaskStatus status;

}
