package gui;

import manager.StudentManager;
import user.User;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RemoveUser {
    private JTextField userTextField;
    private JComboBox userComboBox;
    private JButton abbrechenButton;
    private JButton entfernenButton;
    private JPanel panel1;

    private HashMap<Integer, User> userHashMap;

    /**
     * Opens the RemoveUser view
     *
     * @param studentManager an instance of studentManager
     */
    public RemoveUser(StudentManager studentManager) {
        // configuring jFrame
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Nutzer entfernen - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(400, 250));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.getRootPane().setDefaultButton(this.entfernenButton);

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

        // listener of the remove / confirm button
        this.entfernenButton.addActionListener(e -> {
            if (userHashMap.containsKey(userComboBox.getSelectedIndex())) {
                if (studentManager.deleteUser(userHashMap.get(userComboBox.getSelectedIndex()))) {
                    studentManager.showSuccessMessageDialog("Der Nutzer wurde entfernt.", jFrame);
                } else {
                    studentManager.showErrorMessageDialog("Der Nutzer konnte nicht entfernt werden!", jFrame);
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
