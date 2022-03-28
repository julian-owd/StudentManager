package user;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Teacher extends User {
    private Boolean isSick;
    private Boolean isAdmin;


    public Teacher(int uID) {
        super(uID);
    }

    public Boolean getSick() {
        return isSick;
    }

    public void setSick(Boolean sick) {
        isSick = sick;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
