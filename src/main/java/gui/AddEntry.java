package gui;

import course.Course;
import course.Date;
import course.Entry;
import manager.StudentManager;
import user.Student;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class AddEntry {
    private JPanel panel1;
    private JTextField titleField;
    private JComboBox participantsComboBox;
    private JTextArea participantsTextArea;
    private JButton anwesendButton;
    private JButton zurückButton;
    private JButton speichernButton;
    private JTextField homeworkField;
    private JCheckBox hausaufgabeHinzufügenCheckBox;
    private JLabel homeworkLabel;
    private JTextArea designationTextArea;

    private HashMap<String, Student> studentHashMap;
    private ArrayList<Student> participants;

    /**
     * Opens the AddEntry view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     * @param course         the course to add an entry to
     */
    public AddEntry(JFrame jFrame, StudentManager studentManager, Course course) {
        // configuring the jFrame
        jFrame.setTitle(course.getDesignation() + " - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.participants = new ArrayList<>();
        this.studentHashMap = new HashMap<>();

        this.homeworkField.setVisible(false);
        this.homeworkLabel.setVisible(false);

        // listener of the checkbox to show options for adding a homework
        this.hausaufgabeHinzufügenCheckBox.addItemListener(e -> {
            if (hausaufgabeHinzufügenCheckBox.isSelected()) {
                hausaufgabeHinzufügenCheckBox.setVisible(false);
                homeworkField.setVisible(true);
                homeworkLabel.setVisible(true);
            }
        });

        // filling the combobox with students
        for (int i = 0; i < course.getStudents().size(); i++) {
            this.studentHashMap.put(course.getStudents().get(i).getFirstName() + " " + course.getStudents().get(i).getLastName(), course.getStudents().get(i));
            this.participantsComboBox.addItem(course.getStudents().get(i).getFirstName() + " " + course.getStudents().get(i).getLastName());
        }

        // listener of the button to add participants
        this.anwesendButton.addActionListener(e -> {
            if (participantsComboBox.getSelectedIndex() > -1) {
                Student student = studentHashMap.get(String.valueOf(participantsComboBox.getSelectedItem()));
                if (participantsTextArea.getText().isEmpty()) {
                    participantsTextArea.setText("• " + student.getFirstName() + " " + student.getLastName());
                } else {
                    participantsTextArea.setText(participantsTextArea.getText() + "\n• " + student.getFirstName() + " " + student.getLastName());
                }
                participants.add(student);
                participantsComboBox.removeItemAt(participantsComboBox.getSelectedIndex());
            }
        });

        // listener of the save button
        this.speichernButton.addActionListener(e -> {
            if (titleField.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib eine Überschrift für die Stunde an!", jFrame);
                return;
            }
            if (titleField.getText().length() > 100) {
                studentManager.showErrorMessageDialog("Der Titel darf nicht länger als 100 Zeichen sein!", jFrame);
                return;
            }
            if (designationTextArea.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib eine Beschreibung für die Stunde an!", jFrame);
                return;
            }
            if (designationTextArea.getText().length() > 5000) {
                studentManager.showErrorMessageDialog("Die Beschreibung darf nicht länger als 5000 Zeichen sein!", jFrame);
                return;
            }
            if (homeworkField.getText().length() > 500) {
                studentManager.showErrorMessageDialog("Der Hausaufgabentext darf nicht länger als 500 Zeichen sein!", jFrame);
                return;
            }
            Entry entry = studentManager.addEntry(course, Date.getCurrentDate(), titleField.getText(), designationTextArea.getText(), participants);
            if (entry != null) {
                // checking if a homework was added
                if (!homeworkField.getText().isEmpty()) {
                    // if saving the homework didn't work
                    if (studentManager.addHomework(entry, homeworkField.getText()) == null) {
                        panel1.setVisible(false);
                        new TeacherCourseDetail(jFrame, studentManager, course);
                        studentManager.showErrorMessageDialog("Der Eintrag wurde gespeichert, die Hausaufgabe jedoch nicht!", jFrame);
                        return;
                    }
                }
                panel1.setVisible(false);
                new TeacherCourseDetail(jFrame, studentManager, course);
                studentManager.showSuccessMessageDialog("Der Eintrag wurde gespeichert.", jFrame);
                return;
            }
            panel1.setVisible(false);
            new TeacherCourseDetail(jFrame, studentManager, course);
            studentManager.showErrorMessageDialog("Der Eintrag konnte nicht gespeichert werden!", jFrame);
        });

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new TeacherCourseDetail(jFrame, studentManager, course);
        });
    }

}
