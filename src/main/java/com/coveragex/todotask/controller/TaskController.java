package com.coveragex.todotask.controller;

import com.coveragex.todotask.entity.Task;
import com.coveragex.todotask.exception.TaskNotFoundException;
import com.coveragex.todotask.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("Request to create a new task: {}", task);
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getRecentTasks() {
        log.info("Request to fetch recent tasks");
        List<Task> tasks = taskService.getRecentTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskCompleted(@PathVariable Long id) {
        log.info("Request to mark task as completed: ID {}", id);
        try {
            Task updatedTask = taskService.markTaskCompleted(id);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            log.error("Task not found: ID {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
