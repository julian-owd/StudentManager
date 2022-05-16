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
    private JButton button1; // Vertretungsplan & Account hinfzufügen
    private JButton button2; // Hausaufgaben & Account löschen
    private JButton logoutButton;
    private JLabel accountLabel;
    private JButton hausaufgabeHinzufügenButton;

    public MainMenu(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Übersicht - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.accountLabel.setText(studentManager.getCurrentUser().getFirstName() + " " + studentManager.getCurrentUser().getLastName());
        this.hausaufgabeHinzufügenButton.setVisible(false);

        if (studentManager.getCurrentUser() instanceof Teacher t) {
            this.hausaufgabeHinzufügenButton.setVisible(true);
            if (t.isAdmin()) {
                button1.setText("Account hinzufügen");
                button2.setText("Account löschen");
            } else {
                button1.setVisible(false);
                button2.setVisible(false);
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

        this.accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new AccountManagement(jFrame, studentManager);
            }
        });
    }
}
