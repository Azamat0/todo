package kg.azamat.todo.service;


import kg.azamat.todo.dto.TaskCreateRequest;
import kg.azamat.todo.dto.TaskDto;
import kg.azamat.todo.dto.TaskFilter;
import kg.azamat.todo.dto.TaskUpdateRequest;
import kg.azamat.todo.mappers.TaskMapper;
import kg.azamat.todo.models.Task;
import kg.azamat.todo.models.TaskStatus;
import kg.azamat.todo.repositories.TaskRepository;
import kg.azamat.todo.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask() {
        TaskCreateRequest request = new TaskCreateRequest();
        Task task = new Task();
        TaskDto taskDto = new TaskDto();

        when(taskMapper.getTask(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.getTaskDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(request);

        assertEquals(taskDto, result);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setId(1L);
        Task task = new Task();
        TaskDto taskDto = new TaskDto();

        when(taskRepository.findById(request.getId())).thenReturn(Optional.of(task));
        when(taskMapper.getTaskDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.updateTask(request);

        assertEquals(taskDto, result);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTaskThrowsEntityNotFoundException() {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setId(1L);

        when(taskRepository.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(request));
    }

    @Test
    void updateTaskStatus() {
        Long taskId = 1L;
        Task task = new Task();
        task.setTaskStatus(TaskStatus.NOT_COMPLETED);
        TaskDto taskDto = new TaskDto();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.getTaskDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.updateTaskStatus(taskId);

        assertEquals(taskDto, result);
        verify(taskRepository).save(task);
        assertEquals(TaskStatus.COMPLETED, task.getTaskStatus());
    }

    @Test
    void updateTaskStatusThrowsEntityNotFoundException() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTaskStatus(taskId));
    }

    @Test
    void getById() {
        Long taskId = 1L;
        Task task = new Task();
        TaskDto taskDto = new TaskDto();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.getTaskDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.getById(taskId);

        assertEquals(taskDto, result);
    }

    @Test
    void getByIdThrowsEntityNotFoundException() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getById(taskId));
    }

    @Test
    void getAllTasks() {
        TaskFilter filter = new TaskFilter();
        Pageable pageable = PageRequest.of(0, 10);
        Task task = new Task();
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));
        TaskDto taskDto = new TaskDto();

        when(taskRepository.getTasksByFilter(filter, pageable)).thenReturn(taskPage);
        when(taskMapper.getTaskDto(task)).thenReturn(taskDto);

        Page<TaskDto> result = taskService.getAllTasks(filter, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(taskDto, result.getContent().get(0));
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }
}
