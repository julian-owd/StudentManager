package user;

import course.Course;
import course.Homework;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Student extends User {
    private ArrayList<Homework> doneHomework;
    private ArrayList<Course> courses;

    public Student(int uID) {
        super(uID);
        this.doneHomework = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public ArrayList<Homework> getDoneHomework() {
        return doneHomework;
    }

    public void setDoneHomework(ArrayList<Homework> doneHomework) {
        this.doneHomework = doneHomework;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
