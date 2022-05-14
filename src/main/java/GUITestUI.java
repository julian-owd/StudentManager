import gui.Login;
import manager.StudentManager;

import javax.swing.*;

public class GUITestUI {

    public static void main(String[] args) {
        StudentManager sm = new StudentManager();
        new Login(new JFrame(), sm);
    }

}
