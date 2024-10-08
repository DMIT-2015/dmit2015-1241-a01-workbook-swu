package dmit2015.entity;

import dmit2015.persistence.StudentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import net.datafaker.Faker;

import java.util.logging.Logger;

@ApplicationScoped
public class StudentInitializer {
    private final Logger _logger = Logger.getLogger(StudentInitializer.class.getName());

    @Inject
    private StudentRepository _studentRepository;


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
        _logger.info("Initializing students");

        if (_studentRepository.count() == 0) {
            /* You have three options for creating the test data:
                Option 1) Hard code the test data when testing a small dataset.
                Option 2) Read the test data from a text file when testing a large dataset.
                Option 3) Generate the test data using DataFaker.
                          When used with Integration Testing you will need to save the generated data
                          to a file that can be read later to compare with expected values.
             */

            try {
                // Use Faker to generate 10 random Student
                var fake = new Faker();
                for (int count = 0; count < 10; count++) {
                    var student = new Student();
                    student.setFirstName(fake.name().firstName());
                    student.setLastName(fake.name().lastName());
                    student.setEmail(fake.internet().emailAddress());

                    _studentRepository.add(student);
                }

            } catch (Exception ex) {
                _logger.warning(ex.getMessage());
            }

            _logger.info("Created " + _studentRepository.count() + " records.");
        }
    }
}