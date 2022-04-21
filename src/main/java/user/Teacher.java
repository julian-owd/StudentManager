package user;

import course.Course;
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
public class Teacher extends User {

    private boolean isSick;
    private boolean isAdmin;

    public Teacher(int uID) {
        super(uID);
    }
}
