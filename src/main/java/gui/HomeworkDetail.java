package gui;

import course.Course;
import course.Entry;
import course.Homework;
import manager.StudentManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class HomeworkDetail {
    private JLabel titelLabel;
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton hausaufgabeAlsErledigtMarkierenButton;
    private JButton zurückButton;

    /**
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     * @param entry          the entry the homework is attached to
     * @param course         the course of the entry, can also be null in case the menu was opened from the HomeworkOverview
     */
    public HomeworkDetail(JFrame jFrame, StudentManager studentManager, Entry entry, Course course) {
        // configuring jFrame
        jFrame.setTitle("Hausaufgabe vom " + entry.getDate() + " - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(400, 200));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(this.hausaufgabeAlsErledigtMarkierenButton);

        this.titelLabel.setText(this.titelLabel.getText() + " " + entry.getDate());

        this.textArea1.append(entry.getHomework().getDesignation());

        // listener of the button to mark a homework as finished
        this.hausaufgabeAlsErledigtMarkierenButton.addActionListener(e -> {
            studentManager.markHomeworkAsFinished(entry.getHomework());
            if (course != null) {
                panel1.setVisible(false);
                new StudentCourseDetail(jFrame, studentManager, course);
            } else {
                panel1.setVisible(false);
                new HomeworkOverview(jFrame, studentManager);
            }
        });

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            if (course != null) {
                panel1.setVisible(false);
                new StudentCourseDetail(jFrame, studentManager, course);
            } else {
                panel1.setVisible(false);
                new HomeworkOverview(jFrame, studentManager);
            }
        });
    }

}
