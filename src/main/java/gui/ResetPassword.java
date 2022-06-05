package gui;

import manager.StudentManager;
import user.User;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ResetPassword {
    private JPanel panel1;
    private JTextField userTextField;
    private JComboBox userComboBox;
    private JButton abbrechenButton;
    private JButton zurücksetzenButton;

    private HashMap<Integer, User> userHashMap;

    /**
     * Opens the ResetPassword view
     *
     * @param studentManager an instance of studentManager
     */
    public ResetPassword(StudentManager studentManager) {
        // configuring jFrame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Passwort zurücksetzen - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(420, 270));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(this.zurücksetzenButton);

        this.userHashMap = new HashMap<>();
        this.loadComboBox(studentManager);

        // listener of the cancel button
        this.abbrechenButton.addActionListener(e -> {
            jFrame.setVisible(false);
            jFrame.dispose();
        });

        // listener of the combobox
        this.userComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                loadComboBox(studentManager);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        // listener of the reset / confirm button
        this.zurücksetzenButton.addActionListener(e -> {
            if (userHashMap.containsKey(userComboBox.getSelectedIndex())) {
                if (studentManager.resetUserPassword(userHashMap.get(userComboBox.getSelectedIndex()))) {
                    studentManager.showSuccessMessageDialog("Das Passwort des Nutzers wurde zurückgesetzt. Er erhält sein neues Passwort an seine E-Mail-Adresse.", jFrame);
                } else {
                    studentManager.showErrorMessageDialog("Das Passwort konnte nicht zurückgesetzt werden!", jFrame);
                }
                jFrame.setVisible(false);
                jFrame.dispose();
            }
        });
    }

    /**
     * Loads a sorted comboBox to show the administrator useful suggestions for users
     *
     * @param studentManager instance of studentManager to access the methods
     */
    private void loadComboBox(StudentManager studentManager) {
        this.userComboBox.removeAllItems();
        this.userHashMap.clear();

        ArrayList<User> users = new ArrayList<>(studentManager.getUsers());
        users.sort(User::compareTo);

        if (this.userTextField.getText().isEmpty()) {
            for (User user : users) {
                this.userComboBox.addItem(user.getFirstName() + " " + user.getLastName() + " | " + user.getEmail());
                this.userHashMap.put(this.userComboBox.getItemCount() - 1, user);
            }
        } else {
            for (User user : users) {
                String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
                if (name.contains(this.userTextField.getText().toLowerCase()) || user.getEmail().toLowerCase().contains(this.userTextField.getText().toLowerCase())) {
                    this.userComboBox.addItem(name + " | " + user.getEmail());
                    this.userHashMap.put(this.userComboBox.getItemCount() - 1, user);
                }
            }
        }

        if (this.userComboBox.getItemCount() > 0) {
            this.userComboBox.setSelectedIndex(0);
        }
    }

}
