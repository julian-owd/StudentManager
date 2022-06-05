package gui;

import course.Course;
import course.Homework;
import manager.StudentManager;
import user.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeworkOverview {
    private JPanel panel1;
    private JButton zurückButton;
    private JList homeworkList;
    private DefaultListModel<String> homeworkModel;

    private HashMap<Integer, Homework> homeworkHashMap = new HashMap<>();

    /**
     * Opens the HomeworkOverview
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public HomeworkOverview(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Hausaufgaben - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        if (studentManager.getCurrentUser() instanceof Student student) {

            HashMap<Course, ArrayList<Homework>> homeworkByCourse = new HashMap<>();
            ArrayList<Homework> myHomework = studentManager.getMyHomework();

            // loading the list of undone homework
            for (Course course : studentManager.getMyCourses()) {
                ArrayList<Homework> homeworkList = new ArrayList<>();
                for (Homework h : myHomework) {
                    if (h.getEntry().getCourse().getCID() == course.getCID()) {
                        if (!student.getDoneHomework().contains(h)) {
                            homeworkList.add(h);
                        }
                    }
                }
                if (!homeworkList.isEmpty()) {
                    homeworkByCourse.put(course, homeworkList);
                }
            }

            if (homeworkByCourse.isEmpty()) {
                homeworkModel.addElement("Aktuell hast du keine Hausaufgaben zu erledigen.");
            } else {
                for (Course course : homeworkByCourse.keySet()) {
                    this.homeworkModel.addElement(course.getDesignation() + ":");
                    for (Homework homework : homeworkByCourse.get(course)) {
                        this.homeworkHashMap.put(this.homeworkModel.getSize(), homework);
                        this.homeworkModel.add(this.homeworkModel.getSize(), "  • " + homework.getDesignation());
                    }
                    this.homeworkModel.addElement("\n");
                }
            }

            // listener of the list
            this.homeworkList.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Homework homework = homeworkHashMap.get(homeworkList.getSelectedIndex());
                    if (homework != null) {
                        panel1.setVisible(false);
                        new HomeworkDetail(jFrame, studentManager, homework.getEntry(), null);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            // listener of the back button
            this.zurückButton.addActionListener(e -> {
                panel1.setVisible(false);
                new MainMenu(jFrame, studentManager);
            });
        }
    }

    /**
     * Custom create of UI components to modify them
     */
    private void createUIComponents() {
        this.homeworkModel = new DefaultListModel<>();
        this.homeworkList = new JList(this.homeworkModel);
    }

}
