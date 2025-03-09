package com.coveragex.todotask.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean completed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors, Getters, and Setters
    public Task() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }
}