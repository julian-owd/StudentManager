package gui;

import manager.StudentManager;
import user.Teacher;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private JPanel panel1;
    private JButton meineKurseButton;
    private JButton accountButton;
    private JButton vertretungsplanButton;
    private JButton hausaufgabenButton;
    private JButton logoutButton;
    private JLabel accountLabel;
    private JButton adminButton;

    /**
     * Opens the MainMenu view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public MainMenu(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Übersicht - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.accountLabel.setText(studentManager.getCurrentUser().getFirstName() + " " + studentManager.getCurrentUser().getLastName());
        this.adminButton.setVisible(false);

        // modifying which buttons to show
        if (studentManager.getCurrentUser() instanceof Teacher t) {
            this.vertretungsplanButton.setVisible(false);
            this.hausaufgabenButton.setVisible(false);
            if (t.isAdmin()) {
                this.adminButton.setVisible(true);
            }
        }

        this.panel1.setVisible(true);

        // listener of the logout button
        this.logoutButton.addActionListener(e -> {
            panel1.setVisible(false);
            studentManager.logOut();
            new Login(jFrame, studentManager);
        });

        // listener of the courses button
        this.meineKurseButton.addActionListener(e -> {
            panel1.setVisible(false);
            new CoursesOverview(jFrame, studentManager);
        });

        // listener of the account button
        this.accountButton.addActionListener(e -> {
            panel1.setVisible(false);
            new AccountManagement(jFrame, studentManager);
        });

        // in case the button is visible, registering its listener
        if (this.vertretungsplanButton.isVisible()) {
            this.vertretungsplanButton.addActionListener(e -> {
                panel1.setVisible(false);
                new SubstitutionPlan(jFrame, studentManager);
            });
        }

        // in case the button is visible, registering its listener
        if (this.hausaufgabenButton.isVisible()) {
            this.hausaufgabenButton.addActionListener(e -> {
                panel1.setVisible(false);
                new HomeworkOverview(jFrame, studentManager);
            });
        }

        // in case the button is visible, registering its listener
        if (this.adminButton.isVisible()) {
            this.adminButton.addActionListener(e -> {
                panel1.setVisible(false);
                new AdminPanel(jFrame, studentManager);
            });
        }
    }

}
