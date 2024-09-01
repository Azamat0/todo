package kg.azamat.todo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.azamat.todo.dto.TaskCreateRequest;
import kg.azamat.todo.dto.TaskDto;
import kg.azamat.todo.dto.TaskFilter;
import kg.azamat.todo.dto.TaskUpdateRequest;
import kg.azamat.todo.models.TaskStatus;
import kg.azamat.todo.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    @Operation(summary = "Создание новой задачи.")
    public ResponseEntity<TaskDto> create(@RequestBody TaskCreateRequest taskRequest) {
        return new ResponseEntity<>(taskService.createTask(taskRequest), HttpStatus.CREATED);
    }

    @PutMapping("/tasks")
    @Operation(summary = "Редактирование задачи по идентификатору.")
    public ResponseEntity<TaskDto> update(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        return new ResponseEntity<>(taskService.updateTask(taskUpdateRequest), HttpStatus.OK);
    }

    @PutMapping("/tasks/status/{id}")
    @Operation(summary = "Переключение статуса по идентификатору.")
    public ResponseEntity<TaskDto> updateStatus(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.updateTaskStatus(id), HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    @Operation(summary = "Получение информации о задаче по идентификатору.")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(taskService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    @Operation(summary = "Получение списка задач с поиском по ключевому слову, с фильтрацией статуса и пагинацией.")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "") String description,
                                    @RequestParam(required = false) TaskStatus status,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size){
        TaskFilter filter = new TaskFilter(description, status);
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(taskService.getAllTasks(filter, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/tasks")
    @Operation(summary = "Удаление задачи по идентификатору.")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>("The task was successfully deleted", HttpStatus.OK);
    }

}
