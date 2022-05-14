package gui;

import manager.StudentManager;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private JPanel panel1;

    public MainMenu(JFrame jFrame, StudentManager studentManager) {
        // configuring the jFrame
        jFrame.setTitle("Übersicht - Schulportal");
        jFrame.setContentPane(panel1);
        jFrame.setPreferredSize(new Dimension(800, 500));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().setDefaultButton(null);

        panel1.setVisible(true);
    }
}
