package course;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Exam {
    private int eID;
    private String designation;
    private int grade;
    private Course course;

    public Exam(int eID) {
        this.eID = eID;
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
