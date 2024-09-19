package dmit2015.restclient;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * This class contains the response body payload for sign in with firebase authentication
 */
@Data
public class FirebaseUser {

    private String localId;

    private String email;

    private String idToken;

    private String refreshToken;

    private String expiresIn;

    private LocalDateTime expiresInDateTime;

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
        int expiresInSeconds = Integer.parseInt(expiresIn);
        this.expiresInDateTime = LocalDateTime.now().plusSeconds(expiresInSeconds);
    }
}