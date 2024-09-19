package dmit2015.restclient;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named("firebaseLoginSession")
@SessionScoped
public class FirebaseLoginSession implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private FirebaseAuthenticationMpRestClient _loginService;

    @Inject
    @ConfigProperty(name = "firebase.webapikey")
    private String _firebaserestapiKey;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private FirebaseUser firebaseUser;

    public String checkForToken() {
        String nextPage = null;

        if (firebaseUser == null || firebaseUser.getIdToken() == null) {
            nextPage = "/firebaseLogin?requestURI=" + Utils.encodeURI(Faces.getRequestURI());
        } else if (firebaseUser.getExpiresInDateTime().isAfter(LocalDateTime.now())) {
            JsonObject requestBodyPayload = Json.createObjectBuilder()
                    .add("grant_type","refresh_token")
                    .add("refresh_token", firebaseUser.getRefreshToken())
                    .build();
           JsonObject responsePayload = _loginService.refreshToken(_firebaserestapiKey, requestBodyPayload);
           firebaseUser.setExpiresIn(responsePayload.getString("expires_in"));
           firebaseUser.setRefreshToken(responsePayload.getString("refresh_token"));
           firebaseUser.setIdToken(responsePayload.getString("id_token"));
           firebaseUser.setLocalId(responsePayload.getString("user_id"));
        }

        return nextPage;
    }
}