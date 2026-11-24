# ToDoApplication
Simple To Do Application for the CSO4 Position


# The repository  include:
 * Complete source code
 * README with setup instructions
 * Architecture overview
 * Testing instructions
 * Design pattern explanation
 * Security considerations

 # Complete source code
 is under the todoApplication

 # Readme
 This is the read me file 

 # Architecture overview
 The To-Do application uses a layered Spring Boot monolith backed by a file-based SQLite database, packaged for local containerized execution via Docker and Docker Compose.

    1. Application Layers (Spring Boot)
    Web / Controller Layer (TodoController): Exposes HTTP endpoints (such as /todos), validates incoming request payloads, and maps domain responses back to HTTP responses.
    
    Business Logic Layer (TodoBOImpl): Encapsulates core task rules, validation logic, and orchestration between the web tier and persistence tier.
    
    Data Access Layer (Spring Data JPA): Manages entity mapping and CRUD operations against the relational schema without boilerplate SQL.
    
    Testing Setup (JUnit 5): Configured directly on the Eclipse Java Build Path (junit-jupiter-api, junit-jupiter-engine, and platform JARs) to test TodoBOImpl and TodoController offline.
    
    2. Data Persistence (SQLite)
    Embedded File Database: Uses SQLite (todos.db) via JDBC (jdbc:sqlite:...), eliminating the need to run a separate database server process.
    
    Environment-Driven Configuration: application.properties uses ${SPRING_DATASOURCE_URL:jdbc:sqlite:./data/todos.db} so the app reads ./data/todos.db when launched directly on the host machine and switches cleanly to /app/data/todos.db inside a container.
    
    3. Containerized Deployment (Docker & Compose)
    Runtime Image (Dockerfile): Built on eclipse-temurin:21-jre-alpine, copies the packaged Spring Boot JAR into /app/app.jar, creates /app/data, and exposes port 8080.
    
    Service Orchestration (docker-compose.yml):
    
    Maps host port 8080 to container port 8080.
    
    Injects SPRING_DATASOURCE_URL=jdbc:sqlite:/app/data/todos.db.
    
    Bind-mounts ./data:/app/data so SQLite records persist on the host filesystem across container restarts and rebuilds.

    # Testing instructions
    ### 1. Unit Tests (JUnit 5 in Eclipse)
    1. I used Maven, and I couldn't get all the jars needed so all you have to do  is open the pom.xml and uncomment the dependency for the artifact spring-boot-starter-test  and right click the project and Click on Maven and update Project.
     
    2. Add unit test classes under `src/test/java` for `TodoBOImpl` (business logic) and `TodoController` (controller behavior). Just uncomment them
    3. In Eclipse, right-click the test class or `src/test/java` and select **Run As -> JUnit Test**.
    4. Confirm the **JUnit** tab finishes with **0 errors** and **0 failures**.
    
    ### 2. Local Application & SQLite Persistence Test
    1. Create a `data` directory in the project root if it does not already exist.
    2. Start the Spring Boot application on port `8080`.
    3. Create and retrieve a sample To-Do item:
       ```bash
       curl -X POST http://localhost:8080/api/todos \
         -H "Content-Type: application/json" \
         -d '{"title":"Verify SQLite persistence","completed":false}'
    
       curl http://localhost:8080/api/todos
    Restart the application and run GET [http://localhost:8080/api/todos](http://localhost:8080/api/todos) again to verify that data/todos.db retains the saved record.
    
    3. Containerized Test (Docker Compose)
    Ensure the packaged JAR is in place and docker is available on the machine.
    
    Build and start the container in detached mode:
    
    Bash
    docker compose up --build -d
    Verify container health and logs:
    
    Bash
    docker compose ps
    docker compose logs --tail=50 todo-app
    Test http://localhost:8080/todos and confirm ./data/todos.db persists data across docker compose down and docker compose up -d.
    
    
    <ElicitationsGroup message="How would you like to refine these testing instructions?">
      <Elicitation label="Merge into full README.md" query="Combine these testing instructions with the architecture overview and Docker Compose setup into a complete README.md file."/>
      <Elicitation label="Adapt for Windows cmd" query="Rewrite the curl commands in the testing instructions for Windows Command Prompt (cmd) instead of bash."/>
      <Elicitation label="Add sample test cases" query="Write sample JUnit 5 test methods for TodoBOImpl and TodoController to include in the test suite."/>
    </ElicitationsGroup>


## Design Patterns Used

1. **Layered Architecture (N-Tier Pattern)**
   - Separates HTTP handling (`com.example.todo.action.TodoController`), business contracts and service logic (`com.example.todo.bo.TodoBO`, `com.example.todo.bo.impl.TodoBOImpl`), data access (`com.example.todo.DAO.TodoDAO`, `com.example.todo.DAO.impl.TodoDAOImpl`), factory creation (`com.example.todo.DAO.TodoRepositoryFactory`), and domain/value objects (`com.example.todo.vo.Todo`) into distinct layers.

2. **Front Controller & REST Controller (MVC)**
   - Spring's `DispatcherServlet` routes `/api/todos` requests to `@RestController` endpoints in `TodoController`, translating business outcomes, `Optional` values, and exceptions (`IllegalArgumentException`, `SQLException`) into HTTP `ResponseEntity` status codes.

3. **Data Transfer Object (DTO) / Request Record**
   - `TodoController.TodoRequest` (Java `record`) separates incoming HTTP request payloads from the internal `Todo` domain/value object.

4. **Business Object (`@Service` + Interface) & Fail-Fast Validation**
   - `TodoBO` defines the business contract and `TodoBOImpl` (`@Service`) enforces domain rules (such as rejecting blank titles with `IllegalArgumentException` and guarding null dependencies with `Objects.requireNonNull`) before delegating to `TodoDAO`.

5. **Data Access Object (DAO) Pattern**
   - `TodoDAO` and `TodoDAOImpl` encapsulate SQLite JDBC persistence operations (`add`, `findAll`, `markCompleted`, `updateTodo`, `deleteTodo`) behind a clean data-access interface.

6. **Static Factory Method Pattern**
   - `TodoRepositoryFactory` provides `createSqlite(String dbPath)` (`jdbc:sqlite:<path>`) and `createInMemorySqlite()` (`jdbc:sqlite::memory:`) to encapsulate `TodoDAOImpl` construction and JDBC URL setup for file-backed and in-memory test scenarios.

7. **Dependency Injection (IoC) & `@Configuration` Bean Factory**
   - Spring's IoC container manages bean lifecycles and constructor injection, with `AppConfig` (`@Co

