package com.example.tasklist.repository.impl;

import com.example.tasklist.config.DataSourceConfig;
import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DataSourceConfig dataSourceConfig;
    private Connection connection=dataSourceConfig.connection();
    private final String SQL_FIND_BY_ID = """
            SELECT id              as user_id,
                                         name           as user_name,
                                         username        as user_username,
                                         password        as user_password,                                         
                                        
                                  from users
                                  where id=?;
            """;
    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement statement=connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1,id);
            ResultSet rs=statement.executeQuery();
            if(){

            }
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Role role) {

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
