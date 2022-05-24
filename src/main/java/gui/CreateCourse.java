package gui;

import manager.StudentManager;
import user.Student;
import user.Teacher;
import user.User;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public CreateCourse(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Kurs hinzufügen - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(this.kursHinzufügenButton);

        this.users = new ArrayList<>();
        this.users.add(studentManager.getCurrentUser());
        this.userTextArea.setText(this.userTextArea.getText() + studentManager.getCurrentUser().getEmail() + "\n");

        this.aktuelleTeilnehmerLabel.setText("Aktuelle Teilnehmer (" + users.size() + ")");

        loadComboBox(this.userComboBox, studentManager);
        this.userComboBox.setSelectedIndex(0);

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

        this.addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userComboBox.getSelectedItem() == null) {
                    return;
                }
                User user = studentManager.findUser(String.valueOf(userComboBox.getSelectedItem()));
                users.add(user);
                userTextArea.setText(userTextArea.getText() + user.getEmail() + "\n");
                loadComboBox(userComboBox, studentManager);
                aktuelleTeilnehmerLabel.setText("Aktuelle Teilnehmer (" + users.size() + ")");
            }
        });

        this.abbrechenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new CoursesOverview(jFrame, studentManager);
            }
        });

        this.kursHinzufügenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kursbezeichnungTextField.getText().isEmpty()) {
                    studentManager.showErrorMessageDialog("Bitte gib eine Kursbezeichnung ein.", jFrame);
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

                if (studentManager.addCourse(kursbezeichnungTextField.getText(), weekdays)) {
                    if (studentManager.addUserToCourse(users, studentManager.findCourse(kursbezeichnungTextField.getText()))) {
                        panel1.setVisible(false);
                        new CoursesOverview(jFrame, studentManager);
                        studentManager.showSuccessMessageDialog("Der Kurs " + kursbezeichnungTextField.getText() + " wurde erstellt.", jFrame);
                    }
                }
            }
        });
    }

    /**
     * Reloads / Loads the combo box (suggestions for a user) is located
     *
     * @param jComboBox The box into which the elements are to be loaded
     * @param studentManager instance of studentManager to access the methods
     */
    private void loadComboBox(JComboBox jComboBox, StudentManager studentManager) {
        jComboBox.removeAllItems();
        if (!userTextField.getText().isEmpty()) {
            loadComboBoxFiltered(jComboBox, studentManager);
            return;
        }
        for (User user : studentManager.getUsers()) {
            if (!this.users.contains(user)) {
                jComboBox.addItem(user.getEmail());
            }
        }
    }

    /**
     * Reloads / Loads the combo box (suggestions for a user) is located, but only the elements which aren't yet selected are going to be displayed
     *
     * @param jComboBox The box into which the elements are to be loaded
     * @param studentManager instance of studentManager to access the methods
     */
    private void loadComboBoxFiltered(JComboBox jComboBox, StudentManager studentManager) {
        jComboBox.removeAllItems();
        for (User user : studentManager.getUsers()) {
            if (!this.users.contains(user) && user.getEmail().toLowerCase().startsWith(userTextField.getText().toLowerCase())) {
                jComboBox.addItem(user.getEmail());
            }
        }
    }
}
