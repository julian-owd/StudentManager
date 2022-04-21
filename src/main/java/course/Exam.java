package course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import user.Student;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
@ToString
public class Exam {

    private int eID;
    private String designation;
    private int grade;
    private Course course;
    private Student student;

    public Exam(int eID, Course course) {
        this.eID = eID;
        this.course = course;
    }
}
