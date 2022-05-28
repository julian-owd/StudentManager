package gui;

import manager.StudentManager;
import user.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JPanel panel1;
    private JButton meineKurseButton;
    private JButton accountButton;
    private JButton vertretungsplanButton;
    private JButton hausaufgabenButton;
    private JButton logoutButton;
    private JLabel accountLabel;
    private JButton adminButton;

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

        if (studentManager.getCurrentUser() instanceof Teacher t) {
            this.vertretungsplanButton.setVisible(false);
            this.hausaufgabenButton.setVisible(false);
            if (t.isAdmin()) {
                this.adminButton.setVisible(true);
            }
        }

        this.panel1.setVisible(true);

        this.logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                studentManager.logOut();
                new Login(jFrame, studentManager);
            }
        });

        this.meineKurseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new CoursesOverview(jFrame, studentManager);
            }
        });

        this.accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new AccountManagement(jFrame, studentManager);
            }
        });

        if (this.vertretungsplanButton.isVisible()) {
            this.vertretungsplanButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel1.setVisible(false);
                    new SubstitutionPlan(jFrame, studentManager);
                }
            });
        }

        if (this.hausaufgabenButton.isVisible()) {
            this.hausaufgabenButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel1.setVisible(false);
                    new HomeworkOverview(jFrame, studentManager);
                }
            });
        }

        if (this.adminButton.isVisible()) {
            this.adminButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel1.setVisible(false);
                    new AdminArea(jFrame, studentManager);
                }
            });
        }
    }
}
