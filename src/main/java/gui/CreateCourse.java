package gui;

import manager.StudentManager;
import user.User;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CreateCourse {
    private JTextField kursbezeichnungTextField;
    private JTextField userTextField;
    private JComboBox userComboBox;
    private JButton addUserButton;
    private JPanel panel1;
    private JTextArea userTextArea;
    private JButton kursHinzufügenButton;
    private JButton abbrechenButton;
    private JCheckBox montagCheckBox;
    private JCheckBox dienstagCheckBox;
    private JCheckBox mittwochCheckBox;
    private JCheckBox donnerstagCheckBox;
    private JCheckBox freitagCheckBox;
    private JLabel aktuelleTeilnehmerLabel;

    private ArrayList<User> users;
    private HashMap<Integer, User> userComboBoxHashMap;

    /**
     * Opens the CreateCourse view
     *
     * @param jFrame         the jFrame of all windows
     * @param studentManager an instance of studentManager
     */
    public CreateCourse(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Kurs hinzufügen - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(this.kursHinzufügenButton);

        this.users = new ArrayList<>();
        this.userComboBoxHashMap = new HashMap<>();
        this.users.add(studentManager.getCurrentUser());
        this.userTextArea.setText(this.userTextArea.getText() + studentManager.getCurrentUser().getEmail() + "\n");

        this.aktuelleTeilnehmerLabel.setText("Aktuelle Teilnehmer (" + users.size() + ")");

        loadComboBox(this.userComboBox, studentManager);
        this.userComboBox.setSelectedIndex(0);

        // listener of the combobox
        this.userComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                loadComboBox(userComboBox, studentManager);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        // listener of the button to add an user to this new course
        this.addUserButton.addActionListener(e -> {
            if (userComboBox.getSelectedItem() == null) {
                return;
            }
            if (userComboBoxHashMap.containsKey(userComboBox.getSelectedIndex())) {
                User user = userComboBoxHashMap.get(userComboBox.getSelectedIndex());
                users.add(user);
                userTextArea.setText(userTextArea.getText() + user.getEmail() + "\n");
                userTextField.setText("");
                loadComboBox(userComboBox, studentManager);
                aktuelleTeilnehmerLabel.setText("Aktuelle Teilnehmer (" + users.size() + ")");
            }
        });

        // listener of the cancel button
        this.abbrechenButton.addActionListener(e -> {
            panel1.setVisible(false);
            new CoursesOverview(jFrame, studentManager);
        });

        // listener of the create button
        this.kursHinzufügenButton.addActionListener(e -> {
            if (kursbezeichnungTextField.getText().isEmpty()) {
                studentManager.showErrorMessageDialog("Bitte gib eine Kursbezeichnung ein.", jFrame);
                return;
            }
            if (kursbezeichnungTextField.getText().length() > 50) {
                studentManager.showErrorMessageDialog("Die Kursbezeichnung darf nicht länger als 50 Zeichen sein!", jFrame);
                return;
            }
            if (studentManager.findCourse(kursbezeichnungTextField.getText()) != null) {
                studentManager.showErrorMessageDialog("Diesen Kursnamen gibt es bereits.", jFrame);
                return;
            }

            ArrayList<Integer> weekdays = new ArrayList<>();
            if (montagCheckBox.isSelected()) {
                weekdays.add(1);
            }
            if (dienstagCheckBox.isSelected()) {
                weekdays.add(2);
            }
            if (mittwochCheckBox.isSelected()) {
                weekdays.add(3);
            }
            if (donnerstagCheckBox.isSelected()) {
                weekdays.add(4);
            }
            if (freitagCheckBox.isSelected()) {
                weekdays.add(5);
            }

            if (weekdays.isEmpty()) {
                studentManager.showErrorMessageDialog("Der Kurs muss an mindestens einem Wochentag stattfinden.", jFrame);
                return;
            }

            if (studentManager.addCourse(kursbezeichnungTextField.getText(), weekdays) != null) {
                if (!studentManager.addUserToCourse(users, studentManager.findCourse(kursbezeichnungTextField.getText()))) {
                    studentManager.showErrorMessageDialog("Es konnten nicht alle Schüler dem Kurs hinzugefügt werden!", jFrame);
                }
                panel1.setVisible(false);
                new CoursesOverview(jFrame, studentManager);
                studentManager.showSuccessMessageDialog("Der Kurs " + kursbezeichnungTextField.getText() + " wurde erstellt.", jFrame);
            } else {
                studentManager.showErrorMessageDialog("Der Kurs konnte nicht erstellt werden!", jFrame);
            }
        });
    }

    /**
     * Reloads / Loads the combo box (suggestions for a user) is located
     *
     * @param jComboBox      The box into which the elements are to be loaded
     * @param studentManager instance of studentManager to access the methods
     */
    private void loadComboBox(JComboBox jComboBox, StudentManager studentManager) {
        jComboBox.removeAllItems();
        if (!userTextField.getText().isEmpty()) {
            loadComboBoxFiltered(jComboBox, studentManager);
            return;
        }

        ArrayList<User> users = studentManager.getUsers().stream().filter(user -> !this.users.contains(user)).sorted(User::compareTo).collect(Collectors.toCollection(ArrayList::new));

        for (User user : users) {
            jComboBox.addItem(user.getFirstName() + " " + user.getLastName() + " | " + user.getEmail());
            this.userComboBoxHashMap.put(jComboBox.getItemCount() - 1, user);
        }
    }

    /**
     * Reloads / Loads the combo box (suggestions for a user) is located, but only the elements which aren't yet selected are going to be displayed
     *
     * @param jComboBox      The box into which the elements are to be loaded
     * @param studentManager instance of studentManager to access the methods
     */
    private void loadComboBoxFiltered(JComboBox jComboBox, StudentManager studentManager) {
        jComboBox.removeAllItems();

        ArrayList<User> users = studentManager.getUsers().stream().filter(user -> !this.users.contains(user) && user.getEmail().toLowerCase().contains(this.userTextField.getText().toLowerCase())).sorted(User::compareTo).collect(Collectors.toCollection(ArrayList::new));

        for (User user : users) {
            jComboBox.addItem(user.getFirstName() + " " + user.getLastName() + " | " + user.getEmail());
            this.userComboBoxHashMap.put(jComboBox.getItemCount() - 1, user);
        }
    }

}
