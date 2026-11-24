package com.example.todo.action;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.bo.impl.TodoBOImpl;
import com.example.todo.vo.Todo;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	private final TodoBOImpl todobo;

	public TodoController(TodoBOImpl todobo) {
		this.todobo = todobo;
	}

	public record TodoRequest(String title, Boolean completed) {
	}

	@PostMapping
	public ResponseEntity<Todo> create(@RequestBody TodoRequest req) {
		if (req.title() == null || req.title().isBlank()) {
			return ResponseEntity.badRequest().build();
		}
		try {
			Todo created = todobo.createTodo(req.title().trim());
			return ResponseEntity.status(HttpStatus.CREATED).body(created);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	public ResponseEntity<List<Todo>> findAll() {
		try {
			return ResponseEntity.ok(todobo.listTodos());
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Todo> update(@PathVariable int id, @RequestBody TodoRequest req) {
		try {
			return todobo.updateTodo(id, req.title(), req.completed()).map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		try {
			boolean deleted = todobo.deleteTodo(id);
			if (!deleted) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.noContent().build();
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
