package dmit2015.faces;

import dmit2015.model.WeatherData;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This Jakarta Faces backing bean class contains the data and event handlers
 * to perform CRUD operations using a PrimeFaces DataTable configured to perform CRUD.
 * <p>
 * The Java HttpClient library is used send Http Request to the Firebase Realtime Database REST API.
 */
@Named("currentWeatherDataFirebaseRtdbCrudView")
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
public class WeatherDataFirebaseRtdbCrudView implements Serializable {

    /**
     * The selected WeatherData instance to create, edit, update or delete.
     */
    @Getter
    @Setter
    private WeatherData selectedWeatherData;

    /**
     * The unique name of the selected WeatherData instance.
     */
    @Getter
    @Setter
    private String selectedId;

    /**
     * The list of WeatherData objects fetched from the Firebase Realtime Database
     */
    @Getter
    private List<WeatherData> weatherDatas;

    /**
     * The base URL to the Firebase Realtime Database
     */
    private static final String FIREBASE_REALTIME_DATABASE_BASE_URL = "https://dmit2015-1241-a01-swu-default-rtdb.firebaseio.com";

    /**
     * The URL to the Firebase Realtime Database to access all data.
     */
    private String _jsonAllDataPath;

    /**
     * Fetch all WeatherData from the Firebase Realtime Database
     */
    @PostConstruct
    public void init() {
        _jsonAllDataPath = String.format("%s/%s.json", FIREBASE_REALTIME_DATABASE_BASE_URL, WeatherData.class.getSimpleName());
        fetchFirebaseData();
    }

    /**
     * Event handler for the New button on the Faces crud page.
     * Create a new selected WeatherData instance to enter data for.
     */
    public void onOpenNew() {
        selectedWeatherData = new WeatherData();
        selectedId = null;
    }

    /**
     * Use the DataFaker to generate random data.
     *
     * @link <a href="https://www.datafaker.net/documentation/getting-started/">Getting started with DataFaker</a>
     */
    public void onGenerateData() {
        try {
            var faker = new Faker();
            //currentWeatherData.setPropertyName(faker.providerName().dataName());

        } catch (Exception e) {
            Messages.addGlobalError("Error generating data {0}", e.getMessage());
        }

    }

