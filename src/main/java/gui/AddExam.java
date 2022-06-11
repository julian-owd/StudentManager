package gui;

import course.Course;
import manager.StudentManager;
import user.Student;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    private final HashMap<String, Student> studentHashMap;
    private final HashMap<Student, Integer> gradeMap;

    /**
     * Opens the AddExam view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     * @param course         the course to add an exam to
     */
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

        ArrayList<Student> students = course.getStudents().stream().sorted(User::compareTo).collect(Collectors.toCollection(ArrayList::new));

        // filling the combobox with the students of this course
        for (int i = 0; i < students.size(); i++) {
            this.studentHashMap.put(students.get(i).getFirstName() + " " + students.get(i).getLastName(), students.get(i));
            this.studentComboBox.addItem(students.get(i).getFirstName() + " " + students.get(i).getLastName());
        }

        // listener of the slider
        this.gradeSlider.addChangeListener(e -> gradeLabel.setText(String.valueOf(gradeSlider.getValue())));

        // listener of the add button for a single grade
        this.hinzufügenButton.addActionListener(e -> {
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
        });

        // listener of the save button of all grades
        this.notenSpeichernButton.addActionListener(e -> {
            if (designationField.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib eine Bezeichnung für die Noten ein!", jFrame);
                return;
            }
            if (designationField.getText().length() > 200) {
                studentManager.showErrorMessageDialog("Die Bezeichnung darf nicht länger als 200 Zeichen sein!", jFrame);
                return;
            }
            if (gradeMap.isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte trage mindestens eine Note ein!", jFrame);
                return;
            }

            if (studentManager.addExam(course, designationField.getText(), gradeMap) != null) {
                studentManager.showSuccessMessageDialog("Die Noten wurden gespeichert.", jFrame);
            } else {
                studentManager.showErrorMessageDialog("Es konnten eventuell nicht alle Noten gespeichert werden!", jFrame);
            }

            panel1.setVisible(false);
            new TeacherCourseDetail(jFrame, studentManager, course);
        });

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new TeacherCourseDetail(jFrame, studentManager, course);
        });
    }

}
