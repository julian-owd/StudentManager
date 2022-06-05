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
    private String password;

    /**
     * @param uID            the id of the user
     * @param studentManager an instance of studentManager
     */
    public User(int uID, StudentManager studentManager) {
        this.uID = uID;

        // loads the data of the user
        HashMap<Integer, ArrayList<String>> userData = studentManager.getDatabase().getData("SELECT lastName, firstName, email, password FROM user WHERE uID=" + uID);
        if (!userData.isEmpty()) {
            this.lastName = userData.get(0).get(0);
            this.firstName = userData.get(0).get(1);
            this.email = userData.get(0).get(2);
            this.password = userData.get(0).get(3);
        }
    }

    /**
     * Compares the name of this user with another name. Useful to sort an ArrayList
     *
     * @param user the user to compare this object to
     * @return is < 0 if the name alphabetically precedes the other user's name and > 0 if the name alphabetically follows the other user's name
     */
    public int compareTo(User user) {
        String name1 = this.firstName + " " + this.lastName;
        String name2 = user.getFirstName() + " " + user.getLastName();
        return name1.compareTo(name2);
    }
}
