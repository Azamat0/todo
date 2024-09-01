package kg.azamat.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateRequest {
    Long id;
    String description;
}
