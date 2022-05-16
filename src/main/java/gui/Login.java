package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel panel1;
    private JTextField emailField;
    private JPasswordField passwortField;
    private JButton loginButton;

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

        // logic behind login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checking if the user filled out every field
                if (emailField.getText().length() == 0 || passwortField.getPassword().length == 0) {
                    showErrorMessageDialog("Bitte gib eine E-Mail-Adresse und ein Passwort ein!", "Fehler", jFrame);
                    return;
                }

                // checking if we can log in the user
                if (studentManager.logIn(emailField.getText(), String.copyValueOf(passwortField.getPassword()))) {
                    panel1.setVisible(false);
                    new MainMenu(jFrame, studentManager);
                } else {
                    showErrorMessageDialog("Deine Anmeldedaten stimmen mit keinem Account überein!\n" +
                            "Wenn du dein Passwort vergessen hast, wende dich an einen Administrator.", "Fehler", jFrame);
                }
            }
        });
    }

    /**
     * Shows an error message dialog
     *
     * @param message the message in the window
     * @param title   the title of the window
     * @param jFrame  the frame of the gui
     */
    public void showErrorMessageDialog(String message, String title, JFrame jFrame) {
        JOptionPane.showMessageDialog(jFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
