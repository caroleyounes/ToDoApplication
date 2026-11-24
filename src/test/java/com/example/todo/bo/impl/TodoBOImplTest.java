package com.example.todo.bo.impl;

import com.example.todo.DAO.TodoDAO;
import com.example.todo.DAO.TodoRepositoryFactory;
import com.example.todo.vo.Todo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

//import static org.junit.jupiter.api.Assertions.*;
public class TodoBOImplTest {
	/*
	 * private TodoBOImpl todoBO;
	 * 
	 * @BeforeEach void setUp() throws SQLException { TodoDAO dao =
	 * TodoRepositoryFactory.createInMemorySqlite(); todoBO = new TodoBOImpl(dao); }
	 * 
	 * // --- Create ---
	 * 
	 * @Test void createTodo_validTitle_createsUncompletedTodo() throws SQLException
	 * { Todo created = todoBO.createTodo("  Write unit tests  ");
	 * 
	 * assertNotNull(created); assertTrue(created.getId() > 0);
	 * assertEquals("Write unit tests", created.getTitle());
	 * assertFalse(created.isCompleted()); }
	 * 
	 * @Test void createTodo_blankOrNullTitle_throwsIllegalArgumentException() {
	 * assertThrows(IllegalArgumentException.class, () -> todoBO.createTodo("   "));
	 * assertThrows(IllegalArgumentException.class, () -> todoBO.createTodo(null));
	 * }
	 * 
	 * // --- Update ---
	 * 
	 * @Test void updateTodo_existingId_updatesTitleAndReturnsTodo() throws
	 * SQLException { Todo created = todoBO.createTodo("Draft notes");
	 * 
	 * Optional<Todo> updated = todoBO.updateTodo(created.getId(), "Finalize notes",
	 * false);
	 * 
	 * assertTrue(updated.isPresent()); assertEquals(created.getId(),
	 * updated.get().getId()); assertEquals("Finalize notes",
	 * updated.get().getTitle()); assertFalse(updated.get().isCompleted()); }
	 * 
	 * @Test void updateTodo_nonExistentId_returnsEmpty() throws SQLException {
	 * Optional<Todo> updated = todoBO.updateTodo(9999, "Does not exist", false);
	 * 
	 * assertTrue(updated.isEmpty()); }
	 * 
	 * // --- Delete ---
	 * 
	 * @Test void deleteTodo_existingId_removesTodoAndReturnsTrue() throws
	 * SQLException { Todo created = todoBO.createTodo("Temp task");
	 * 
	 * boolean deleted = todoBO.deleteTodo(created.getId());
	 * 
	 * assertTrue(deleted); assertTrue(todoBO.listTodos().stream().noneMatch(t ->
	 * t.getId() == created.getId())); }
	 * 
	 * @Test void deleteTodo_nonExistentId_returnsFalse() throws SQLException {
	 * boolean deleted = todoBO.deleteTodo(9999);
	 * 
	 * assertFalse(deleted); }
	 * 
	 * // --- Mark as Completed ---
	 * 
	 * @Test void markCompleted_existingId_setsCompletedTrue() throws SQLException {
	 * Todo created = todoBO.createTodo("Submit assignment");
	 * 
	 * Optional<Todo> result = todoBO.updateTodo(created.getId(),
	 * created.getTitle(), true);
	 * 
	 * assertTrue(result.isPresent()); assertTrue(result.get().isCompleted()); }
	 * 
	 * @Test void markCompleted_nonExistentId_returnsEmpty() throws SQLException {
	 * Optional<Todo> result = todoBO.updateTodo(9999, "Missing", true);
	 * 
	 * assertTrue(result.isEmpty()); }
	 */
}
