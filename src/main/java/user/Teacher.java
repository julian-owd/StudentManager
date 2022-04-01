package user;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Teacher extends User {
    private boolean isSick;
    private boolean isAdmin;

    public Teacher(int uID) {
        super(uID);
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    public boolean isSick(){
        return isSick;
    }

    public void setIsSick(boolean isSick){
        this.isSick = isSick;
    }
}
