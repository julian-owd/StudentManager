package course;

import java.util.Date;

import user.Student;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Entry {
    private int eID;
    private Date date;
    private String title;
    private String designation;
    private Course course;
    private ArrayList<Student> participants;

    public Entry(int eID, Course course) {
        this.eID = eID;
        this.course = course;
        this.participants = new ArrayList<>();
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public ArrayList<Student> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Student> participants) {
        this.participants = participants;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