    /**
     * Push or Write currentWeatherData to Firebase Realtime Database using the REST API
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/database">Firebase Realtime Database REST API</a>
     */
    public void onSave() {
        // Jsonb is used for converting Java objects to a JSON string or visa-versa
        // HttpClient is native Java library for sending Http Request to a web server
        try (Jsonb jsonb = JsonbBuilder.create();
             var httpClient = HttpClient.newHttpClient()) {

            // Convert the currentWeatherData to a JSON string using JSONB
            String requestBodyJson = jsonb.toJson(selectedWeatherData);

            // If selecteId is null then create new data otherwise update current data
            if (selectedId == null) {

                // Create an Http Request for sending an Http POST request to push new data
                var httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(_jsonAllDataPath))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson, StandardCharsets.UTF_8))
                        .build();
                // Send the Http Request
                var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                // Check if the Http Request response is successful
                if (httpResponse.statusCode() == 200) {
                    // Get the body of the Http Response
                    var responseBodyJson = httpResponse.body();
                    // Convert the JSON String to a JsonObject
                    JsonObject responseJsonObject = jsonb.fromJson(responseBodyJson, JsonObject.class);
                    // Send a Faces info message that add was successful
                    Messages.addGlobalInfo("Successfully added data with name {0}", responseJsonObject.getString("name"));
                    // Reset the selected instance to null
                    selectedWeatherData = null;

                } else {
                    // Send a Faces info message that add was not successful
                    Messages.addGlobalInfo("Add was not successful, server return status {0}", httpResponse.statusCode());
                }

            } else {

                // Build the url path to object to update
                String _jsonSingleDataPath = String.format("%s/%s/%s.json",
                        FIREBASE_REALTIME_DATABASE_BASE_URL, WeatherData.class.getSimpleName(), selectedWeatherData.getName());

                // Create and Http Request to send an HTTP PUT request to write over existing data
                var httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(_jsonSingleDataPath))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(requestBodyJson, StandardCharsets.UTF_8))
                        .build();
                // Send the Http Request
                var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                // Check if the Http Response was successful
                if (httpResponse.statusCode() == 200) {
                    // Get the body of the Http Response
                    var responseBodyJson = httpResponse.body();
                    // Convert the JSON String to an WeatherData instance
                    WeatherData updatedWeatherData = jsonb.fromJson(responseBodyJson, WeatherData.class);
                    // Send a Faces info message that update was successful
                    Messages.addGlobalInfo("Successfully updated WeatherData {0}",
                            updatedWeatherData.toString());
                } else {
                    // Send a Faces info message that update was not successful
                    Messages.addGlobalInfo("Update was not successful, server return status {0}", httpResponse.statusCode());
                }

            }

            // Fetch a list of objects from the Firebase RTDB
            fetchFirebaseData();

            // Hide the PrimeFaces dialog
            PrimeFaces.current().executeScript("PF('manageWeatherDataDialog').hide()");

        } catch (Exception e) {
            // Send a Faces info message that an error occurred when saving
            Messages.addGlobalError("Error saving data {0}", e.getMessage());
        }


    }

    /**
     * Remove currentWeatherData to Firebase Realtime Database using the REST API
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/database">Firebase Realtime Database REST API</a>
     */
    public void onDelete() {
        // Jsonb is used for converting Java objects to a JSON string or visa-versa
        // HttpClient is native Java library for sending Http Request to a web server
        try (Jsonb jsonb = JsonbBuilder.create();
             var httpClient = HttpClient.newHttpClient()) {

            // Get the unique name of the Json object to delete
            String name = selectedWeatherData.getName();
            // Build the URL path of the Json object to delete
            String _jsonSingleDataPath = String.format("%s/%s/%s.json",
                    FIREBASE_REALTIME_DATABASE_BASE_URL, WeatherData.class.getSimpleName(), name);
            // Create an DELETE Http Request
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(_jsonSingleDataPath))
                    .DELETE()
                    .build();
            // Send the DELETE Http Request
            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            // Check if the Http Reqponse was successful
            if (httpResponse.statusCode() == 200) {
                Messages.addGlobalInfo("Successfully deleted data with name {0}", name);
                // Fetch new data from Firebase
                fetchFirebaseData();
            } else {
                // Send a Faces info message that delete was not successful
                Messages.addGlobalInfo("Delete was not successful, server return status {0}", httpResponse.statusCode());
            }

        } catch (Exception e) {
            // Send a Faces error message with the exception message
            Messages.addGlobalError("Error deleting Firebase Realtime Database data {0}", e.getMessage());
        }

    }

    /**
     * Get currentWeatherData to Firebase Realtime Database using the REST API
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/database">Firebase Realtime Database REST API</a>
     */
    private void fetchFirebaseData() {
        // Jsonb is used for converting Java objects to a JSON string or visa-versa
        // HttpClient is native Java library for sending Http Request to a web server
        try (Jsonb jsonb = JsonbBuilder.create();
             var httpClient = HttpClient.newHttpClient()) {

            // Create an GET Http Request to fetch all data
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(_jsonAllDataPath))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            // Send the GET Http Request
            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            // Check if the Http Request was successful
            if (httpResponse.statusCode() == 200) {
                // Get the body of the Http Response
                var responseBodyJson = httpResponse.body();
                // Convert the responseBodyJson to an LinkedHashMap<String, WeatherData>
                LinkedHashMap<String, WeatherData> responseData = jsonb.fromJson(responseBodyJson, new LinkedHashMap<String, WeatherData>() {
                }.getClass().getGenericSuperclass());
                // Convert the LinkedHashMap<String, WeatherData> to List<WeatherData>
                weatherDatas = responseData.entrySet()
                        .stream()
                        .map(item -> {
                            var currentWeatherData = new WeatherData();
                            currentWeatherData.setName(item.getKey());

                            currentWeatherData.setCity(item.getValue().getCity());
                            currentWeatherData.setDate(item.getValue().getDate());
                            currentWeatherData.setDescription(item.getValue().getDescription());
                            currentWeatherData.setTemperatureCelsius(item.getValue().getTemperatureCelsius());

                            return currentWeatherData;
                        })
                        .toList();

                Messages.addGlobalInfo("Successfully fetched Firebase Realtime Database data");
                PrimeFaces.current().ajax().update("dialogs:messages", "form:dt-WeatherDatas");
            } else {
                Messages.addGlobalInfo("Fetch data was not successful, server return status {0}", httpResponse.statusCode());
            }

        } catch (Exception e) {
            Messages.addGlobalError("Error adding Firebase Realtime Database data {0}", e.getMessage());
        }
    }
}