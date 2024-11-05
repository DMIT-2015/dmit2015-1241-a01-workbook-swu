package dmit2015.resource;

import dmit2015.entity.TodoItem;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * This class contains starter code for testing REST API endpoints for CRUD operations using REST-assured.
 *
 * <a href="https://github.com/rest-assured/rest-assured">REST Assured GitHub repo</a>
 * <a href="https://github.com/rest-assured/rest-assured/wiki/Usage">REST Assured Usage</a>
 * <a href="http://www.mastertheboss.com/jboss-frameworks/resteasy/restassured-tutorial">REST Assured Tutorial</a>
 * <a href="https://hamcrest.org/JavaHamcrest/tutorial">Hamcrest Tutorial</a>
 * <a href="https://github.com/FasterXML/jackson-databind">Jackson Data-Binding</a>
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoItemResourceRestAssuredFakerIT {

    static Faker faker = new Faker();

    final String todoItemResourceUrl = "http://localhost:8090/restapi/TodoItems";

    @BeforeAll
    static void beforeAllTests() {
        // code to execute before all tests in the current test class
    }

    @AfterAll
    static void afterAllTests() {
        // code to execute after all tests in the current test class
    }

    @BeforeEach
    void beforeEachTestMethod() {
        // Code to execute before each test such as creating the test data
    }

    @AfterEach
    void afterEachTestMethod() {
        // code to execute after each test such as deleting the test data
    }

    @Order(1)
    @Test
    void givenTodoItemData_whenCreateTodoItem_thenTodoItemIsCreated() throws Exception {
        // Arrange: Set up the initial state
        var currentTodoItem = new TodoItem();
        currentTodoItem.setTask(faker.pokemon().move()) ;
//        currentTodoItem.setProperty2(faker.providerName().methodName());
//        currentTodoItem.setProperty3(faker.providerName().methodName());

        // Act & Assert
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonBody = jsonb.toJson(currentTodoItem);
            given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
            .when()
                .post(todoItemResourceUrl)
            .then()
                .statusCode(201)
                .header("location", containsString(todoItemResourceUrl))
            ;
        }
    }

    @Order(2)
    @Test
    void givenExistingTodoItemId_whenFindTodoItemById_thenReturnTodoItem() throws Exception {
        // Arrange: Set up the initial state
        var currentTodoItem = new TodoItem();
        currentTodoItem.setTask(faker.pokemon().move()) ;
        currentTodoItem.setDone(false);
//        currentTodoItem.setProperty3(faker.providerName().methodName());

       // Act & Assert
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonBody = jsonb.toJson(currentTodoItem);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when()
                    .post(todoItemResourceUrl);
            var postedDataLocation = response.getHeader("location");

            // Act & Assert
            given()
            .when()
                .get(postedDataLocation)
            .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("task", equalTo(currentTodoItem.getTask()))
                .body("done", equalTo(currentTodoItem.isDone()))
//                .body("property3", equalTo(currentTodoItem.getProperty3()))
                ;
        }

    }

    @Order(3)
    @Test
    void givenTodoItemExist_whenFindAllTodoItems_thenReturnTodoItemList() throws Exception {
        // Arrange: Set up the initial state
        try (Jsonb jsonb = JsonbBuilder.create()) {
            // Post 3 records and verify the 3 records are added
            var todoItems = new ArrayList<TodoItem>();
            for (int index = 0; index < 3; index++) {
                var currentTodoItem = new TodoItem();
                currentTodoItem.setTask(faker.pokemon().move()) ;
//                currentTodoItem.setProperty2(faker.providerName().methodName());
//                currentTodoItem.setProperty3(faker.providerName().methodName());

                todoItems.add(currentTodoItem);

                given()
                        .contentType(ContentType.JSON)
                        .body(jsonb.toJson(todoItems.get(index)))
                        .when()
                        .post(todoItemResourceUrl)
                .then()
                        .statusCode(201);
            }

            // Act & Assert: Perform the action and verify the expected outcome
            given()
                    .when()
                    .get(todoItemResourceUrl)
            .then()
                    .statusCode(200)
                    .body("size()", greaterThan(0))
                    .body("task", hasItems(todoItems.getFirst().getTask(), todoItems.getLast().getTask()))
//                    .body("property2", hasItems(todoItems.getFirst().getProperty2(), todoItems.getLast().getProperty2()))
//                    .body("property3", hasItems(todoItems.getFirst().getProperty3(), todoItems.getLast().getProperty3()))
                    ;

        }

    }

    @Order(4)
    @Test
    void givenUpdatedTodoItemData_whenUpdatedTodoItem_thenTodoItemIsUpdated() throws Exception {
        // Arrange: Set up the initial state
        var createTodoItem = new TodoItem();
        createTodoItem.setTask(faker.pokemon().move()) ;
//        createTodoItem.setProperty2(faker.providerName().methodName());
//        createTodoItem.setProperty3(faker.providerName().methodName());

        var updateTodoItem = new TodoItem();
        updateTodoItem.setTask(faker.pokemon().move()) ;
//        updateTodoItem.setProperty2(faker.providerName().methodName());
//        updateTodoItem.setProperty2(faker.providerName().methodName());

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String createJsonBody = jsonb.toJson(createTodoItem);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(createJsonBody)
                    .when()
                    .post(todoItemResourceUrl);
            var postedDataLocation = response.getHeader("location");
            Long entityId = Long.parseLong(postedDataLocation.substring(postedDataLocation.lastIndexOf("/") + 1));
            updateTodoItem.setId(entityId);
            // Act & Assert
            String updateJsonBody = jsonb.toJson(updateTodoItem);
            given()
                .contentType(ContentType.JSON)
                .body(updateJsonBody)
            .when()
//                .pathParam("id", entityId)
                .put(postedDataLocation)
            .then()
                .statusCode(200)
                .body("id", equalTo(entityId.intValue()))
                .body("task", equalTo(updateTodoItem.getTask()))
//                .body("property2", equalTo(updateTodoItem.getProperty2()))
//                .body("property3", equalTo(updateTodoItem.getProperty3()))
            ;
        }

    }

    @Order(5)
    @Test
    void givenExistingTodoItemId_whenDeleteTodoItem_thenTodoItemIsDeleted() throws Exception {
        // Arrange: Set up the initial state
        var currentTodoItem = new TodoItem();
        currentTodoItem.setTask(faker.pokemon().move()) ;
//        currentTodoItem.setProperty2(faker.providerName().methodName());
//        currentTodoItem.setProperty3(faker.providerName().methodName());

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonBody = jsonb.toJson(currentTodoItem);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when()
                    .post(todoItemResourceUrl);
            var postedDataLocation = response.getHeader("location");
            int entityId = Integer.parseInt(postedDataLocation.substring(postedDataLocation.lastIndexOf("/") + 1));

            // Act & Assert
            given()
//                .pathParam("id", entityId)
            .when()
                .delete(postedDataLocation)
            .then()
                .statusCode(204);

            // Verify deletion
            given()
            .when()
                .delete(postedDataLocation)
            .then()
                .statusCode(404);
        }

    }

}