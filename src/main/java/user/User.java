package user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
@ToString
public abstract class User {

    private int uID;
    private String lastName;
    private String firstName;
    private String email;

    public User(int uID) {
        this.uID = uID;
    }
}
