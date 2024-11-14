package dmit2015.restclient;

import dmit2015.restclient.KeycloakLoginMpRestClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import java.time.LocalDateTime;

@Named
@RequestScoped
public class KeycloakLogin {

    @Inject
    @ConfigProperty(name = "keycloak.oidc.clientId")
    private String oidcClientId;

    @Inject
    @ConfigProperty(name = "keycloak.oidc.clientSecret")
    private String oidcClientSecret;

    @Inject
    @RestClient
    private KeycloakLoginMpRestClient _loginRestClient;

    @NotBlank(message = "Username value is required.")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password value is required.")
    @Getter
    @Setter
    private String password;

    @Inject
    private LoginSession _loginSession;

    public String submit() {
        String nextPage = null;

        try {
            JsonObject responsePayload = _loginRestClient.authenticate(username, password, oidcClientId, oidcClientSecret, "password");
            _loginSession.setLoginResponsePayload(responsePayload);
            _loginSession.setExpiresIn(LocalDateTime.now().plusSeconds(responsePayload.getInt("expires_in")));
            _loginSession.setUsername(username);
            nextPage = "/index?faces-redirect=true";
        } catch (Exception e) {
            Messages.addGlobalError(e.getMessage());
        }

        return nextPage;
    }

}