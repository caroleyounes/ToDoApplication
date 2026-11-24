package com.example.todo.action.config;



import com.example.todo.DAO.TodoDAO;
import com.example.todo.DAO.TodoRepositoryFactory;
import com.example.todo.bo.impl.TodoBOImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class AppConfig {

    @Bean
    public TodoBOImpl todoBO() throws SQLException {
        TodoDAO dao = TodoRepositoryFactory.createSqlite("todo.db");
        return new TodoBOImpl(dao);
    }
}
