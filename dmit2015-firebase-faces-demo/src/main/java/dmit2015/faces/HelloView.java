package dmit2015.faces;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named // create an instance of this class with same name with first letter in lowercase
        // helloView
//@RequestScoped
@ViewScoped
public class HelloView implements Serializable {

    // Define a data field for storing username
    @Getter @Setter
    @NotBlank(message = "Please enter a username")
    private String username;

    // Define a list of String username
    @Getter
    private List<String> usernameList = new ArrayList<>();

    // Define getters/setts to encapsulate access to our data fields
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    // Define a method to handle a submit action
    public void onSubmit() {
        // Send an info message to FacesContext with a greeting message
        Messages.addGlobalInfo("Welcome {0} to Jakarta Faces info message",
                username);
        // Send an info message to FacesContext with a greeting message
        Messages.addGlobalError("Welcome {0} to Jakarta Faces error message",
                username);
        // Clear the username field
        username = null;
    }

    // Define a method to add username to usernameList
    public void onAddUsername() {
        usernameList.add(username);
    }

    // Define a method to clear username and usernameList
    public void onClear() {
        usernameList.clear();
        username = null;
    }
}
