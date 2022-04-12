package user;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public abstract class User {
    private int uID;
    private String lastName;
    private String firstName;
    private String email;

    public User(int uID) {
        this.uID = uID;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "uID=" + uID + ", lastName='" + lastName + '\'' + ", firstName='" + firstName + '\'' + ", email='" + email + '\'' + '}';
    }
}
