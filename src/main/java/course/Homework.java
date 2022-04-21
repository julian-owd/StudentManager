package course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@Setter
@ToString
public class Homework {

    private int hID;
    private String designation;
    private Entry entry;

    public Homework(int hID, Entry entry) {
        this.hID = hID;
        this.entry = entry;
    }
}
