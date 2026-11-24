package com.example.todo.bo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.example.todo.vo.Todo;

public interface TodoBO {

	Todo createTodo(String title) throws SQLException;

	List<Todo> listTodos() throws SQLException;

	boolean completeTodo(int id) throws SQLException;

	Optional<Todo> updateTodo(int id, String title, boolean completed) throws SQLException;

	boolean deleteTodo(int id) throws SQLException;
}
