package dmit2015.faces;

import dmit2015.entity.Student;
import dmit2015.persistence.StudentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.util.List;

@Named("currentStudentCrudView")
@ViewScoped
public class StudentCrudView implements Serializable {

    @Inject
    private StudentRepository _studentRepository;

    @Getter
    private List<Student> students;

    @Getter
    @Setter
    private Student selectedStudent;

    @Getter
    @Setter
    private Long selectedId;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            students = _studentRepository.findAll();
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public void onOpenNew() {
        selectedStudent = new Student();
        selectedId = null;
    }

    public void onSave() {
        if (selectedId == null) {
            try {
                _studentRepository.add(selectedStudent);
                Messages.addGlobalInfo("Create was successful. {0}", selectedStudent.getId());
                students = _studentRepository.findAll();
            } catch (RuntimeException ex) {
                Messages.addGlobalError(ex.getMessage());
            } catch (Exception ex) {
                Messages.addGlobalError("Create was not successful. {0}", ex.getMessage());
                handleException(ex);
            }
        } else {
            try {
                _studentRepository.update(selectedId, selectedStudent);
                Messages.addFlashGlobalInfo("Update was successful.");
                students = _studentRepository.findAll();
            } catch (RuntimeException ex) {
                Messages.addGlobalError(ex.getMessage());
            } catch (Exception ex) {
                Messages.addGlobalError("Update was not successful.");
                handleException(ex);
            }
        }

        PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-Students");
    }

    public void onDelete() {
        try {
            _studentRepository.delete(selectedStudent);
            selectedStudent = null;
            selectedId = null;
            Messages.addGlobalInfo("Delete was successful.");
            students = _studentRepository.findAll();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-Students");
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        } catch (Exception ex) {
            Messages.addGlobalError("Delete not successful.");
            handleException(ex);
        }
    }

    /**
     * This method is used to handle exceptions and display root cause to user.
     *
     * @param ex The Exception to handle.
     */
    protected void handleException(Exception ex) {
        StringBuilder details = new StringBuilder();
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