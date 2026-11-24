package com.example.todo.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.example.todo.vo.Todo;

// Data access abstraction
public interface TodoDAO {
	Todo add(String title) throws SQLException;

	List<Todo> findAll() throws SQLException;

	boolean markCompleted(int id, boolean completed) throws SQLException;

	Optional<Todo> updateTodo(int id, String title, boolean completed) throws SQLException;

	boolean deleteTodo(int id) throws SQLException;
}
