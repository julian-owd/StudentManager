package user;

import course.Course;
import course.Homework;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    public Student(int uID, StudentManager studentManager) {
        super(uID, studentManager);
        this.doneHomework = new ArrayList<>();

        HashMap<Integer, ArrayList<String>> homeworkData = studentManager.getDatabase().getData("SELECT hID FROM student_homework WHERE uID=" + uID);
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
