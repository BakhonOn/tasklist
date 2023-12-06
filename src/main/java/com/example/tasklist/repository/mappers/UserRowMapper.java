package com.example.tasklist.repository.mappers;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.tasklist.repository.mappers.TaskRowMapper.mapRow;

public class UserRowMapper {
    @SneakyThrows
    public static User mapRow(ResultSet resultSet){
        return User.builder()
                .id(resultSet.getLong("user_id"))
                .name(resultSet.getString("user_name"))
                .username(resultSet.getString("user_username"))
                .password(resultSet.getString("user_password"))
                .roles(Set.of(Role.valueOf(resultSet.getString("user_role"))))
                .tasks(TaskRowMapper.mapRows(resultSet))
                .build();

    }
    @SneakyThrows
    public static List<User> mapRows(ResultSet resultSet){
        List<User> users = new ArrayList<>();
        while (resultSet.next()){
            users.add(mapRow(resultSet));
        }
        return users;
    }
}
