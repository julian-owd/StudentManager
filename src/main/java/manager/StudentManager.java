package manager;

import course.Course;
import course.Entry;
import course.Homework;
import course.Weekday;
import lombok.Getter;
import lombok.Setter;
import user.Student;
import user.Teacher;
import user.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author Julian Oswald
 * @date 03.04.2022
 */

@Getter
@Setter
public class StudentManager {

    // variables for manager.StudentManager
    private ArrayList<Course> courses;
    private ArrayList<User> users;
    private SQLManager database;
    private User currentUser;

    /**
     * Constructor which also connects it with the database
     */
    public StudentManager() {
        this.database = new SQLManager("localhost", "studentmanager", "root", "", 3306);
        this.courses = new ArrayList<>();
        this.users = new ArrayList<>();

        // add the users
        HashMap<Integer, ArrayList<String>> dbUsers = this.database.getData("SELECT uID FROM user");
        for (Integer i : dbUsers.keySet()) {
            if (this.database.getData("SELECT uID FROM teacher WHERE uID=" + dbUsers.get(i).get(0)).isEmpty()) {
                this.users.add(new Student(Integer.parseInt(dbUsers.get(i).get(0)), this));
            } else {
                this.users.add(new Teacher(Integer.parseInt(dbUsers.get(i).get(0)), this));
            }
        }

        // add the courses
        HashMap<Integer, ArrayList<String>> dbCourses = this.database.getData("SELECT cID FROM course");
        for (Integer i : dbCourses.keySet()) {
            this.courses.add(new Course(Integer.parseInt(dbCourses.get(i).get(0)), this));
        }

        // loading the homework after loaded every course to prevent homework that is null
        for (User u : this.users) {
            if (u instanceof Student) {
                ((Student) u).loadHomework(this);
            }
        }
    }

