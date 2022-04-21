package course;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
@ToString
public enum Weekday {

    MON("Montag"),
    TUE("Dienstag"),
    WED("Mittwoch"),
    THR("Donnerstag"),
    FRI("Freitag");

    private String designation;

    Weekday(String designation) {
        this.designation = designation;
    }
}
