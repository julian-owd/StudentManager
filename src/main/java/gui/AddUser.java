package gui;

import manager.StudentManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class AddUser {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField mailField;
    private JButton abbrechenButton;
    private JButton erstellenButton;
    private JPanel panel1;
    private JCheckBox lehrerCheckBox;

    /**
     * Opens the AddUser view
     *
     * @param studentManager an instance of studentManager
     */
    public AddUser(StudentManager studentManager) {
        // configuring jFrame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Nutzer erstellen - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(400, 250));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(this.erstellenButton);

        // listener of cancel button
        this.abbrechenButton.addActionListener(e -> {
            jFrame.setVisible(false);
            jFrame.dispose();
        });

        // listener of the create button
        this.erstellenButton.addActionListener(e -> {
            if (firstNameField.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib einen Vornamen ein!", jFrame);
                return;
            }
            if (lastNameField.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib einen Nachnamen ein!", jFrame);
                return;
            }
            if (mailField.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib eine E-Mail-Adresse ein!", jFrame);
                return;
            }
            if (studentManager.findUser(mailField.getText()) != null) {
                studentManager.showErrorMessageDialog("Diese E-Mail-Adresse ist bereits vergeben!", jFrame);
                return;
            }
            if (studentManager.createUser(lastNameField.getText(), firstNameField.getText(), mailField.getText(), lehrerCheckBox.isSelected()) == null) {
                studentManager.showErrorMessageDialog("Der Nutzer konnte nicht erstellt werden!", jFrame);
            } else {
                studentManager.showSuccessMessageDialog("Der Nutzer wurde erstellt. Sein Passwort erhält er an seine E-Mail-Adresse.", jFrame);
            }
            jFrame.setVisible(false);
            jFrame.dispose();
        });
    }

}
