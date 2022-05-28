package gui;

import course.Course;
import course.Entry;
import course.Homework;
import manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeworkDetail {
    private JLabel titelLabel;
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton hausaufgabeAlsErledigtMarkierenButton;
    private JButton zurückButton;

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

        this.hausaufgabeAlsErledigtMarkierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentManager.markHomeworkAsFinished(entry.getHomework());
                if (course != null) {
                    panel1.setVisible(false);
                    new StudentCourseDetail(jFrame, studentManager, course);
                } else {
                    panel1.setVisible(false);
                    new HomeworkOverview(jFrame, studentManager);
                }
            }
        });

        this.zurückButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (course != null) {
                    panel1.setVisible(false);
                    new StudentCourseDetail(jFrame, studentManager, course);
                } else {
                    panel1.setVisible(false);
                    new HomeworkOverview(jFrame, studentManager);
                }
            }
        });
    }
}
