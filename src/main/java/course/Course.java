package course;

import user.Student;
import user.Teacher;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Course {
    private int cID;
    private String designation;
    private ArrayList<Exam> exams;
    private ArrayList<Homework> homework;
    private ArrayList<Weekday> weekdays;
    private ArrayList<Entry> entries;
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;

    public Course(int cID) {
        this.cID = cID;
        this.exams = new ArrayList<>();
        this.homework = new ArrayList<>();
        this.weekdays = new ArrayList<>();
        this.entries = new ArrayList<>();
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
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

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Course{" + "cID=" + cID + ", designation='" + designation + '\'' + ", exams=" + exams + ", homework=" + homework + ", weekdays=" + weekdays + ", entries=" + entries + ", students=" + students + ", teachers=" + teachers + '}';
    }
}
