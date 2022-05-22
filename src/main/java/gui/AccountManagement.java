package gui;

import manager.StudentManager;
import user.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AccountManagement {
    private JButton eMailAdresseÄndernButton;
    private JButton passwortÄndernButton;
    private JCheckBox abwesendCheckBox;
    private JButton zurückButton;
    private JPanel panel1;

    public AccountManagement(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Accountverwaltung - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.abwesendCheckBox.setVisible(false);

        if (studentManager.getCurrentUser() instanceof Teacher t) {
            this.abwesendCheckBox.setVisible(true);
            if (t.isSick()) {
                this.abwesendCheckBox.setSelected(true);
            }
        }

        this.panel1.setVisible(true);

        this.zurückButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new MainMenu(jFrame, studentManager);
            }
        });

        this.eMailAdresseÄndernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeMail(studentManager);
            }
        });

        this.passwortÄndernButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassword(studentManager);
            }
        });

        this.abwesendCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    studentManager.changeUserPresenceStatus(studentManager.getCurrentUser(), true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    studentManager.changeUserPresenceStatus(studentManager.getCurrentUser(), false);
                }
            }
        });
    }
}
