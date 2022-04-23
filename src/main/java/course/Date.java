package course;

import lombok.AllArgsConstructor;

/**
 * @author Julian Oswald
 * @date 23.04.2022
 */
@AllArgsConstructor
public class Date {

    private int year;
    private int month;
    private int day;

    public Date(String date) {
        String[] splittedDate = date.split("-");
        year = Integer.parseInt(splittedDate[0]);
        month = Integer.parseInt(splittedDate[1]);
        day = Integer.parseInt(splittedDate[2]);
    }

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
