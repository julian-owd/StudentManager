package course;

import lombok.AllArgsConstructor;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * @author Julian Oswald
 * @date 23.04.2022
 */
@AllArgsConstructor
public class Date {

    private int year;
    private int month;
    private int day;

    /**
     * creates a new date object
     *
     * @param date the string to convert into a Date object
     */
    public Date(String date) {
        if (date.contains("-")) { // sql syntax
            String[] splittedDate = date.split("-");
            year = Integer.parseInt(splittedDate[0]);
            month = Integer.parseInt(splittedDate[1]);
            day = Integer.parseInt(splittedDate[2]);
        } else if (date.contains(".")) { // german syntax
            String[] splittedDate = date.split("\\.");
            year = Integer.parseInt(splittedDate[2]);
            month = Integer.parseInt(splittedDate[1]);
            day = Integer.parseInt(splittedDate[0]);
        }

    }

    /**
     * Generates an object of Date with the current date
     *
     * @return date object
     */
    public static Date getCurrentDate() {
        return new Date(DateFormat.getDateInstance(DateFormat.MEDIUM).format(new GregorianCalendar().getTime()));
    }

    /**
     * Converts the date into a sql suitable string
     *
     * @return the date in its new format
     */
    public String toSQLString() {
        StringBuilder builder = new StringBuilder();

        if (month < 10) {
            builder.append(year).append("-").append(0).append(month).append("-");
        } else {
            builder.append(year).append("-").append(month).append("-");
        }
        if (day < 10) {
            builder.append(0).append(day);
        } else {
            builder.append(day);
        }

        return builder.toString();
    }

    /**
     * Converts the date into a string with german syntax
     *
     * @return the date in its new format
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (day < 10) {
            builder.append(0).append(day).append(".");
        } else {
            builder.append(day).append(".");
        }
        if (month < 10) {
            builder.append(0).append(month).append(".").append(year);
        } else {
            builder.append(month).append(".").append(year);
        }
        return builder.toString();
    }
}
