package gui;

import course.Course;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ManageParticipants {
    private JPanel panel1;
    private JList userList;
    private JTextField userTextField;
    private JComboBox userComboBox;
    private JButton abbrechenButton;
    private JButton änderungenÜbernehmenButton;
    private JButton nutzerZumKursHinzufügenButton;

    private ArrayList<User> usersToAdd;
    private ArrayList<User> usersToRemove;
    private HashMap<Integer, User> userComboBoxHashMap;
    private HashMap<Integer, User> userJListHashMap;
    private DefaultListModel<String> userModel;

    public ManageParticipants(JFrame jFrame, StudentManager studentManager, Course course) {
        // configuring the jFrame
        jFrame.setTitle(course.getDesignation() + " - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.usersToAdd = new ArrayList<>();
        this.usersToRemove = new ArrayList<>();
        this.userComboBoxHashMap = new HashMap<>();
        this.userJListHashMap = new HashMap<>();
        this.loadComboBox(studentManager, course);
        this.loadUserList(course);

        this.abbrechenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new TeacherCourseDetail(jFrame, studentManager, course);
            }
        });

        this.userComboBox.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                loadComboBox(studentManager, course);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        this.nutzerZumKursHinzufügenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userComboBoxHashMap.containsKey(userComboBox.getSelectedIndex())) {
                    usersToAdd.add(userComboBoxHashMap.get(userComboBox.getSelectedIndex()));
                    usersToRemove.remove(userComboBoxHashMap.get(userComboBox.getSelectedIndex()));
                    loadComboBox(studentManager, course);
                    loadUserList(course);
                }
            }
        });

        this.userList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = userList.getSelectedIndex();
                User user = userJListHashMap.get(index);
                if (user.getUID() == studentManager.getCurrentUser().getUID()) {
                    studentManager.showErrorMessageDialog("Du kannst dich nicht selbst aus einem Kurs entfernen!", jFrame);
                    return;
                }
                usersToRemove.add(userJListHashMap.get(index));
                usersToAdd.remove(userJListHashMap.get(index));
                loadComboBox(studentManager, course);
                loadUserList(course);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.änderungenÜbernehmenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean successfulAdding;
                boolean successfulRemoving = true;
                successfulAdding = studentManager.addUserToCourse(usersToAdd, course);

                for (User user : usersToRemove) {
                    if (!studentManager.removeUserFromCourse(user, course)) {
                        studentManager.showErrorMessageDialog("Der Nutzer " + user.getFirstName() + " " + user.getLastName() + " konnte nicht entfernt werden!", jFrame);
                        successfulRemoving = false;
                    }
                }

                panel1.setVisible(false);
                new TeacherCourseDetail(jFrame, studentManager, course);
                if (successfulAdding && successfulRemoving) {
                    studentManager.showSuccessMessageDialog("Die Änderungen wurden übernommen.", jFrame);
                    return;
                }
                if (!successfulAdding && !successfulRemoving) {
                    studentManager.showErrorMessageDialog("Die Änderungen konnten nicht übernommen werden!", jFrame);
                    return;
                }
                if (!successfulAdding) {
                    studentManager.showSuccessMessageDialog("Es konnten eventuell nicht alle Nutzer dem Kurs hinzugefügt werden.", jFrame);
                } else {
                    studentManager.showSuccessMessageDialog("Es konnten eventuell nicht alle Nutzer aus dem Kurs entfernt werden.", jFrame);
                }
            }
        });
    }

    /**
     * Loads a sorted comboBox to show the administrator useful suggestions for users
     *
     * @param studentManager instance of studentManager to access the methods
     */
    private void loadComboBox(StudentManager studentManager, Course course) {
        this.userComboBox.removeAllItems();
        this.userComboBoxHashMap.clear();

        ArrayList<User> users = studentManager.getUsers().stream().filter(user -> (!course.getStudents().contains(user) && !course.getTeachers().contains(user) && !this.usersToAdd.contains(user)) || this.usersToRemove.contains(user)).sorted(User::compareTo).collect(Collectors.toCollection(ArrayList::new));

        if (this.userTextField.getText().isEmpty()) {
            for (User user : users) {
                this.userComboBox.addItem(user.getFirstName() + " " + user.getLastName() + " | " + user.getEmail());
                this.userComboBoxHashMap.put(this.userComboBox.getItemCount() - 1, user);
            }
        } else {
            for (User user : users) {
                String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
                if (name.contains(this.userTextField.getText().toLowerCase()) || user.getEmail().toLowerCase().contains(this.userTextField.getText().toLowerCase())) {
                    this.userComboBox.addItem(name + " | " + user.getEmail());
                    this.userComboBoxHashMap.put(this.userComboBox.getItemCount() - 1, user);
                }
            }
        }

        if (this.userComboBox.getItemCount() > 0) {
            this.userComboBox.setSelectedIndex(0);
        }
    }

    /**
     * Shows the teacher an overview of the existing users and new users in this course
     *
     * @param course the course we want to add the users to
     */
    private void loadUserList(Course course) {
        this.userModel.removeAllElements();
        this.userJListHashMap.clear();

        this.userModel.addElement("Schüler:");
        for (Student student : course.getStudents().stream().filter(user -> !this.usersToRemove.contains(user)).toList()) {
            int index = this.userModel.getSize();
            this.userModel.add(index, student.getFirstName() + " " + student.getLastName());
            this.userJListHashMap.put(index, student);
        }
        for (User user : this.usersToAdd) {
            if (user instanceof Student student) {
                int index = this.userModel.getSize();
                this.userModel.add(index, "* " + student.getFirstName() + " " + student.getLastName());
                this.userJListHashMap.put(index, student);
            }
        }
        this.userModel.addElement("\n");
        this.userModel.addElement("Lehrer:");
        for (Teacher teacher : course.getTeachers().stream().filter(user -> !this.usersToRemove.contains(user)).toList()) {
            int index = this.userModel.getSize();
            this.userModel.add(index, teacher.getFirstName() + " " + teacher.getLastName());
            this.userJListHashMap.put(index, teacher);
        }
        for (User user : this.usersToAdd) {
            if (user instanceof Teacher teacher) {
                int index = this.userModel.getSize();
                this.userModel.add(index, "* " + teacher.getFirstName() + " " + teacher.getLastName());
                this.userJListHashMap.put(index, teacher);
            }
        }
    }

    private void createUIComponents() {
        this.userModel = new DefaultListModel<>();
        this.userList = new JList(this.userModel);
    }
}
