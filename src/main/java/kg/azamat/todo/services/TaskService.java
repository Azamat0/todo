package kg.azamat.todo.services;

import jakarta.persistence.EntityNotFoundException;
import kg.azamat.todo.dto.TaskCreateRequest;
import kg.azamat.todo.dto.TaskDto;
import kg.azamat.todo.dto.TaskFilter;
import kg.azamat.todo.dto.TaskUpdateRequest;
import kg.azamat.todo.mappers.TaskMapper;
import kg.azamat.todo.models.TaskStatus;
import kg.azamat.todo.models.Task;
import kg.azamat.todo.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto createTask(TaskCreateRequest request){
        log.info("...Creating new Task..");
        Task task = taskMapper.getTask(request);
        taskRepository.save(task);
        return taskMapper.getTaskDto(task);
    }

    @Transactional
    public TaskDto updateTask(TaskUpdateRequest request){
        log.info("...Updating Task...");
        Task task = taskRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task with such ID is not found"));
        task.setDescription(request.getDescription());
        taskRepository.save(task);
        return taskMapper.getTaskDto(task);
    }

    @Transactional
    public TaskDto updateTaskStatus(Long id){
        log.info("...Updating status of Task...");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with such ID is not found"));
        TaskStatus newStatus = getNewStatus(task.getTaskStatus());
        task.setTaskStatus(newStatus);
        taskRepository.save(task);
        return taskMapper.getTaskDto(task);
    }

    public TaskDto getById(Long id){
        log.info("...Getting Task By ID...");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with such ID is not found"));
        return taskMapper.getTaskDto(task);
    }

    public Page<TaskDto> getAllTasks(TaskFilter filter, Pageable pageable){
        log.info("...Getting list of Tasks...");
        Page<Task> taskPage = taskRepository.getTasksByFilter(filter, pageable);
        return taskPage.map(taskMapper::getTaskDto);
    }

    @Transactional
    public void deleteTask(Long id){
        log.info("...Deleting Task...");
        taskRepository.deleteById(id);
    }

    //----------------
    // Private Methods
    //----------------

    private TaskStatus getNewStatus(TaskStatus status) {
        if(status.equals(TaskStatus.COMPLETED)) {
            return TaskStatus.NOT_COMPLETED;
        }
        return TaskStatus.COMPLETED;
    }

}
