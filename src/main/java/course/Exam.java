package course;

import user.Student;

/**
 * @author Elias Paul
 * @date 28.03.2022
 */

public class Exam {
    private int eID;
    private String designation;
    private int grade;
    private Course course;
    private Student student;

    public Exam(int eID, Course course) {
        this.eID = eID;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
