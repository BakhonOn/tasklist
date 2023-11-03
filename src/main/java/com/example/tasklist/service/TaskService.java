package com.example.tasklist.service;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task getById(Long id);
    List<Task> getAllBehaviorId(Long id);
    Task update(Task task);
    Task create(Task task);
    void delete(Long id);

}
