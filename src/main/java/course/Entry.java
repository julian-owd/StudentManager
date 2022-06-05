package course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public class Entry {

    private int eID;
    private Date date;
    private String title;
    private String designation;
    private Course course;
    private Homework homework;
    private ArrayList<Student> participants;

    /**
     * creates a new entry object
     *
     * @param eID the id of the entry
     * @param course the course of the entry
     * @param studentManager an instance of studentManager
     */
    public Entry(int eID, Course course, StudentManager studentManager) {
        this.eID = eID;
        this.course = course;
        this.participants = new ArrayList<>();

        // loads the data for this specific entry
        HashMap<Integer, ArrayList<String>> entryData = studentManager.getDatabase().getData("SELECT date, title, description FROM entry WHERE eID=" + eID);
        this.date = new Date(entryData.get(0).get(0));
        this.title = entryData.get(0).get(1);
        this.designation = entryData.get(0).get(2);

        // loads homework, if existing
        HashMap<Integer, ArrayList<String>> homeworkData = studentManager.getDatabase().getData("SELECT hID FROM homework WHERE eID=" + eID);
        if (!homeworkData.isEmpty()) {
            this.homework = new Homework(Integer.parseInt(homeworkData.get(0).get(0)), this, studentManager);
        }

        // loads the participants for this entry
        HashMap<Integer, ArrayList<String>> studentData = studentManager.getDatabase().getData("SELECT uID FROM student_entry WHERE eID=" + eID);
        for (Integer i : studentData.keySet()) {
            User student = studentManager.findUser(Integer.parseInt(studentData.get(i).get(0)));
            if (student instanceof Student) {
                this.participants.add((Student) student);
            }
        }
    }

    @Override
    public String toString() {
        return "Entry{" +
                "eID=" + eID +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", designation='" + designation + '\'' +
                ", homework=" + homework +
                ", participants=" + participants +
                '}';
    }
}
