package dmit2015.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Student {

    /** Firebase Realtime Database unique identifier for this data */
    private String name;

    /** First Name of student */
    @NotBlank(message = "First Name value is required.")
    private String firstName;

    /** Last Name of student */
    @NotBlank(message = "Last Name value is required.")
    private String lastName;

    /** Email address of student */
    @NotBlank(message = "Email address value is required.")
    @Email(message = "Email address provided is not a valid email pattern.")
    private String email;
}
