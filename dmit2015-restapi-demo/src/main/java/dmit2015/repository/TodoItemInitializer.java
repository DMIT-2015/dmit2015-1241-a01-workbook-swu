package dmit2015.repository;

import dmit2015.entity.TodoItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class TodoItemInitializer {
    private final Logger _logger = Logger.getLogger(TodoItemInitializer.class.getName());

    @Inject
    private TodoItemRepository _todoItemRepository;


    /**
     * Using the combination of `@Observes` and `@Initialized` annotations, you can
     * intercept and perform additional processing during the phase of beans or events
     * in a CDI container.
     * <p>
     * The @Observers is used to specify this method is in observer for an event
     * The @Initialized is used to specify the method should be invoked when a bean type of `ApplicationScoped` is being
     * initialized
     * <p>
     * Execute code to create the test data for the entity.
     * This is an alternative to using a @WebListener that implements a ServletContext listener.
     * <p>
     * ]    * @param event
     */
    public void initialize(@Observes @Initialized(ApplicationScoped.class) Object event) {
        _logger.info("Initializing todoItems");

        if (_todoItemRepository.count() == 0) {

            // You could hard code the test data
            try {
                // TODO: Create a new entity instance
                // TODO: Set the properties of the entity instance
                // TODO: Add the entity instance to the JPA repository
                TodoItem todo1 = new TodoItem();
                todo1.setTask("Create JAX-RS demo project");
                todo1.setDone(true);
                _todoItemRepository.add(todo1);

                TodoItem todo2 = new TodoItem();
                todo2.setTask("Run and verify all Integration Test pass");
                todo2.setDone(false);
                _todoItemRepository.add(todo2);

                TodoItem todo3 = new TodoItem();
                todo3.setTask("Create DTO version of TodoResource");
                todo3.setDone(false);
                _todoItemRepository.add(todo3);


            } catch (Exception ex) {
                _logger.fine(ex.getMessage());
            }

            _logger.info("Created " + _todoItemRepository.count() + " records.");
        }
    }
}