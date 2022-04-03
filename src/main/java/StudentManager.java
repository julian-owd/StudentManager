import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

}
