package course;

import user.Student;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Homework {
    private int hID;
    private String designation;
    private Course course;

    public Homework(int hID, Course course) {
        this.hID = hID;
        this.course = course;
    }

    public int gethID() {
        return hID;
    }

    public void sethID(int hID) {
        this.hID = hID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
