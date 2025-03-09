package com.coveragex.todotask.repository;

import com.coveragex.todotask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTop5ByCompletedFalseOrderByCreatedAtDesc();
}