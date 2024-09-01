package kg.azamat.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.azamat.todo.models.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private String description;
    private String status;

}
