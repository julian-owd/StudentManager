package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;

public class AdminPanel {
    private JPanel panel1;
    private JButton nutzerEntfernenButton;
    private JButton nutzerHinzufügenButton;
    private JButton passwortZurücksetzenButton;
    private JButton zurückButton;

    /**
     * Opens the AdminPanel view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public AdminPanel(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Adminpanel - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(600, 300));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        // listener of the back button
        this.zurückButton.addActionListener(e -> {
            panel1.setVisible(false);
            new MainMenu(jFrame, studentManager);
        });

        // listener of the add user button
        this.nutzerHinzufügenButton.addActionListener(e -> new AddUser(studentManager));

        // listener of the remove user button
        this.nutzerEntfernenButton.addActionListener(e -> new RemoveUser(studentManager));

        // listener of the reset password button
        this.passwortZurücksetzenButton.addActionListener(e -> new ResetPassword(studentManager));
    }

}