# Benefits of this Design Patterns
Clear Separation of Concerns (Layered Architecture + BO + DAO): HTTP routing and status codes live in TodoController, business validation and workflow rules live in TodoBOImpl, and raw JDBC/SQLite statements live in TodoDAOImpl. Changing a REST endpoint does not touch SQL, and changing a query does not touch HTTP handling.

Safer Database Queries & Automatic Resource Cleanup (JDBC + Try-With-Resources): TodoDAOImpl uses parameterized PreparedStatements (? placeholders) to prevent SQL injection and try-with-resources blocks so Connection, Statement, PreparedStatement, and ResultSet close automatically after each operation.

Automatic Schema Bootstrapping: Calling initSchema() with CREATE TABLE IF NOT EXISTS todos (...) inside TodoDAOImpl's constructor ensures the todos table exists on first startup without requiring manual SQL setup scripts.

Decoupled API Input & Domain Object (DTO Record + VO): TodoRequest (record) isolates incoming JSON fields (title, completed) from com.example.todo.vo.Todo, letting API payloads evolve independently of your internal domain model.

Easy Testing & Variant Creation (Static Factory + DI): TodoRepositoryFactory (createSqlite and createInMemorySqlite) and constructor injection make it straightforward to wire SQLite for production or run isolated tests with an in-memory database or mock/stub TodoDAO.

Explicit HTTP Error Mapping: Catching IllegalArgumentException (400 Bad Request) and SQLException (500 Internal Server Error) in TodoController, paired with Optional<Todo> (200 OK vs. 404 Not Found) and boolean delete checks (204 No Content vs. 404 Not Found), gives API callers predictable status codes.

    # Security considerations
    Not applicable
