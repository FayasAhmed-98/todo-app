package com.coveragex.todotask.service;

import com.coveragex.todotask.entity.Task;
import com.coveragex.todotask.exception.TaskNotFoundException;
import com.coveragex.todotask.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Creates a new task.
     * @param task Task entity to be saved
     * @return Saved Task
     */
    public Task createTask(Task task) {
        log.info("Creating a new task: {}", task);
        try {
            Task savedTask = taskRepository.save(task);
            log.info("Task created successfully: {}", savedTask);
            return savedTask;
        } catch (Exception e) {
            log.error("Error creating task: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create task", e);
        }
    }

    /**
     * Retrieves the 5 most recent uncompleted tasks.
     * @return List of recent tasks
     */
    public List<Task> getRecentTasks() {
        log.info("Fetching the 5 most recent uncompleted tasks");
        try {
            List<Task> tasks = taskRepository.findTop5ByCompletedFalseOrderByCreatedAtDesc();
            log.info("Retrieved {} tasks", tasks.size());
            return tasks;
        } catch (Exception e) {
            log.error("Error fetching recent tasks: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch recent tasks", e);
        }
    }

    /**
     * Marks a task as completed by ID.
     * @param id Task ID
     * @return Updated Task
     * @throws TaskNotFoundException if task is not found
     */
    public Task markTaskCompleted(Long id) {
        log.info("Marking task as completed: ID {}", id);
        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Task with ID {} not found", id);
                        return new TaskNotFoundException("Task not found with ID: " + id);
                    });

            task.setCompleted(true);
            Task updatedTask = taskRepository.save(task);
            log.info("Task marked as completed: {}", updatedTask);
            return updatedTask;
        } catch (TaskNotFoundException e) {
            throw e; // Custom exception is thrown directly
        } catch (Exception e) {
            log.error("Error marking task as completed: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to mark task as completed", e);
        }
    }
}
