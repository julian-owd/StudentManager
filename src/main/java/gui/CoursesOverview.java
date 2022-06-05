package gui;

import course.Course;
import manager.StudentManager;
import user.Student;
import user.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CoursesOverview {
    private JList courseList;
    private JPanel panel1;
    private JList teacherList;
    private JButton kursHinzufügenButton;
    private JButton zurückButton;
    private DefaultListModel<String> courseModel;
    private DefaultListModel<String> teacherModel;

    /**
     * Opens the CoursesOverview
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public CoursesOverview(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Meine Kurse - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        // only showing this buttons in case the current user is a teacher
        this.kursHinzufügenButton.setVisible(false);
        if (studentManager.getCurrentUser() instanceof Teacher) {
            this.kursHinzufügenButton.setVisible(true);
        }

        // filling the lists
        for (Course course : studentManager.getMyCourses()) {
            this.courseModel.addElement("• " + course.getDesignation());
            StringBuilder teachers = new StringBuilder();
            for (int i = 0; i < course.getTeachers().size(); i++) {
                if (i == course.getTeachers().size() - 1) {
                    teachers.append(course.getTeachers().get(i).getFirstName()).append(" ").append(course.getTeachers().get(i).getLastName());
                    this.teacherModel.addElement(teachers.toString());
                    break;
                }
                teachers.append(course.getTeachers().get(i).getFirstName()).append(" ").append(course.getTeachers().get(i).getLastName()).append(", ");
            }
        }

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new MainMenu(jFrame, studentManager);
        });

        // only registering the listener of the add course button in case it's visible
        if (this.kursHinzufügenButton.isVisible()) {
            this.kursHinzufügenButton.addActionListener(e -> {
                panel1.setVisible(false);
                new CreateCourse(jFrame, studentManager);
            });
        }

        // listener of the list of course designations
        this.courseList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel1.setVisible(false);
                if (studentManager.getCurrentUser() instanceof Student) {
                    new StudentCourseDetail(jFrame, studentManager, studentManager.findCourse(String.valueOf(courseList.getSelectedValue()).replace("• ", "")));
                } else {
                    new TeacherCourseDetail(jFrame, studentManager, studentManager.findCourse(String.valueOf(courseList.getSelectedValue()).replace("• ", "")));
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

    }

    /**
     * Custom create of UI components to modify them
     */
    private void createUIComponents() {
        this.courseModel = new DefaultListModel<>();
        this.teacherModel = new DefaultListModel<>();
        this.courseList = new JList(this.courseModel);
        this.teacherList = new JList<>(this.teacherModel);
    }

}
