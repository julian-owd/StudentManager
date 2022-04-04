package user;

import course.Homework;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Student extends User {
    private ArrayList<Homework> doneHomework;

    public Student(int uID) {
        super(uID);
    }

    public ArrayList<Homework> getDoneHomework() {
        return doneHomework;
    }

    public void setDoneHomework(ArrayList<Homework> doneHomework) {
        this.doneHomework = doneHomework;
    }
}
