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
    private ArrayList<Student> participants;

    public Entry(int eID) {
        this.eID = eID;
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
}
