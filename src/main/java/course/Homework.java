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

    public Homework(int hID, Entry entry, StudentManager studentManager) {
        this.hID = hID;
        this.entry = entry;

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
