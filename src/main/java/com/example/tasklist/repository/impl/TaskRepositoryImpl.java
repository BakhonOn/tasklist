package com.example.tasklist.repository.impl;

import com.example.tasklist.config.DataSourceConfig;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSourceConfig dataSourceConfig;
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

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(Long id) {

    }
}
