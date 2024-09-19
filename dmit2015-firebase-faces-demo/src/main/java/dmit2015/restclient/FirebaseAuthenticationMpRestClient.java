package dmit2015.restclient;

import jakarta.enterprise.context.Dependent;
import jakarta.json.JsonObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.Serializable;

/**
 *
 * To set the baseUri in microprofile-config.properties:
 * 1) Open src/main/resources/META-INF/microprofile-config.properties
 * 2) Add a key/value pair in the following format:
 *       package-name.ClassName/mp-rest/url=baseUri
 *       For example:
 *          package-name:    dmit2015.client
 *          ClassName:       FirebaseAuthService
 *          baseUri:         https://identitytoolkit.googleapis.com/v1
 *       The key/value pair you need to add is:
 *          dmit2015.client.FirebaseAuthService/mp-rest/url=https://identitytoolkit.googleapis.com/v1
 *
 */
@Dependent
@RegisterRestClient(baseUri = "https://identitytoolkit.googleapis.com/v1")
@RegisterProvider(FirebaseAuthenticationResponseMapper.class)   // Register the response mapper
public interface FirebaseAuthenticationMpRestClient extends Serializable {

    /**
     * Create a new email and password user
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth#section-create-email-password">...</a>
     * @param apiKey Firebase project Web API key
     * @param payload Request Body Payload with properties for: email, password, returnSecureToken
     * @return Response Payload with properties for: idToken, email, refreshToken, expiresIn, localId
     */
    @POST
    @Path("/accounts:signUp")
    JsonObject signUp(@QueryParam("key") String apiKey, JsonObject payload);

    /**
     * Sign in a user with an email and password.
     * The response body payload property idToken contains the token we need to use for future requests
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth/#section-sign-in-email-password">...</a>
     *
     * @param apiKey Firebase project Web API key
     * @param payload Request Body Payload with properties for: email, password, returnSecureToken
     * @return Response Payload with properties for: idToken, email, refreshToken, expiresIn, localId, registered
     */
    @POST
    @Path("/accounts:signInWithPassword")
    FirebaseUser signIn(@QueryParam("key") String apiKey, JsonObject payload);

    /**
     * Refresh a Fireabse ID token.
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth/#section-refresh-token">...</a>
     *
     * @param apiKey Firebase project Web API key
     * @param payload Request Body Payload with properties for: token, returnSecureToken
     * @return Response Payload with properties for: idToken, refreshToken, expiresIn
     */
    @POST
    @Path("/token")
    JsonObject refreshToken(@QueryParam("key") String apiKey, JsonObject payload);

    /**
     * Change a user's email or password.
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth/#section-change-email">...</a>
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth/#section-change-password">...</a>
     *
     * @param apiKey Firebase project Web API key
     * @param requestBodyPayload Request Body Payload with properties for: idToken, email, returnSecureToken
     * @return Response Payload with properties for: localId, email, passwordHash, providerUserInfo, idToken, refreshToken, expiresIn
     */
    @POST
    @Path("/accounts:update")
    JsonObject update(@QueryParam("key") String apiKey, JsonObject requestBodyPayload);

    /**
     * Delete a current user.
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth/#section-delete-account">...</a>
     *
     * @param apiKey Firebase project Web API key
     * @param requestBodyPayload Request Body Payload with properties for: idToken
     */
    @POST
    @Path("/accounts:delete")
    void delete(@QueryParam("key") String apiKey, JsonObject requestBodyPayload);

    /**
     * Get a user's data.
     *
     * @link <a href="https://firebase.google.com/docs/reference/rest/auth#section-get-account-info">...</a>
     *
     * @param apiKey Firebase project Web API key
     * @param requestBodyPayload requestBodyPayload Request Body Payload with properties for: idToken
     * @return Response Payload with properties for: localId, email, emailVerified, displayName,
     * providerUserInfo, photoUrl, passwordHash, passwordUpdatedAt, valinceSInce, disabled,
     * lastLoginAt, createdAt, customAuth
     */
    @POST
    @Path("/accounts:lookup")
    JsonObject findById(@QueryParam("key") String apiKey, JsonObject requestBodyPayload);
}