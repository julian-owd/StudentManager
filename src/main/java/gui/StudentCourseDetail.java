package gui;

import course.Course;
import course.Entry;
import course.Exam;
import manager.StudentManager;
import user.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StudentCourseDetail {

    private JPanel panel1;
    private JLabel kursbezeichnungLabel;
    private JLabel lehrkraftLabel;
    private JButton zurückButton;
    private JList dateList;
    private JList topicList;
    private JList presenceStatusList;
    private JList gradeList;
    private DefaultListModel<String> dateModel;
    private DefaultListModel<String> topicModel;
    private DefaultListModel<String> presenceModel;
    private DefaultListModel<String> gradeModel;

    /**
     * Opens the CourseDetail view of a student
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     * @param course         the course to show the details from
     */
    public StudentCourseDetail(JFrame jFrame, StudentManager studentManager, Course course) {
        // configuring the jFrame
        jFrame.setTitle(course.getDesignation() + " - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.kursbezeichnungLabel.setText(course.getDesignation());

        // loads the teachers
        StringBuilder teachers = new StringBuilder();
        for (int i = 0; i < course.getTeachers().size(); i++) {
            if (i == course.getTeachers().size() - 1) {
                teachers.append(course.getTeachers().get(i).getFirstName()).append(" ").append(course.getTeachers().get(i).getLastName());
                break;
            }
            teachers.append(course.getTeachers().get(i).getFirstName()).append(" ").append(course.getTeachers().get(i).getLastName()).append(", ");
        }
        this.lehrkraftLabel.setText(this.lehrkraftLabel.getText() + teachers);

        // loads the existing entries
        for (Entry entry : course.getEntries()) {
            Student student = (Student) studentManager.getCurrentUser();
            if (entry.getHomework() != null) {
                if (!student.getDoneHomework().contains(entry.getHomework())) {
                    this.dateModel.addElement(entry.getDate().toString() + " (Hausaufgabe verfügbar)");
                } else {
                    this.dateModel.addElement(entry.getDate().toString());
                }
            } else {
                this.dateModel.addElement(entry.getDate().toString());
            }

            this.topicModel.addElement(entry.getDesignation());

            if (entry.getParticipants().contains(student)) {
                this.presenceModel.addElement("Anwesend");
            } else {
                this.presenceModel.addElement("Nicht anwesend");
            }
        }

        // loads the existing exams
        for (Exam exam : course.getExams()) {
            if (exam.getStudent().equals(studentManager.getCurrentUser())) {
                this.gradeModel.addElement("• " + exam.getDesignation() + ": " + exam.getGrade() + " NP");
            }
        }

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new CoursesOverview(jFrame, studentManager);
        });

        // listener of the date list to show a specific homework
        this.dateList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = dateList.getSelectedIndex();
                if (!String.valueOf(dateList.getSelectedValue()).contains("(Hausaufgabe verfügbar)")) {
                    return;
                }
                String dateString = String.valueOf(dateList.getSelectedValue()).replace(" (Hausaufgabe verfügbar)", "");
                String designation = topicModel.getElementAt(selectedIndex);
                Entry entry = null;
                for (Entry ent : course.getEntries()) {
                    if (ent.getDate().toString().equals(dateString) && designation.equalsIgnoreCase(ent.getDesignation())) {
                        entry = ent;
                        break;
                    }
                }
                if (entry == null) {
                    studentManager.showErrorMessageDialog("Es gab einen Fehler beim Laden der Hausaufgabe", jFrame);
                    return;
                }
                new HomeworkDetail(jFrame, studentManager, entry, course);
                panel1.setVisible(false);
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
        this.dateModel = new DefaultListModel<>();
        this.topicModel = new DefaultListModel<>();
        this.presenceModel = new DefaultListModel<>();
        this.gradeModel = new DefaultListModel<>();
        this.dateList = new JList(this.dateModel);
        this.topicList = new JList<>(this.topicModel);
        this.presenceStatusList = new JList<>(this.presenceModel);
        this.gradeList = new JList<>(this.gradeModel);
    }

}
