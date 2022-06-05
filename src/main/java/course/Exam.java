package course;

import lombok.Getter;
import lombok.Setter;
import manager.StudentManager;
import user.Student;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
public class Exam {

    private int eID;
    private String designation;
    private int grade;
    private Course course;
    private Student student;

    /**
     * creates a new exam object
     *
     * @param eID            the id of the exam
     * @param course         the course of the exam
     * @param studentManager an instance of studentManager
     */
    public Exam(int eID, Course course, StudentManager studentManager) {
        this.eID = eID;
        this.course = course;

        // loads the exam data
        HashMap<Integer, ArrayList<String>> examData = studentManager.getDatabase().getData("SELECT designation, grade, uID FROM exam WHERE eID=" + eID);
        if (!examData.isEmpty()) {
            this.designation = examData.get(0).get(0);
            this.grade = Integer.parseInt(examData.get(0).get(1));
            this.student = (Student) studentManager.findUser(Integer.parseInt(examData.get(0).get(2)));
        }
    }

    @Override
    public String toString() {
        return "Exam{" +
                "eID=" + eID +
                ", designation='" + designation + '\'' +
                ", grade=" + grade +
                ", student=" + student +
                '}';
    }
}
