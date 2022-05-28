package gui;

import course.Course;
import manager.StudentManager;
import user.Student;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AddExam {
    private JComboBox studentComboBox;
    private JSlider gradeSlider;
    private JButton hinzufügenButton;
    private JTextField designationField;
    private JLabel gradeLabel;
    private JPanel panel1;
    private JButton zurückButton;
    private JTextArea addedGrades;
    private JButton notenSpeichernButton;

    private HashMap<String, Student> studentHashMap;
    private HashMap<Student, Integer> gradeMap;

    public AddExam(JFrame jFrame, StudentManager studentManager, Course course) {
        // configuring the jFrame
        jFrame.setTitle(course.getDesignation() + " - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.studentHashMap = new HashMap<>();
        this.gradeMap = new HashMap<>();

        this.gradeLabel.setText(String.valueOf(this.gradeSlider.getValue()));

        for (int i = 0; i < course.getStudents().size(); i++) {
            this.studentHashMap.put(course.getStudents().get(i).getFirstName() + " " + course.getStudents().get(i).getLastName(), course.getStudents().get(i));
            this.studentComboBox.addItem(course.getStudents().get(i).getFirstName() + " " + course.getStudents().get(i).getLastName());
        }

        this.gradeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gradeLabel.setText(String.valueOf(gradeSlider.getValue()));
            }
        });

        this.hinzufügenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentComboBox.getSelectedIndex() > -1) {
                    Student student = studentHashMap.get(String.valueOf(studentComboBox.getSelectedItem()));
                    if (addedGrades.getText().isEmpty()) {
                        addedGrades.setText(student.getFirstName() + " " + student.getLastName() + ": " + gradeSlider.getValue());
                    } else {
                        addedGrades.setText(addedGrades.getText() + "\n" + student.getFirstName() + " " + student.getLastName() + ": " + gradeSlider.getValue());
                    }
                    gradeMap.put(student, gradeSlider.getValue());
                    studentComboBox.removeItemAt(studentComboBox.getSelectedIndex());
                }
            }
        });

        this.notenSpeichernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (designationField.getText().isEmpty()) {
                    studentManager.showErrorMessageDialog("Bitte gib eine Bezeichnung für die Noten ein!", jFrame);
                    return;
                }
                if (gradeMap.isEmpty()) {
                    studentManager.showErrorMessageDialog("Bitte trage mindestens eine Note ein!", jFrame);
                    return;
                }

                boolean error = false;
                int errorCount = 0;
                for (Student student : gradeMap.keySet()) {
                    if (studentManager.addExam(student, course, designationField.getText(), gradeMap.get(student)) == null) {
                        studentManager.showErrorMessageDialog("Die Note von " + student.getFirstName() + " " + student.getLastName() + " konnte nicht gespeichert werden!" , jFrame);
                        error = true;
                        errorCount++;
                    }
                }

                panel1.setVisible(false);
                new TeacherCourseDetail(jFrame, studentManager, course);
                if (error) {
                    studentManager.showErrorMessageDialog("Es sind  " + errorCount + " Fehler aufgetreten!", jFrame);
                }
                studentManager.showSuccessMessageDialog("Es wurden " + (gradeMap.size() - errorCount) + " Noten eingetragen.", jFrame);
            }
        });

        this.zurückButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new TeacherCourseDetail(jFrame, studentManager, course);
            }
        });
    }
}
