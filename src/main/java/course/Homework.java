package course;

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
public class Homework {

    private int hID;
    private String designation;
    private Entry entry;

    /**
     * creates a new homework object
     *
     * @param hID the id of the homework
     * @param entry the entry of the homework
     * @param studentManager an instance of studentManager
     */
    public Homework(int hID, Entry entry, StudentManager studentManager) {
        this.hID = hID;
        this.entry = entry;

        // loads the data of this specific homework
        HashMap<Integer, ArrayList<String>> homeworkData = studentManager.getDatabase().getData("SELECT designation FROM homework WHERE hID=" + hID);
        if (!homeworkData.isEmpty()) {
            this.designation = homeworkData.get(0).get(0);
        }
    }

    @Override
    public String toString() {
        return "Homework{" +
                "hID=" + hID +
                ", designation='" + designation + '\'' +
                '}';
    }
}
