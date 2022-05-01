package course;

import lombok.Getter;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

@Getter
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

    /**
     * Compare this instance to another weekday
     *
     * @param weekday the weekday to compare
     * @return 0 = the days are the same, 1 = the next day, 2 = the day after tomorrow, ...
     */
    public int compare(Weekday weekday) {
        switch (this) {
            case MON:
                if (weekday == Weekday.MON) {
                    return 0;
                } else if (weekday == Weekday.TUE) {
                    return 1;
                } else if (weekday == Weekday.WED) {
                    return 2;
                } else if (weekday == Weekday.THR) {
                    return 3;
                } else if (weekday == Weekday.FRI) {
                    return 4;
                } else {
                    return -1;
                }
            case TUE:
                if (weekday == Weekday.MON) {
                    return 4;
                } else if (weekday == Weekday.TUE) {
                    return 0;
                } else if (weekday == Weekday.WED) {
                    return 1;
                } else if (weekday == Weekday.THR) {
                    return 2;
                } else if (weekday == Weekday.FRI) {
                    return 3;
                } else {
                    return -1;
                }
            case WED:
                if (weekday == Weekday.MON) {
                    return 3;
                } else if (weekday == Weekday.TUE) {
                    return 4;
                } else if (weekday == Weekday.WED) {
                    return 0;
                } else if (weekday == Weekday.THR) {
                    return 1;
                } else if (weekday == Weekday.FRI) {
                    return 2;
                } else {
                    return -1;
                }
            case THR:
                if (weekday == Weekday.MON) {
                    return 2;
                } else if (weekday == Weekday.TUE) {
                    return 3;
                } else if (weekday == Weekday.WED) {
                    return 4;
                } else if (weekday == Weekday.THR) {
                    return 0;
                } else if (weekday == Weekday.FRI) {
                    return 1;
                } else {
                    return -1;
                }
            case FRI:
                if (weekday == Weekday.MON) {
                    return 1;
                } else if (weekday == Weekday.TUE) {
                    return 2;
                } else if (weekday == Weekday.WED) {
                    return 3;
                }else if (weekday == Weekday.THR) {
                    return 4;
                } else if (weekday == Weekday.FRI) {
                    return 0;
                } else {
                    return -1;
                }
            default:
                return -1;
        }
    }
}
