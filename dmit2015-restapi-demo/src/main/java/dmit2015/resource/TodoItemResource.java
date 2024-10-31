package dmit2015.resource;

import common.validator.BeanValidator;
import dmit2015.entity.TodoItem;
import dmit2015.repository.TodoItemRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

/**
 * This Jakarta RESTful Web Services root resource class provides common REST API endpoints to
 * perform CRUD operations on Jakarta Persistence entity.
 */
@ApplicationScoped
@Path("TodoItems")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class TodoItemResource {

    @Inject
    private TodoItemRepository _todoItemRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response findAllTodoItemsTodoItems() {
        return Response.ok(_todoItemRepository.findAll()).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findTodoItemById(@PathParam("id") Long id) {
        TodoItem existingTodoItem = _todoItemRepository.findById(id).orElseThrow(NotFoundException::new);

        return Response.ok(existingTodoItem).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response createTodoItemTodoItem(TodoItem newTodoItem, @Context UriInfo uriInfo) {

        String errorMessage = BeanValidator.validateBean(newTodoItem);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new TodoItem into the database
            _todoItemRepository.add(newTodoItem);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newTodoItem.getId()))
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the TodoItem was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateTodoItemTodoItem(@PathParam("id") Long id, TodoItem updatedTodoItem) {
        if (!id.equals(updatedTodoItem.getId())) {
            throw new BadRequestException();
        }

        String errorMessage = BeanValidator.validateBean(updatedTodoItem);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        TodoItem existingTodoItem = _todoItemRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
        existingTodoItem.setVersion(updatedTodoItem.getVersion());
        existingTodoItem.setTask(updatedTodoItem.getTask());
        existingTodoItem.setDone(updatedTodoItem.isDone());

        try {
            _todoItemRepository.update(id, existingTodoItem);
        } catch (OptimisticLockException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The data you are trying to update has changed since your last read request.")
                    .build();
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "200 OK" and include in the body of the response the object that was updated
        return Response.ok(existingTodoItem).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response deleteTodoItem(@PathParam("id") Long id) {

        TodoItem existingTodoItem = _todoItemRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        try {
            _todoItemRepository.delete(existingTodoItem);    // Removes the TodoItem from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" to indicated that the resource was deleted
        return Response.noContent().build();
    }

}