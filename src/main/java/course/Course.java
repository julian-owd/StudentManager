package course;

import java.util.ArrayList;

public class Course {
    private int cID;
    private String designation;
    private ArrayList<Exam> exams;
    private ArrayList<Homework> homeworks;
    private ArrayList<Weekday> weekdays;
    private ArrayList<Entry> entries;

    public Course(int cID) {
        this.cID = cID;

        exams = new ArrayList<>();
        homeworks = new ArrayList<>();
        weekdays = new ArrayList<>();
        entries = new ArrayList<>();
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
