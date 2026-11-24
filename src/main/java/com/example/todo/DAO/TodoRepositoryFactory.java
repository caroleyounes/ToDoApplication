package com.example.todo.DAO;

import java.sql.SQLException;
import com.example.todo.DAO.impl.*;

//Factory for creating configured TodoRepository instances
public class TodoRepositoryFactory {


 public static TodoDAOImpl createSqlite(String dbPath) throws SQLException {
     return new TodoDAOImpl("jdbc:sqlite:" + dbPath);
 }

 static TodoDAOImpl createInMemorySqlite() throws SQLException {
     return new TodoDAOImpl("jdbc:sqlite::memory:");
 }
}
