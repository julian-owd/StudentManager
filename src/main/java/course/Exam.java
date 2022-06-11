package course;

import lombok.Getter;
import lombok.Setter;
import manager.StudentManager;
import user.Student;
import user.User;

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
    private Course course;
    private HashMap<Student, Integer> grades;

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
        this.grades = new HashMap<>();

        // loads the exam data
        HashMap<Integer, ArrayList<String>> examData = studentManager.getDatabase().getData("SELECT designation FROM exam WHERE eID=" + eID);
        if (!examData.isEmpty()) {
            this.designation = examData.get(0).get(0);
        }

        // loads the students of this exam
        HashMap<Integer, ArrayList<String>> studentData = studentManager.getDatabase().getData("SELECT uID, grade FROM student_exam WHERE eID=" + eID);
        for (Integer i : studentData.keySet()) {
            User student = studentManager.findUser(Integer.parseInt(studentData.get(i).get(0)));
            if (student instanceof Student) {
                this.grades.put((Student) student, Integer.parseInt(studentData.get(i).get(1)));
            }
        }
    }

    @Override
    public String toString() {
        return "Exam{" +
                "eID=" + eID +
                ", designation='" + designation + '\'' +
                ", student=" + grades +
                '}';
    }
}
