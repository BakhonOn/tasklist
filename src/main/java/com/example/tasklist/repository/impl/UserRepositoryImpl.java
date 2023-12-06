package com.example.tasklist.repository.impl;

import com.example.tasklist.config.DataSourceConfig;
import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.example.tasklist.repository.mappers.UserRowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String SQL_FIND_BY_ID = """
            SELECT id              as user_id,
                                         name           as user_name,
                                         username        as user_username,
                                         password        as user_password,                                         
                                        
                                  from users
                                  where id=?;
            """;
    private final String SQL_FIND_BY_USERNAME = """ 
            SELECT id              as user_id,
                                         name           as user_name,
                                         username        as user_username,
                                         password        as user_password,                                         
                                        
                                  from users
                                  where username=?;
            """;
    private final String SQL_UPDATE = """
            update users
            set name = ?,
                username = ?,
                password = ?
            where id = ?;
            """;
    private final String SQL_CREATE = """
            insert into users (name, username, password) values (?, ?, ?);
            """;
    private final String SQL_DELETE = """
            delete from users where id = ?;
            """;
    private final String SQL_INSERT_USER_ROLE = """
            insert into public.users_roles (user_id, role) values (?, ?);
            """;
    private final String SQL_IS_TASK_OWNER = """
            select exists(select * from users_tasks where user_id=? and task_id=?);
            """;

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement=connection.prepareStatement(SQL_FIND_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1,id);
            try(ResultSet rs=statement.executeQuery()){
                return Optional.of(UserRowMapper.mapRow(rs));
            }

        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try{
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement=connection.prepareStatement(SQL_FIND_BY_USERNAME);
            statement.setString(1,username);
            try (ResultSet rs=statement.executeQuery()){
                return Optional.of(UserRowMapper.mapRow(rs));
            }
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try {
            if (!findById(user.getId()).isPresent()) {
                throw new ResourceMappingException("User with id " + user.getId() + " not found");
            }
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement=connection.prepareStatement(SQL_UPDATE);
            statement.setString(1,user.getName()==null?"":user.getName());
            statement.setString(2,user.getUsername()==null?"":user.getUsername());
            statement.setString(3,user.getPassword()==null?"":user.getPassword());
            statement.setLong(4,user.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void create(User user) {
        try{
            if(findByUsername(user.getUsername()).isPresent()){
                throw new ResourceMappingException("User with username "+user.getUsername()+" already exists");
            }
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement=connection.prepareStatement(SQL_CREATE);
            statement.setString(1,user.getName()==null?"":user.getName());
            statement.setString(2,user.getUsername()==null?"":user.getUsername());
            statement.setString(3,user.getPassword()==null?"":user.getPassword());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            if(!findById(userId).isPresent()){
                throw new ResourceMappingException("User with id "+userId+" not found");
            }
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement=connection.prepareStatement(SQL_INSERT_USER_ROLE);
            statement.setLong(1,userId);
            statement.setLong(2,Role.valueOf(role.toString()).ordinal());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            if (!findById(userId).isPresent()) {
                throw new ResourceMappingException("User with id " + userId + " not found");
            }
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement = connection.prepareStatement(SQL_IS_TASK_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (!findById(id).isPresent()) {
                throw new ResourceMappingException("User with id " + id + " not found");
            }
            Connection connection=dataSourceConfig.connection();
            PreparedStatement statement=connection.prepareStatement(SQL_DELETE);
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new ResourceMappingException(e.getMessage());
        }
    }
}
