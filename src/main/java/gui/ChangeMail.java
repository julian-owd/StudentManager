package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeMail {
    private JTextField emailField;
    private JButton bestätigenButton;
    private JPanel panel1;
    private JPasswordField passwordField;

    public ChangeMail(StudentManager studentManager) {
        // configuring jFrame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Login - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(400, 150));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(bestätigenButton);

        this.bestätigenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emailField.getText().length() == 0 || passwordField.getPassword().length == 0) {
                    showErrorMessageDialog("Bitte gib eine E-Mail & ein Passwort ein!", "Fehler", jFrame);
                    return;
                }
                if (!(studentManager.getCurrentUser().getPassword().equals(String.valueOf(passwordField.getPassword())))) {
                    System.out.println(studentManager.getCurrentUser().getPassword() + "=" + String.valueOf(passwordField.getPassword()));
                    showErrorMessageDialog("Das eingegebene Passwort ist falsch!", "Fehler", jFrame);
                    return;
                }
                studentManager.changeUserEmail(studentManager.getCurrentUser(), emailField.getText());
                JOptionPane.showMessageDialog(jFrame, "Deine E-Mail-Adresse wurde erfolgreich geändert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                jFrame.setVisible(false);
                jFrame.dispose();
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
