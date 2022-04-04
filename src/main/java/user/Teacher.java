package user;

import course.Course;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Teacher extends User {
    private boolean isSick;
    private boolean isAdmin;
    private ArrayList<Course> courses;

    public Teacher(int uID) {
        super(uID);
        this.courses = new ArrayList<>();
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

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
