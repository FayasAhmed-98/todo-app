package com.coveragex.todotask.controller;

import com.coveragex.todotask.entity.Task;
import com.coveragex.todotask.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Use RANDOM_PORT for testing
public class TaskControllerIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");

        ResponseEntity<Task> response = restTemplate.postForEntity("/api/tasks", task, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("New Task");
    }

    @Test
    public void testGetRecentTasks() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        taskRepository.save(task2);

        ResponseEntity<Task[]> response = restTemplate.getForEntity("/api/tasks", Task[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    public void testMarkTaskCompleted() {
        Task task = new Task();
        task.setTitle("Task to Complete");
        task = taskRepository.save(task);

        // Assuming you are calling a PUT request to mark the task as completed
        ResponseEntity<Task> response = restTemplate.exchange(
                "/api/tasks/{id}/complete", HttpMethod.PUT, null, Task.class, task.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isCompleted()).isTrue();
    }
}