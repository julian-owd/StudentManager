package user;

import course.Course;
import course.Homework;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
@ToString
public class Student extends User {

    private ArrayList<Homework> doneHomework;

    public Student(int uID) {
        super(uID);
        this.doneHomework = new ArrayList<>();
    }
}
