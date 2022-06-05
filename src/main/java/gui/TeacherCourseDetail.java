package gui;

import course.Course;
import course.Entry;
import course.Exam;
import manager.StudentManager;
import user.Student;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class TeacherCourseDetail {
    private JPanel panel1;
    private JList nameList;
    private JList homeworkList;
    private JList examList;
    private JButton teilnehmerVerwaltenButton;
    private JButton eintragHinzufügenButton;
    private JButton noteHinzufügenButton;
    private JLabel kursbezeichnungLabel;
    private JButton zurückButton;
    private DefaultListModel<String> teilnehmerModel;
    private DefaultListModel<String> homeworkModel;
    private DefaultListModel<String> examModel;

    /**
     * Opens the CourseDetail view of a teacher
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     * @param course         the course to show the details from
     */
    public TeacherCourseDetail(JFrame jFrame, StudentManager studentManager, Course course) {
        // configuring the jFrame
        jFrame.setTitle(course.getDesignation() + " - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.kursbezeichnungLabel.setText(course.getDesignation());

        // loads the students of this course as well as their current average grade
        for (Student student : course.getStudents()) {
            this.teilnehmerModel.addElement("• " + student.getFirstName() + " " + student.getLastName());
            int doneHomework = student.getDoneHomework().stream().filter(c -> c.getEntry().getCourse().getCID() == course.getCID()).toList().size();
            int existingHomework = 0;
            for (Entry entry : course.getEntries()) {
                if (entry.getHomework() != null) {
                    existingHomework++;
                }
            }
            this.homeworkModel.addElement(doneHomework + " / " + existingHomework);

            double sumExams = 0;
            int numberExams = 0;
            for (Exam exam : course.getExams()) {
                if (exam.getStudent().getUID() == student.getUID()) {
                    sumExams += exam.getGrade();
                    numberExams++;
                }
            }
            if (numberExams == 0) {
                this.examModel.addElement("Bisher keine Noten");
                continue;
            }
            this.examModel.addElement(String.valueOf(Math.round(sumExams / numberExams * 100.0) / 100.0));
        }

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new CoursesOverview(jFrame, studentManager);
        });

        // listener of the add grade button
        this.noteHinzufügenButton.addActionListener(e -> {
            panel1.setVisible(false);
            new AddExam(jFrame, studentManager, course);
        });

        // listener of the add entry button
        this.eintragHinzufügenButton.addActionListener(e -> {
            panel1.setVisible(false);
            new AddEntry(jFrame, studentManager, course);
        });

        // listener of the manage participants button
        this.teilnehmerVerwaltenButton.addActionListener(e -> {
            panel1.setVisible(false);
            new ManageParticipants(jFrame, studentManager, course);
        });
    }

    /**
     * Custom create of UI components to modify them
     */
    private void createUIComponents() {
        this.teilnehmerModel = new DefaultListModel<>();
        this.homeworkModel = new DefaultListModel<>();
        this.examModel = new DefaultListModel<>();
        this.nameList = new JList(this.teilnehmerModel);
        this.homeworkList = new JList(this.homeworkModel);
        this.examList = new JList(this.examModel);
    }

}
