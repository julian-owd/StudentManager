package course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import user.Student;
import user.Teacher;

import java.util.ArrayList;

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

    public Course(int cID) {
        this.cID = cID;
        this.exams = new ArrayList<>();
        this.weekdays = new ArrayList<>();
        this.entries = new ArrayList<>();
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }
}
