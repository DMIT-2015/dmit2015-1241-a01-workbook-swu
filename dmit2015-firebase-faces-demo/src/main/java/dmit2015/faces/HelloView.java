package dmit2015.faces;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.omnifaces.util.Messages;

@Named // create an instance of this class with same name with first letter in lowercase
        // helloView
@RequestScoped
public class HelloView {

    // Define a data field for storing username
    private String username;

    // Define getters/setts to encapsulate access to our data fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Define a method to handle a submit action
    public void onSubmit() {
        // Send an info message to FacesContext with a greeting message
        Messages.addGlobalInfo("Welcome {0} to Jakarta Faces info message",
                username);
        // Send an info message to FacesContext with a greeting message
        Messages.addGlobalError("Welcome {0} to Jakarta Faces error message",
                username);

    }
}
