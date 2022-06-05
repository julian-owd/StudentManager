package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;

public class Login {
    private JPanel panel1;
    private JTextField emailField;
    private JPasswordField passwortField;
    private JButton loginButton;

    /**
     * Opens the Login view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public Login(JFrame jFrame, StudentManager studentManager) {
        // configuring jFrame
        jFrame.setTitle("Login - Schulportal");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(500, 200));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(loginButton);

        if (!studentManager.getDatabase().isConnected()) {
            this.loginButton.setEnabled(false);
        }

        // listener of the login button
        loginButton.addActionListener(e -> {
            // checking if the user filled out every field
            if (emailField.getText().length() == 0 || passwortField.getPassword().length == 0) {
                studentManager.showErrorMessageDialog("Bitte gib eine E-Mail-Adresse und ein Passwort ein!", jFrame);
                return;
            }

            // checking if we can log in the user
            if (studentManager.logIn(emailField.getText(), String.copyValueOf(passwortField.getPassword()))) {
                panel1.setVisible(false);
                new MainMenu(jFrame, studentManager);
            } else {
                studentManager.showErrorMessageDialog("Deine Anmeldedaten stimmen mit keinem Account überein!\n" +
                        "Wenn du du dein Passwort vergessen hast, wende dich an einen Administrator.", jFrame);
            }
        });
    }

}
