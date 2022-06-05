package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ChangePassword {
    private JPanel panel1;
    private JPasswordField currentPasswordField;
    private JButton bestätigenButton;
    private JPasswordField newPasswordField;
    private JPasswordField newPasswortField2;

    /**
     * Opens the ChangePassword view
     *
     * @param studentManager an instance of studentManager
     */
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

        // listener of the confirm button
        bestätigenButton.addActionListener(e -> {
            if (currentPasswordField.getPassword().length == 0 || newPasswordField.getPassword().length == 0 || newPasswortField2.getPassword().length == 0) {
                studentManager.showErrorMessageDialog("Bitte fülle alle Felder aus!", jFrame);
                return;
            }
            if (newPasswordField.getPassword().length > 256) {
                studentManager.showErrorMessageDialog("Dein Password darf nicht länger als 256 Zeichen sein!", jFrame);
                return;
            }
            if (!(String.valueOf(currentPasswordField.getPassword()).equals(studentManager.getCurrentUser().getPassword()))) {
                studentManager.showErrorMessageDialog("Dein aktuelles Passwort ist inkorrekt!", jFrame);
                return;
            }
            if (!(Arrays.equals(newPasswordField.getPassword(), newPasswortField2.getPassword()))) {
                studentManager.showErrorMessageDialog("Dein neues Passwort muss 2x korrekt eingegeben werden!", jFrame);
                return;
            }
            studentManager.changeUserPassword(studentManager.getCurrentUser(), String.valueOf(newPasswordField.getPassword()));
            studentManager.showSuccessMessageDialog("Dein Passwort wurde erfolgreich geändert.", jFrame);
            jFrame.setVisible(false);
            jFrame.dispose();
        });
    }

}
