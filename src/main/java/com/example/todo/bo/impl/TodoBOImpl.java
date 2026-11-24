package com.example.todo.bo.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.todo.vo.Todo;
import com.example.todo.DAO.TodoDAO;
import com.example.todo.bo.TodoBO;

//Business logic receives its dependency rather than creating it
@Service
public class TodoBOImpl implements TodoBO {
	private final TodoDAO repository;

	public TodoBOImpl(TodoDAO repository) {
		this.repository = Objects.requireNonNull(repository, "/api/todos");
	}

	public Todo createTodo(String title) throws SQLException {
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("Todo title cannot be blank");
		}
		return repository.add(title.trim());
	}

	public List<Todo> listTodos() throws SQLException {
		return repository.findAll();
	}

	public boolean completeTodo(int id) throws SQLException {
		return repository.markCompleted(id, true);
	}

	public Optional<Todo> updateTodo(int id, String title, boolean completed) throws SQLException {
		return repository.updateTodo(id, title, true);
	};

	public boolean deleteTodo(int id) throws SQLException {
		return repository.deleteTodo(id);
	}

}