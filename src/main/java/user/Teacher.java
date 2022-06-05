package user;

import course.Course;
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
public class Teacher extends User {

    private boolean isSick;
    private boolean isAdmin;

    /**
     * Creates a new teacher object
     *
     * @param uID the id of the user
     * @param studentManager an instance of studentManager
     */
    public Teacher(int uID, StudentManager studentManager) {
        super(uID, studentManager);

        // loads the data of the teacher
        HashMap<Integer, ArrayList<String>> teacherData = studentManager.getDatabase().getData("SELECT isSick, isAdmin FROM teacher WHERE uID=" + uID);
        if (!teacherData.isEmpty()) {
            this.isSick = teacherData.get(0).get(0).equals("1");
            this.isAdmin = teacherData.get(0).get(1).equals("1");
        }
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                "isSick=" + isSick +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
