package com.example.todo.DAO.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.todo.DAO.TodoDAO;
import com.example.todo.vo.Todo;

//SQLite implementation
public class TodoDAOImpl implements TodoDAO {
	private final String jdbcUrl;

	public TodoDAOImpl(String jdbcUrl) throws SQLException {
		this.jdbcUrl = Objects.requireNonNull(jdbcUrl, "/api/todos");
		initSchema();
	}

	private void initSchema() throws SQLException {
		String sql = """
				CREATE TABLE IF NOT EXISTS todos (
				    id INTEGER PRIMARY KEY AUTOINCREMENT,
				    title TEXT NOT NULL,
				    completed INTEGER NOT NULL DEFAULT 0
				)
				""";
		try (Connection conn = DriverManager.getConnection(jdbcUrl); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		}
	}

	@Override
	public Todo add(String title) throws SQLException {
		String insertSql = "INSERT INTO todos (title, completed) VALUES (?, 0)";
		try (Connection conn = DriverManager.getConnection(jdbcUrl);
				PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, title);
			ps.executeUpdate();
			try (ResultSet keys = ps.getGeneratedKeys()) {
				int id = keys.next() ? keys.getInt(1) : 0;
				return new Todo(id, title, false);
			}
		}
	}

	@Override
	public List<Todo> findAll() throws SQLException {
		String sql = "SELECT id, title, completed FROM todos ORDER BY id ASC";
		List<Todo> todos = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(jdbcUrl);
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				todos.add(new Todo(rs.getInt("id"), rs.getString("title"), rs.getInt("completed") == 1));
			}
		}
		return todos;
	}

	@Override
	public boolean markCompleted(int id, boolean completed) throws SQLException {
		String sql = "UPDATE todos SET completed = ? WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcUrl);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, completed ? 1 : 0);
			ps.setInt(2, id);
			return ps.executeUpdate() > 0;
		}
	}

	@Override
	public Optional<Todo> updateTodo(int id, String title, boolean completed) throws SQLException {
		String sql = "UPDATE todos SET title = ?, completed = ? WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcUrl);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, title);
			ps.setInt(2, completed ? 1 : 0);
			ps.setInt(3, id);

			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated > 0) {
				return Optional.of(new Todo(id, title, completed));
			}
			return Optional.empty();
		}
	};

	@Override
	public boolean deleteTodo(int id) throws SQLException {
		String sql = "delete from  todos where  WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcUrl);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		}
	}
}