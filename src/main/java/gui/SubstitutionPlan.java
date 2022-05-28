package gui;

import manager.StudentManager;
import user.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubstitutionPlan {
    private JPanel panel1;
    private JButton zurückButton;
    private JList substitutionList;
    private DefaultListModel<String> substitutionModel;

    public SubstitutionPlan(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Vertretungsplan - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        if (studentManager.getCurrentUser() instanceof Student student) {
            if (studentManager.getSubstitutionPlan().isEmpty()) {
                this.substitutionModel.addElement("Alle deine Kurse finden voraussichtlich statt!");
            } else {
                for (String s : studentManager.getSubstitutionPlan()) {
                    this.substitutionModel.addElement("• " + s);
                }
            }
        }

        this.zurückButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new MainMenu(jFrame, studentManager);
            }
        });
    }

    private void createUIComponents() {
        this.substitutionModel = new DefaultListModel<>();
        this.substitutionList = new JList(this.substitutionModel);
    }
}