    /**
     * login into an existing account
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return if the login was successful, user can be found in variable currentUser
     */
    public boolean logIn(String email, String password) {
        // the user is already logged in
        if (this.currentUser != null) {
            return false;
        }

        // getting the uID from the database
        HashMap<Integer, ArrayList<String>> userData = this.database.getData("SELECT uID FROM user WHERE email='" + email + "' AND password='" + password + "'");
        if (!userData.isEmpty()) {

            User user = this.findUser(Integer.parseInt(userData.get(0).get(0)));
            if (user != null) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * logout from the current account
     */
    public void logOut() {
        // there is no user logged in right now
        if (this.currentUser == null) {
            return;
        }
        this.currentUser = null;
    }

    /**
     * Get a list of the courses the current user is a member of
     *
     * @return the list with courses
     */
    public ArrayList<Course> getMyCourses() {
        ArrayList<Course> myCourses = new ArrayList<>();

        for (Course course : this.courses) {
            // if the user is a student or teacher in this specific course
            if (course.getStudents().contains(this.currentUser) || course.getTeachers().contains(this.currentUser)) {
                myCourses.add(course);
            }
        }

        return myCourses;
    }

    /**
     * Get a list of the homework which is relevant to the current user
     *
     * @return the list with homework
     */
    public ArrayList<Homework> getMyHomework() {
        ArrayList<Homework> myHomework = new ArrayList<>();

        for (Course course : this.courses) {
            // if the user is a student or teacher in this specific course
            if (course.getStudents().contains(this.currentUser)) {
                for (Entry entry : course.getEntries()) {
                    if (entry.getHomework() != null) {
                        myHomework.add(entry.getHomework());
                    }
                }
            }
        }

        return myHomework;
    }

    /**
     * Get a list of the courses on this or the next day which are not going to take place
     *
     * @return the list with the courses not taking place
     */
    public ArrayList<String> getSubstitutionPlan() {
        ArrayList<String> substitutionPlan = new ArrayList<>();

        for (Course course : this.courses) {
            // if the user is a student or teacher in this specific course
            if (course.getStudents().contains(this.currentUser)) {
                Weekday weekday = Weekday.MON;
                int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                // converting the currentDay into a weekday. Default Mon in case the user is looking on a weekend-day
                switch (currentDay) {
                    case Calendar.TUESDAY -> weekday = Weekday.TUE;
                    case Calendar.WEDNESDAY -> weekday = Weekday.WED;
                    case Calendar.THURSDAY -> weekday = Weekday.THR;
                    case Calendar.FRIDAY -> weekday = Weekday.FRI;
                }

                for (Weekday days : course.getWeekdays()) {
                    // whether the current or the next day
                    if (weekday.compare(days) == 0 || weekday.compare(days) == 1) {
                        boolean allSick = true;
                        for (Teacher teacher : course.getTeachers()) {
                            // checking if there is a single teacher who isn't sick in this course
                            if (!teacher.isSick()) {
                                allSick = false;
                                break;
                            }
                        }
                        if (allSick) {
                            substitutionPlan.add(course.getDesignation());
                        }
                    }
                }
            }
        }

        return substitutionPlan;
    }

    /**
     * Create a user
     *
     * @param lastName  the lastName of the user
     * @param firstName the firstName of the user
     * @param email     the email of the user
     * @param isTeacher whether the user is a teacher
     * @return true if the creation was successful, false if not
     */
    public boolean createUser(String lastName, String firstName, String email, boolean isTeacher) {
        // checking if the email is already in use
        if (this.findUser(email) != null) {
            return false;
        }
        String password = this.generateRandomPassword();

        this.database.query("INSERT INTO user(lastName, firstName, email, password) VALUES ('" + lastName + "', '" + firstName + "', '" + email + "', '" + password + "')");
        HashMap<Integer, ArrayList<String>> userData = this.database.getData("SELECT uID FROM user WHERE email='" + email + "' AND lastName='" + lastName + "' AND firstName='" + firstName + "'");

        // if the HashMap is empty, we failed to add him into the database
        if (userData.isEmpty()) {
            return false;
        }
        int uID = Integer.parseInt(userData.get(0).get(0));

        // adding him into the running system
        if (isTeacher) {
            this.database.query("INSERT INTO teacher(isSick, isAdmin, uID) VALUES (0, 0, " + uID + ")");
            this.users.add(new Teacher(uID, this));
        } else {
            this.users.add(new Student(uID, this));
        }

        return this.sendMail(email, "Dein Zugangspasswort", "Dein Passwort für das Schulportal lautet: " + password);
    }

    /**
     * Delete an existing user
     *
     * @param user the user to delete
     * @return true if deleting the user was successful, false if not
     */
    public boolean deleteUser(User user) {
        // checking if the user object is null
        if (user == null) {
            return false;
        }

        // evaluating whether the user is a teacher or a student
        if (user instanceof Teacher) {
            this.database.query("DELETE FROM teacher_course WHERE uID=" + user.getUID());
            this.database.query("DELETE FROM teacher WHERE uID=" + user.getUID());
            this.database.query("DELETE FROM user WHERE uID=" + user.getUID());
            return true;
        } else if (user instanceof Student) {
            this.database.query("DELETE FROM student_course WHERE uID=" + user.getUID());
            this.database.query("DELETE FROM student_entry WHERE uID=" + user.getUID());
            this.database.query("DELETE FROM student_homework WHERE uID=" + user.getUID());
            this.database.query("DELETE FROM student_exam WHERE uID=" + user.getUID());
            this.database.query("DELETE FROM user WHERE uID=" + user.getUID());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reset the password of a user
     *
     * @param user the user whose password is being reset
     * @return true if the reset was successful, false if not
     */
    public boolean resetUserPassword(User user) {
        // checking if the user object is null
        if (user == null) {
            return false;
        }
        String newPassword = this.generateRandomPassword(); // generating a new password
        this.database.query("UPDATE user SET password='" + newPassword + "' WHERE uID=" + user.getUID()); // updating the password in the database
        return this.sendMail(user.getEmail(), "Dein neues Zugangspasswort", "Dein neues Passwort für das Schulportal lautet: " + newPassword); // sending a mail with the new password
    }

    public void markHomeworkAsFinished(Homework homework) {
        // if the current user is not a student he can't mark a homework as finished
        if (!(this.currentUser instanceof Student)) {
            return;
        }
        Student student = (Student) this.currentUser;

        boolean alreadyFinished = false;
        for (Homework h : student.getDoneHomework()) {
            if (h.getHID() == homework.getHID()) {
                alreadyFinished = true;
                break;
            }
        }

        // checking if the user has already done the homework
        if (alreadyFinished) {
            // marking the homework as unfinished
            student.getDoneHomework().remove(homework);
            this.database.query("DELETE FROM student_homework WHERE uID=" + student.getUID() + " AND hID=" + homework.getHID());
            return;
        }
        student.getDoneHomework().add(homework);
        database.query("INSERT INTO student_homework VALUES (" + student.getUID() + ", " + homework.getHID() + ")");
    }

    /**
     * Removes a user from a course
     *
     * @param user   the user to remove from the course
     * @param course the course to remove a user from
     * @return true if the removing was successful, false if not
     */
    public boolean removeUserFromCourse(User user, Course course) {
        if (course.getStudents().contains(user) || course.getTeachers().contains(user)) {
            if (user instanceof Teacher) {
                this.database.query("DELETE FROM teacher_course WHERE uID=" + user.getUID() + " AND cID=" + course.getCID());
            } else {
                this.database.query("DELETE FROM student_course WHERE uID=" + user.getUID() + " AND cID=" + course.getCID());
            }
            course.getStudents().remove(user);
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

            if (this.courses.get(mid).getCID() == cID) {
                found = this.courses.get(mid);
            } else {
                // decide whether our course is on the left or right side of the list
                if (cID < this.courses.get(mid).getCID()) {
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

            if (this.users.get(mid).getUID() == uID) {
                found = this.users.get(mid);
            } else {
                // decide whether our user is on the left or right side of the list
                if (uID < this.users.get(mid).getUID()) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return found;
    }

    /**
     * Search for a homework based on its id
     *
     * @param hID the id of the homework
     * @return the homework we've searched for, if nothing found the return is null
     */
    public Homework findHomework(int hID) {
        for (Course course : this.courses) {
            for (Entry entry : course.getEntries()) {
                if (entry.getHomework() == null) {
                    continue;
                }
                if (hID == entry.getHomework().getHID()) {
                    return entry.getHomework();
                }
            }
        }
        return null;
    }

    /**
     * create a random password
     *
     * @return random password
     */
    public String generateRandomPassword() {

        // list of words to create the password with
        List<String> words = Arrays.asList("Wasser", "Cola", "Fanta", "Sprite", "Kaffee", "Tee", "Baum", "Katze", "Hund", "Mathe", "Eis", "Pommes", "Zug", "Bus", "Flugzeug", "Kuh", "Huhn", "Banane", "Apfel", "Berg", "Handball", "Tastatur", "Maus", "Mikrofon");

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        List<Integer> usedIndexes = new ArrayList<>(); // list to put in the indexes that were already used

        for (int i = 0; i < 4; i++) { // 4 = select 4 random words for the password
            int randomIndex = random.nextInt(words.size()); // selects a randomIndex from the list

            while (usedIndexes.contains(randomIndex)) {
                randomIndex = random.nextInt(words.size());
            }

            stringBuilder.append(words.get(randomIndex)); // appends the word at the index randomIndex
            usedIndexes.add(randomIndex); // adds the randomIndex to the used list because words are only used once
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
        Session session = Session.getInstance(properties, new Authenticator() {
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
