package dmit2015.restclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * The baseUri for the web MpRestClient be set in either microprofile-config.properties (recommended)
 * or in this file using @RegisterRestClient(baseUri = "http://server/path").
 * <p>
 * To set the baseUri in microprofile-config.properties:
 * 1) Open src/main/resources/META-INF/microprofile-config.properties
 * 2) Add a key/value pair in the following format:
 * package-name.ClassName/mp-rest/url=baseUri
 * For example:
 * package-name:    dmit2015.restclient
 * ClassName:       TodoApiResponseMpRestClient
 * baseUri:         http://localhost:8080/contextName
 * The key/value pair you need to add is:
 * {@snippet :
 *          dmit2015.restclient.TodoApiResponseMpRestClient/mp-rest/url=http://localhost:8080/contextName
 * }
 * <p>
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 * {@snippet :
 *     @Inject
 *     @RestClient
 *     private TodoApiResponseMpRestClient _todoapiresponseMpRestClient;
 * }
 * <p>
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 * {@snippet :
 *      URI apiURI = new URI("http://sever/contextName");
 *      TodoApiResponseMpRestClient _todoapiresponseMpRestClient = RestClientBuilder.newBuilder().baseUri(apiURi).build(TodoApiResponseMpRestClient.class);* }
 */
@RequestScoped
@RegisterProvider(TodoItemRestApiResponseMapper.class)
@RegisterRestClient (baseUri = "http://localhost:8090/restapi/TodoItemDtos")
public interface TodoApiResponseMpRestClient {

    @POST
    Response create(@HeaderParam("Authorization") String authorizationHeader,TodoApiResponse newTodoApiResponse);

    @GET
    List<TodoApiResponse> findAll(@HeaderParam("Authorization") String authorizationHeader);

    @GET
    @Path("/{id}")
    TodoApiResponse findById(@HeaderParam("Authorization") String authorizationHeader,@PathParam("id") Long id);

    @PUT
    @Path("/{id}")
    TodoApiResponse update(@HeaderParam("Authorization") String authorizationHeader,@PathParam("id") Long id, TodoApiResponse updatedTodoApiResponse);

    @DELETE
    @Path("/{id}")
    void delete(@HeaderParam("Authorization") String authorizationHeader,@PathParam("id") Long id);

}