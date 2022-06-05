package course;

import manager.SQLManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import manager.StudentManager;
import user.Student;
import user.Teacher;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
@ToString
public class Course {

    private int cID;
    private String designation;
    private ArrayList<Exam> exams;
    private ArrayList<Weekday> weekdays;
    private ArrayList<Entry> entries;
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;

    /**
     * creates a new course object
     *
     * @param cID the id of the Course
     * @param studentManager an instance of studentManager
     */
    public Course(int cID, StudentManager studentManager) {
        this.cID = cID;
        this.exams = new ArrayList<>();
        this.weekdays = new ArrayList<>();
        this.entries = new ArrayList<>();
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();

        // loads the designation
        HashMap<Integer, ArrayList<String>> courseData = studentManager.getDatabase().getData("SELECT designation FROM course WHERE cID=" + cID);
        this.designation = courseData.get(0).get(0);

        // loads the exams of the course
        HashMap<Integer, ArrayList<String>> examData = studentManager.getDatabase().getData("SELECT eID FROM exam WHERE cID=" + cID);
        for (Integer i : examData.keySet()) {
            this.exams.add(new Exam(Integer.parseInt(examData.get(i).get(0)), this, studentManager));
        }

        // loads the weekdays the course takes place
        HashMap<Integer, ArrayList<String>> weekdayData = studentManager.getDatabase().getData("SELECT weekday FROM course_weekday WHERE cID=" + cID);
        for (Integer i : weekdayData.keySet()) {
            switch (Integer.parseInt(weekdayData.get(i).get(0))) {
                case 1 -> this.weekdays.add(Weekday.MON);
                case 2 -> this.weekdays.add(Weekday.TUE);
                case 3 -> this.weekdays.add(Weekday.WED);
                case 4 -> this.weekdays.add(Weekday.THR);
                case 5 -> this.weekdays.add(Weekday.FRI);
                default -> System.out.println("Fehlerhafter Wochentag für cID=" + cID);
            }
        }

        // loads the entries of the course
        HashMap<Integer, ArrayList<String>> entryData = studentManager.getDatabase().getData("SELECT eID FROM entry WHERE cID=" + cID);
        for (Integer i : entryData.keySet()) {
            this.entries.add(new Entry(Integer.parseInt(entryData.get(i).get(0)), this, studentManager));
        }

        // loads the students of the course
        HashMap<Integer, ArrayList<String>> studentData = studentManager.getDatabase().getData("SELECT uID FROM student_course WHERE cID=" + cID);
        for (Integer i : studentData.keySet()) {
            User student = studentManager.findUser(Integer.parseInt(studentData.get(i).get(0)));
            if (student instanceof Student) {
                this.students.add((Student) student);
            }
        }

        // loads the teachers of the course
        HashMap<Integer, ArrayList<String>> teacherData = studentManager.getDatabase().getData("SELECT uID FROM teacher_course WHERE cID=" + cID);
        for (Integer i : teacherData.keySet()) {
            User teacher = studentManager.findUser(Integer.parseInt(teacherData.get(i).get(0)));
            if (teacher instanceof Teacher) {
                this.teachers.add((Teacher) teacher);
            }
        }
    }
}
