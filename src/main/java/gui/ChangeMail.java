package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;

public class ChangeMail {
    private JTextField emailField;
    private JButton bestätigenButton;
    private JPanel panel1;
    private JPasswordField passwordField;

    /**
     * Opens the ChangeMail view
     *
     * @param studentManager an instance of studentManager
     */
    public ChangeMail(StudentManager studentManager) {
        // configuring jFrame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("E-Mail-Adresse ändern - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(400, 150));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(bestätigenButton);

        // listener of the confirm button
        this.bestätigenButton.addActionListener(e -> {
            if (emailField.getText().length() == 0 || passwordField.getPassword().length == 0) {
                studentManager.showErrorMessageDialog("Bitte gib eine E-Mail & ein Passwort ein!", jFrame);
                return;
            }
            if (!(studentManager.getCurrentUser().getPassword().equals(String.valueOf(passwordField.getPassword())))) {
                System.out.println(studentManager.getCurrentUser().getPassword() + "=" + String.valueOf(passwordField.getPassword()));
                studentManager.showErrorMessageDialog("Das eingegebene Passwort ist falsch!", jFrame);
                return;
            }
            studentManager.changeUserEmail(studentManager.getCurrentUser(), emailField.getText());
            studentManager.showSuccessMessageDialog("Deine E-Mail-Adresse wurde erfolgreich geändert.", jFrame);
            jFrame.setVisible(false);
            jFrame.dispose();
        });
    }

}
