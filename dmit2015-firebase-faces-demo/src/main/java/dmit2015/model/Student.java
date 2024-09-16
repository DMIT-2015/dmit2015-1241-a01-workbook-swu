package dmit2015.model;

import lombok.Data;

@Data
public class Student {

    /** Firebase Realtime Database unique identifier for this data */
    private String name;

    /** First Name of student */
    private String firstName;

    /** Last Name of student */
    private String lastName;

    /** Email address of student */
    private String email;
}
