package dmit2015.faces;

import org.omnifaces.util.Faces;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;

@Named("firebaseLogout")
@RequestScoped
public class FirebaseLogout {

    public void submit() throws ServletException {
        Faces.invalidateSession();
        Faces.redirect( Faces.getRequestContextPath() + "/firebaseLogin.xhtml?faces-redirect=true");
    }

}