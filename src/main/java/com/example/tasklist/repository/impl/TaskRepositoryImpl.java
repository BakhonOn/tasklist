package com.example.tasklist.repository.impl;

import com.example.tasklist.config.DataSourceConfig;
import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSourceConfig dataSourceConfig;
    private  Connection connection=dataSourceConfig.connection();
    private final JdbcTemplate jdbcTemplate;
    private final String SQL_FIND_BY_ID = """
            SELECT id              as task_id,
                                         title           as task_title,
                                         description     as task_description,
                                         status          as task_status,
                                         expiration_date as task_expiration_date
                                  from tasks
                                  where id=?;
            """;

    private final String SQL_FIND_ALL_BY_USER_ID = """
            SELECT  as task_id,
                                                         title           as task_title,
                                                         description     as task_description,
                                                         status          as task_status,
                                                         expiration_date as task_expiration_date
                                                  from tasks
                                                      join public.users_tasks ut on tasks.id = ut.task_id
                                                  where ut.user_id=?;
            """;
    private final String SQL_ASSIGN_TO_USER_BY_ID = """
            insert into public.users_tasks (user_id, task_id) values (?, ?);
            """;
    private final String SQL_UPDATE = """
            update tasks
            set title = ?,
                description = ?,
                status = ?,
                expiration_date = ?
            where id = ?;
            """;
    private final String SQL_CREATE = """
            insert into tasks (title, description, status, expiration_date) values (?, ?, ?, ?);
            """;
    private final String SQL_DELETE = """
            delete from tasks where id = ?;
            """;

    @Override
    public Optional<Task> findById(Long id) {
        try{
            PreparedStatement statement=connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1,id);
            try (ResultSet rs=statement.executeQuery()){
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try{
            PreparedStatement statement=connection.prepareStatement(SQL_FIND_ALL_BY_USER_ID);
            statement.setLong(1,userId);
            try (ResultSet rs=statement.executeQuery()){
                return TaskRowMapper.mapRows(rs);
            }
        }catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            PreparedStatement statement=connection.prepareStatement(SQL_ASSIGN_TO_USER_BY_ID);
            statement.setLong(1,userId);
            statement.setLong(2,taskId);
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void update(Task task) {
        try {
            PreparedStatement statement=connection.prepareStatement(SQL_UPDATE);
            statement.setString(1,task.getTitle()==null?"":task.getTitle());
            statement.setString(2,task.getDescription()==null?"":task.getDescription());
            statement.setString(3,task.getStatus().toString()==null?"":task.getStatus().toString()
            statement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()==null? LocalDateTime.now():task.getExpirationDate())));
            statement.setLong(5,task.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void create(Task task) {
        try {

            PreparedStatement statement=connection.prepareStatement(SQL_CREATE);
            statement.setString(1,task.getTitle()==null?"":task.getTitle());
            statement.setString(2,task.getDescription()==null?"":task.getDescription());
            statement.setString(3,task.getStatus().toString()==null?"":task.getStatus().toString()
            statement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()==null? LocalDateTime.now():task.getExpirationDate())));
            statement.executeUpdate();
            try (ResultSet rs=statement.getGeneratedKeys()){
                if (rs.next()){
                    task.setId(rs.getLong("id"));
                }
            }
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement statement=connection.prepareStatement(SQL_DELETE);
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }
}
