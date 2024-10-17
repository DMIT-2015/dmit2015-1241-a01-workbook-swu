package dmit2015.faces;

import dmit2015.entity.Department;
import dmit2015.entity.Employee;
import dmit2015.repository.EmployeeRepository;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Named("currentEmployeeQueryViewScopedView")
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
// class must implement Serializable
public class EmployeeQueryViewScopedView implements Serializable {

    // Declare read/write properties (field + getter + setter) for each form field
    @Getter @Setter
    private Department selectedDepartment;

    @Getter
    private List<Employee> queryResults;

    // Declare private fields for internal usage only objects
    @Inject
    private EmployeeRepository _employeeRepository;

    public List<Department> completeDepartment(String query) {
        return _employeeRepository.departmentsBy("%" + query + "%");
    }


    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject

    }

    public void onSubmit() {
        try {
            queryResults = _employeeRepository.employeesByDepartmentId(selectedDepartment.getId());
            Messages.addGlobalInfo("Query returned {0} records.", queryResults.size());
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public void onClear() {
        selectedDepartment = null;
        queryResults = null;
    }

    /**
     * This method is used to handle exceptions and display root cause to user.
     *
     * @param ex The Exception to handle.
     */
    protected void handleException(Exception ex) {
        var details = new StringBuilder();
        Throwable causes = ex;
        while (causes.getCause() != null) {
            details.append(ex.getMessage());
            details.append("    Caused by:");
            details.append(causes.getCause().getMessage());
            causes = causes.getCause();
        }
        Messages.create(ex.getMessage()).detail(details.toString()).error().add("errors");
    }

}