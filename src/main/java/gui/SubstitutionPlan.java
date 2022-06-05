package gui;

import manager.StudentManager;
import user.Student;

import javax.swing.*;
import java.awt.*;

public class SubstitutionPlan {
    private JPanel panel1;
    private JButton zurückButton;
    private JList substitutionList;
    private DefaultListModel<String> substitutionModel;

    /**
     * Opens the SubstitutionPlan view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public SubstitutionPlan(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Vertretungsplan - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        // loads the substitution plan
        if (studentManager.getCurrentUser() instanceof Student) {
            if (studentManager.getSubstitutionPlan().isEmpty()) {
                this.substitutionModel.addElement("Alle deine Kurse finden voraussichtlich statt!");
            } else {
                for (String s : studentManager.getSubstitutionPlan()) {
                    this.substitutionModel.addElement("• " + s);
                }
            }
        }

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new MainMenu(jFrame, studentManager);
        });
    }

    /**
     * Custom create of UI components to modify them
     */
    private void createUIComponents() {
        this.substitutionModel = new DefaultListModel<>();
        this.substitutionList = new JList(this.substitutionModel);
    }

}
