package dmit2015.restclient;

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
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoApiResponseResourceRestAssuredFakerIT {

    static Faker faker = new Faker();

    final String todoApiResponseResourceUrl = "http://localhost:8090/restapi/TodoItemDtos";

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
    void givenTodoApiResponseData_whenCreateTodoApiResponse_thenTodoApiResponseIsCreated() throws Exception {
        // Arrange: Set up the initial state
        var currentTodoApiResponse = new TodoApiResponse();
        currentTodoApiResponse.setName(faker.pokemon().move()); ;
        currentTodoApiResponse.setComplete(false);

        // Act & Assert
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonBody = jsonb.toJson(currentTodoApiResponse);
            given()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when()
                    .post(todoApiResponseResourceUrl)
                    .then()
                    .statusCode(201)
                    .header("location", containsString(todoApiResponseResourceUrl))
            ;
        }
    }

    @Order(2)
    @Test
    void givenExistingTodoApiResponseId_whenFindTodoApiResponseById_thenReturnTodoApiResponse() throws Exception {
        // Arrange: Set up the initial state
        var currentTodoApiResponse = new TodoApiResponse();
        currentTodoApiResponse.setName(faker.pokemon().move()); ;
        currentTodoApiResponse.setComplete(false);

        // Act & Assert
        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonBody = jsonb.toJson(currentTodoApiResponse);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when()
                    .post(todoApiResponseResourceUrl);
            var postedDataLocation = response.getHeader("location");

            // Act & Assert
            given()
                    .when()
                    .get(postedDataLocation)
                    .then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("name", equalTo(currentTodoApiResponse.getName()))
                    .body("complete", equalTo(currentTodoApiResponse.getComplete()))
            ;
        }

    }

    @Order(3)
    @Test
    void givenTodoApiResponseExist_whenFindAllTodoApiResponses_thenReturnTodoApiResponseList() throws Exception {
        // Arrange: Set up the initial state
        try (Jsonb jsonb = JsonbBuilder.create()) {
            // Post 3 records and verify the 3 records are added
            var todoApiResponses = new ArrayList<TodoApiResponse>();
            for (int index = 0; index < 3; index++) {
                var currentTodoApiResponse = new TodoApiResponse();
                currentTodoApiResponse.setName(faker.pokemon().move()); ;
                currentTodoApiResponse.setComplete(false);

                todoApiResponses.add(currentTodoApiResponse);

                given()
                        .contentType(ContentType.JSON)
                        .body(jsonb.toJson(todoApiResponses.get(index)))
                        .when()
                        .post(todoApiResponseResourceUrl)
                        .then()
                        .statusCode(201);
            }

            // Act & Assert: Perform the action and verify the expected outcome
            given()
                    .when()
                    .get(todoApiResponseResourceUrl)
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThan(0))
                    .body("name", hasItems(todoApiResponses.getFirst().getName(), todoApiResponses.getLast().getName()))
                    .body("complete", hasItems(todoApiResponses.getFirst().getComplete(), todoApiResponses.getLast().getComplete()))
            ;

        }

    }

    @Order(4)
    @Test
    void givenUpdatedTodoApiResponseData_whenUpdatedTodoApiResponse_thenTodoApiResponseIsUpdated() throws Exception {
        // Arrange: Set up the initial state
        var createTodoApiResponse = new TodoApiResponse();
        createTodoApiResponse.setName(faker.pokemon().move()); ;
        createTodoApiResponse.setComplete(false);

        var updateTodoApiResponse = new TodoApiResponse();
        updateTodoApiResponse.setName(faker.pokemon().move()); ;
        updateTodoApiResponse.setComplete(true);

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String createJsonBody = jsonb.toJson(createTodoApiResponse);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(createJsonBody)
                    .when()
                    .post(todoApiResponseResourceUrl);
            var postedDataLocation = response.getHeader("location");
            Long entityId = Long.parseLong(postedDataLocation.substring(postedDataLocation.lastIndexOf("/") + 1));
            updateTodoApiResponse.setId(entityId.intValue());
            // Act & Assert
            String updateJsonBody = jsonb.toJson(updateTodoApiResponse);
            given()
                    .contentType(ContentType.JSON)
                    .body(updateJsonBody)
                    .when()
//                .pathParam("id", entityId)
                    .put(postedDataLocation)
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(entityId.intValue()))
                    .body("name", equalTo(updateTodoApiResponse.getName()))
                    .body("complete", equalTo(updateTodoApiResponse.getComplete()))
            ;
        }

    }

    @Order(5)
    @Test
    void givenExistingTodoApiResponseId_whenDeleteTodoApiResponse_thenTodoApiResponseIsDeleted() throws Exception {
        // Arrange: Set up the initial state
        var currentTodoApiResponse = new TodoApiResponse();
        currentTodoApiResponse.setName(faker.pokemon().move()); ;
        currentTodoApiResponse.setComplete(false);

        try (Jsonb jsonb = JsonbBuilder.create()) {
            String jsonBody = jsonb.toJson(currentTodoApiResponse);

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(jsonBody)
                    .when()
                    .post(todoApiResponseResourceUrl);
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