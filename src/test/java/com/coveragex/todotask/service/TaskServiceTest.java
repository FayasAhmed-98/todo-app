package com.coveragex.todotask.service;

import com.coveragex.todotask.entity.Task;
import com.coveragex.todotask.exception.TaskNotFoundException;
import com.coveragex.todotask.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testGetRecentTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findTop5ByCompletedFalseOrderByCreatedAtDesc()).thenReturn(tasks);

        List<Task> result = taskService.getRecentTasks();
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findTop5ByCompletedFalseOrderByCreatedAtDesc();
    }

    @Test
    public void testMarkTaskCompleted() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setCompleted(false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.markTaskCompleted(1L);
        assertTrue(result.isCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testMarkTaskCompleted_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.markTaskCompleted(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}
