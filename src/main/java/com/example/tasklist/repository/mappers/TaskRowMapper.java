package com.example.tasklist.repository.mappers;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRowMapper {
    @SneakyThrows
    public static Task mapRow(ResultSet resultSet){
            return Task.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("title"))
                    .description(resultSet.getString("description"))
                    .status(Status.valueOf(resultSet.getString("status")))
                    .expirationDate(resultSet.getTimestamp("expiration_date").toLocalDateTime())
                    .build();

    }
    @SneakyThrows
    public static List<Task> mapRows(ResultSet resultSet){
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()){
            tasks.add(mapRow(resultSet));
        }
        return tasks;
    }
}
