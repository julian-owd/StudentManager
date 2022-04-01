package course;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Course {
    private int cID;
    private String designation;

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

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public ArrayList<Homework> getHomework() {
        return homework;
    }

    public void setHomework(ArrayList<Homework> homework) {
        this.homework = homework;
    }

    public ArrayList<Weekday> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(ArrayList<Weekday> weekdays) {
        this.weekdays = weekdays;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    private ArrayList<Exam> exams;
    private ArrayList<Homework> homework;
    private ArrayList<Weekday> weekdays;
    private ArrayList<Entry> entries;

    public Course(int cID) {
        this.cID = cID;

        exams = new ArrayList<>();
        homework = new ArrayList<>();
        weekdays = new ArrayList<>();
        entries = new ArrayList<>();
    }

}
