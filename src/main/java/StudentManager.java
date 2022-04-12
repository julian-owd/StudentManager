import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author Julian Oswald
 * @date 03.04.2022
 */
public class StudentManager {

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

    public boolean sendMail(String email, String subject, String message) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "mail.mailfour24.de");
        properties.put("mail.smtp.ssl.trust", "mail.mailfour24.de");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("projekt@julian-oswald.de", "e@T4fb8V_K");
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress("projekt@julian-oswald.de"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

}
