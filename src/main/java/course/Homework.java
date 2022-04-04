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
    private ArrayList<Student> students;

    public Homework(int hID) {
        this.hID = hID;
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
