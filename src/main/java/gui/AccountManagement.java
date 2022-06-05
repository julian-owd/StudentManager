package gui;

import manager.StudentManager;
import user.Teacher;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

public class AccountManagement {
    private JButton eMailAdresseÄndernButton;
    private JButton passwortÄndernButton;
    private JCheckBox abwesendCheckBox;
    private JButton zurückButton;
    private JPanel panel1;

    /**
     * opens the AccountManagement view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public AccountManagement(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Accountverwaltung - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.abwesendCheckBox.setVisible(false);

        // only teachers may mark themselves as sick
        if (studentManager.getCurrentUser() instanceof Teacher t) {
            this.abwesendCheckBox.setVisible(true);
            if (t.isSick()) {
                this.abwesendCheckBox.setSelected(true);
            }
        }

        this.panel1.setVisible(true);

        // listener of back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new MainMenu(jFrame, studentManager);
        });

        // listener of change mail button
        this.eMailAdresseÄndernButton.addActionListener(e -> new ChangeMail(studentManager));

        // listener of change password button
        this.passwortÄndernButton.addActionListener(e -> new ChangePassword(studentManager));

        // only registering the listener of the checkbox if it's visible
        if (this.abwesendCheckBox.isVisible()) {
            this.abwesendCheckBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    studentManager.changeUserPresenceStatus(studentManager.getCurrentUser(), true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    studentManager.changeUserPresenceStatus(studentManager.getCurrentUser(), false);
                }
            });
        }
    }

}
