package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.user.UserDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import com.example.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/b1/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TaskService taskService;
    private TaskMapper taskMapper;
    private UserMapper userMapper;

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto){
        User user=userMapper.toEntity(userDto);
        return userMapper.toDto(user);
    }
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return userMapper.toDto(userService.getById(id));
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }
    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTasks(@PathVariable Long id){
        return taskMapper.toDto(taskService.getAllByUserId(id));
    }
    @PostMapping("{id}/tasks")
    public TaskDto createTask(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody TaskDto taskDto){
        Task task=taskMapper.toEntity(taskDto);
        return taskMapper.toDto(taskService.create(task,id));
    }

}
