package course;

import java.util.Date;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Entry {
    private int eID;
    private Date date;
    private String title;
    private String designation;

    public Entry(int eID) {
        this.eID = eID;
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
