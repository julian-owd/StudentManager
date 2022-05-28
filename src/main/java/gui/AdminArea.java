package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminArea {
    private JPanel panel1;
    private JButton nutzerLöschenButton;
    private JButton nutzerErstellenButton;
    private JButton passwortZurücksetzenButton;
    private JButton zurückButton;

    public AdminArea(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Adminpanel - Schulportal");
        jFrame.setContentPane(this.panel1);
        jFrame.setPreferredSize(new Dimension(600, 300));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        this.zurückButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
                new MainMenu(jFrame, studentManager);
            }
        });

        this.nutzerErstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUser(studentManager);
            }
        });
    }
}
