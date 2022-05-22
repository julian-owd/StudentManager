package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ChangePassword {
    private JPanel panel1;
    private JPasswordField currentPasswordField;
    private JButton bestätigenButton;
    private JPasswordField newPasswordField;
    private JPasswordField newPasswortField2;

    public ChangePassword(StudentManager studentManager) {
        // configuring jFrame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Passwort ändern - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(400, 150));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(bestätigenButton);

        bestätigenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPasswordField.getPassword().length == 0 || newPasswordField.getPassword().length == 0 || newPasswortField2.getPassword().length == 0) {
                    showErrorMessageDialog("Bitte fülle alle Felder aus!", "Fehler", jFrame);
                    return;
                }
                if (!(String.valueOf(currentPasswordField.getPassword()).equals(studentManager.getCurrentUser().getPassword()))) {
                    showErrorMessageDialog("Dein aktuelles Passwort ist inkorrekt!", "Fehler", jFrame);
                    return;
                }
                if (!(Arrays.equals(newPasswordField.getPassword(), newPasswortField2.getPassword()))) {
                    showErrorMessageDialog("Dein neues Passwort muss 2x korrekt eingegeben werden!", "Fehler", jFrame);
                    return;
                }
                studentManager.changeUserPassword(studentManager.getCurrentUser(), String.valueOf(newPasswordField.getPassword()));
                JOptionPane.showMessageDialog(jFrame, "Dein Passwort wurde erfolgreich geändert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
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
