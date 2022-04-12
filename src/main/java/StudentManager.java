import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import user.*;
import course.*;
/**
 * @author Julian Oswald
 * @date 03.04.2022
 */
public class StudentManager {
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    /**
     *
     * @param designation
     * @return if our designation the same that we set it before
     */
    public Course findCourse(String designation){
        Course found = null;
        int left = 0;
        int right = courses.size() - 1;

        while(left <= right && found == null){
            int mid = (right + left) / 2;

            if(courses.get(mid).getDesignation().equalsIgnoreCase(designation)){
                found = courses.get(mid);
            } else {
                if(designation.compareToIgnoreCase(courses.get(mid).getDesignation()) < 0) right = mid - 1;
                else left = mid + 1;
            }
        }
        return found;
    }

    /**
     *
     * @param cID
     * @return if our cID the same that we set it before
     */
    public Course findCourse(int cID){
        Course found = null;
        int left = 0;
        int right = courses.size() - 1;

        while(left <= right && found == null){
            int mid = (right + left) / 2;

            if(courses.get(mid).getcID() == cID){
                found = courses.get(mid);
            } else {
                if(cID < courses.get(mid).getcID()) right = mid - 1;
                else left = mid + 1;
            }
        }
        return found;
    }

    /**
     *
     * @param email
     * @return if our email the same that we set it before
     */
    public User findUser(String email){
        User found = null;
        int left = 0;
        int right = users.size() - 1;

        while(left < right && found == null){
            int mid = (right + left) / 2;

            if(users.get(mid).getEmail().equalsIgnoreCase(email)){
                found = users.get(mid);
            } else {
                if(email.compareToIgnoreCase(courses.get(mid).getDesignation()) < 0) right = mid - 1;
                else left = mid + 1;
            }
        }
        return found;
    }

    /**
     *
     * @param uID
     * @return if our uId the same that we set it before
     */
    public User findUser(int uID){
        User found = null;
        int left = 0;
        int right = users.size() - 1;

        while(left <= right && found == null){
            int mid = (right + left) / 2;

            if(users.get(mid).getuID() == uID){
                found = users.get(mid);
            } else {
                if(uID < users.get(mid).getuID()) right = mid - 1;
                else left = mid + 1;
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
        List<String> words = Arrays.asList(
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
                "Mikrofon"
        );

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
     * @param email the recipient of the mail
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
