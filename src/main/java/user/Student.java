package user;

import course.Homework;
import lombok.Getter;
import lombok.Setter;
import manager.StudentManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
public class Student extends User {

    private ArrayList<Homework> doneHomework;

    /**
     * Creates a new student object
     *
     * @param uID            the id of the user
     * @param studentManager an instance of studentManager
     */
    public Student(int uID, StudentManager studentManager) {
        super(uID, studentManager);
        this.doneHomework = new ArrayList<>();
    }

    /**
     * Load the homework which is already done by the student
     *
     * @param studentManager instance of StudentManager for database connection
     */
    public void loadHomework(StudentManager studentManager) {
        this.doneHomework = new ArrayList<>();

        HashMap<Integer, ArrayList<String>> homeworkData = studentManager.getDatabase().getData("SELECT hID FROM student_homework WHERE uID=" + this.getUID());
        for (Integer i : homeworkData.keySet()) {
            this.doneHomework.add(studentManager.findHomework(Integer.parseInt(homeworkData.get(i).get(0))));
        }
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() +
                "doneHomework=" + doneHomework +
                '}';
    }
}
