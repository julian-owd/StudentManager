import course.Date;
import gui.ChangeMail;
import gui.Login;
import manager.StudentManager;

import javax.swing.*;
import java.text.DateFormat;
import java.util.GregorianCalendar;

public class GUITestUI {

    public static void main(String[] args) {
        StudentManager sm = new StudentManager();
        new Login(new JFrame(), sm);
    }

}
