package user;

import course.Entry;
import course.Exam;
import course.Homework;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Student extends User {
    private ArrayList<Entry> participants;
    private Exam exam;
    private Homework homework;

    public Student(int uID) {
        super(uID);
    }

    public ArrayList<Entry> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Entry> participants) {
        this.participants = participants;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}
