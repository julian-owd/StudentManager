package user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import manager.StudentManager;

import java.util.ArrayList;
import java.util.HashMap;

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

    public User(int uID, StudentManager studentManager) {
        this.uID = uID;

        HashMap<Integer, ArrayList<String>> userData = studentManager.getDatabase().getData("SELECT lastName, firstName, email FROM user WHERE uID=" + uID);
        if (!userData.isEmpty()) {
            this.lastName = userData.get(0).get(0);
            this.firstName = userData.get(0).get(1);
            this.email = userData.get(0).get(2);
        }
    }
}
