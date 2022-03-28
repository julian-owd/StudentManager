package course;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public enum Weekday {
    Monday,
    Thuesday,
    Wednesday,
    Thrusday,
    Friday;

    private int wID;
    private String designation;

    public int getwID() {
        return wID;
    }

    public void setwID(int wID) {
        this.wID = wID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
