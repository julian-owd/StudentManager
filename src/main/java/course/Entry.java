package course;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import user.Student;

import java.util.ArrayList;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
@ToString
public class Entry {

    private int eID;
    private Date date;
    private String title;
    private String designation;
    private Course course;
    private Homework homework;
    private ArrayList<Student> participants;

    public Entry(int eID, Course course) {
        this.eID = eID;
        this.course = course;
        this.participants = new ArrayList<>();
    }
}
