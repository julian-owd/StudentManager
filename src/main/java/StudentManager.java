import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

import database.SQLManager;
import user.*;
import course.*;

/**
 * @author Julian Oswald
 * @date 03.04.2022
 */
public class StudentManager {

    // variables for StudentManager
    private ArrayList<Course> courses;
    private ArrayList<User> users;
    private SQLManager database;
    private User currentUser;

    /**
     * Constructor which also connects it with the database
     */
    public StudentManager() {
        this.courses = new ArrayList<>();
        this.users = new ArrayList<>();
        this.database = new SQLManager("localhost", "studentmanager", "root", "", 3306);
    }

    /**
     * login into an existing account
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return if the login was successful, user can be found in variable currentUser
     */
    public boolean login(String email, String password) {
        // the user is already logged in
        if (this.currentUser != null) {
            return false;
        }

        // getting the uID from the database
        HashMap<Integer, ArrayList<String>> userData = this.database.getData("SELECT uID FROM user WHERE email='" + email + "' AND password='" + password + "'");
        if (userData.size() != 0) {

            // checking if it's a teacher or a student
            HashMap<Integer, ArrayList<String>> teacherData = this.database.getData("SELECT uID FROM teacher WHERE uID=" + userData.get(0).get(0));
            if (teacherData.size() != 0) { // teacher
                User user = new Teacher(Integer.parseInt(userData.get(0).get(0)));
                this.currentUser = user;
            } else { // student
                User user = new Student(Integer.parseInt(userData.get(0).get(0)));
                this.currentUser = user;
            }
            return true;

        }
        return false;
    }

    /**
     * Search for a course based on its name
     *
     * @param designation the name of the course we're searching for
     * @return the course we have searched for, if nothing was found then the return is null
     */
    public Course findCourse(String designation) {
        for (Course course : this.courses) {
            if (course.getDesignation().equalsIgnoreCase(designation)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Search for a course based on its id Important: The list must be sorted
     *
     * @param cID the id of the course we're searching for
     * @return the course we have searched for, if nothing was found then the return is null
     */
    public Course findCourse(int cID) {
        Course found = null;
        int left = 0;
        int right = this.courses.size() - 1;

        // as long as we haven't found it yet
        while (left <= right && found == null) {
            int mid = (right + left) / 2;

            if (this.courses.get(mid).getcID() == cID) {
                found = this.courses.get(mid);
            } else {
                // decide whether our course is on the left or right side of the list
                if (cID < this.courses.get(mid).getcID()) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return found;
    }

    /**
     * Search for a user based on his email
     *
     * @param email the email of the user we're searching
     * @return the user we have searched for, if nothing was found then the return is null
     */
    public User findUser(String email) {
        for (User user : this.users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Search for a user based on his id Important: The list must be sorted
     *
     * @param uID the id of the user we're searching
     * @return the user we have searched for, if nothing was found then the return is null
     */
    public User findUser(int uID) {
        User found = null;
        int left = 0;
        int right = this.users.size() - 1;

        // as long as we haven't found him yet
        while (left <= right && found == null) {
            int mid = (right + left) / 2;

            if (this.users.get(mid).getuID() == uID) {
                found = this.users.get(mid);
            } else {
                // decide whether our user is on the left or right side of the list
                if (uID < this.users.get(mid).getuID()) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return found;
    }

    /**
     * create a random password
     *
     * @return random password
     */
    public String generateRandomPassword() {

        // list of words to create the password with
        List<String> words =
                Arrays.asList(
                        "Wasser",
                        "Cola",
                        "Fanta",
                        "Sprite",
                        "Kaffee",
                        "Tee",
                        "Baum",
                        "Katze",
                        "Hund",
                        "Mathe",
                        "Eis",
                        "Pommes",
                        "Zug",
                        "Bus",
                        "Flugzeug",
                        "Kuh",
                        "Huhn",
                        "Banane",
                        "Apfel",
                        "Berg",
                        "Handball",
                        "Tastatur",
                        "Maus",
                        "Mikrofon");

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        List<Integer> usedIndexes =
                new ArrayList<>(); // list to put in the indexes that were already used

        for (int i = 0; i < 4; i++) { // 4 = select 4 random words for the password
            int randomIndex = random.nextInt(words.size()); // selects a randomIndex from the list

            while (usedIndexes.contains(randomIndex)) {
                randomIndex = random.nextInt(words.size());
            }

            stringBuilder.append(words.get(randomIndex)); // appends the word at the index randomIndex
            usedIndexes.add(
                    randomIndex); // adds the randomIndex to the used list because words are only used once
        }

        return stringBuilder.toString();
    }

    /**
     * send a mail
     *
     * @param email   the recipient of the mail
     * @param subject the topic of the mail
     * @param message the message in the mail
     * @return if the sending was successful
     */
    public boolean sendMail(String email, String subject, String message) {
        // which mail server we want to use and some properties of it
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "mail.mailfour24.de");
        properties.put("mail.smtp.ssl.trust", "mail.mailfour24.de");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.port", "587");

        // logging in with the account we want our emails to be sent from
        Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("projekt@julian-oswald.de", "e@T4fb8V_K");
                            }
                        });

        try {
            Message mimeMessage = new MimeMessage(session);

            // setting the variables like sender, recipient, subject & message
            mimeMessage.setFrom(new InternetAddress("projekt@julian-oswald.de"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            // sending the mail
            Transport.send(mimeMessage);
            return true;
        } catch (MessagingException e) { // catching erros so our program doesn't stop
            e.printStackTrace();
        }
        return false;
    }
}
