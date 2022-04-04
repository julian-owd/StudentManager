package course;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public enum Weekday {
    MON("Montag"),
    TUE("Dienstag"),
    WED("Mittwoch"),
    THR("Donnerstag"),
    FRI("Freitag");

    private String designation;

    Weekday(String designation){
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
