import manager.SQLManager;

public class StudentManagerTestUI {

    public static void main(String[] args) {
        SQLManager sql = new SQLManager("localhost", "studentmanager", "root", "", 3306);

        sql.query("INSERT INTO `user` (`uID`, `lastName`, `firstName`, `email`, `password`) VALUES (NULL, 'Oswald', 'Julian', 'julian.oswald@tls-giessen.eu', '1234'), (NULL, 'Roth', 'Elias', 'elias.roth@tls-giessen.eu', '1234'), (NULL, 'Iduh', 'Kenny', 'kenny.iduh@tls-giessen.eu', '1234'), (NULL, 'Drescher', 'Bela', 'bela.drescher@tls-giessen.eu', '1234'), (NULL, 'Carle', 'Mike', 'mike.carle@tls-giessen.eu', '1234'), (NULL, 'Miklic', 'Ivan', 'ivan.miklic@tls-giessen.eu', '1234'), (NULL, 'Mai', 'Luca', 'luca.mai@tls-giessen.eu', '1234'), (NULL, 'Hampel', 'Jonas', 'jonas.hampel@tls-giessen.eu', '1234'), (NULL, 'Bechthold', 'Julian', 'julian.bechthold@tls-giessen.eu', '1234'), (NULL, 'Brasch', 'Carolin', 'carolin.brasch@tls-giessen.eu', '1234'), (NULL, 'Engel', 'Elisabeth', 'elisabeth.engel@tls-giessen.eu', '1234'), (NULL, 'Aldudak', 'Ali', 'ali.aldudak@tls-giessen.eu', '1234')");
        sql.query("INSERT INTO `teacher` (`isSick`, `isAdmin`, `uID`) VALUES ('0', '1', '1'), ('0', '0', '12'), ('1', '1', '11'), ('1', '0', '10')");
        sql.query("INSERT INTO `course` (`cID`, `designation`) VALUES (NULL, 'Ethik GK 12 Brasch'), (NULL, 'Ethik GK 12 Aldudak'), (NULL, 'PI LK 12'), (NULL, 'TECH GK 12'), (NULL, 'Englisch 12 GK Brasch'), (NULL, '12 GK PoWi Aldudak')");
        sql.query("INSERT INTO `course_weekday` (`cID`, `weekday`) VALUES ('1', '4'), ('2', '4'), ('3', '3'), ('4', '1'), ('5', '3'), ('5', '5'), ('6', '1')");
        sql.query("INSERT INTO `student_course` (`uID`, `cID`) VALUES ('2', '2'), ('2', '3'), ('2', '4'), ('2', '5'), ('2', '6'), ('3', '2'), ('3', '3'), ('3', '4'), ('4', '2'), ('4', '3'), ('4', '4'), ('4', '5'), ('4', '6'), ('5', '1'), ('5', '3'), ('5', '4'), ('6', '1'), ('6', '3'), ('6', '4'), ('6', '5'), ('7', '3'), ('7', '4'), ('7', '5'), ('8', '1'), ('8', '3'), ('8', '4'), ('9', '2'), ('9', '3'), ('9', '4')\n");
        sql.query("INSERT INTO `teacher_course` (`uID`, `cID`) VALUES ('1', '1'), ('1', '3'), ('10', '5'), ('10', '1'), ('11', '3'), ('11', '4'), ('12', '6'), ('12', '2')");
        sql.query("INSERT INTO `entry` (`eID`, `date`, `title`, `description`, `cID`) VALUES (NULL, '2022-05-19', 'Kants guter Wille und Pflichtgemäßes Handeln', 'AB Kants guter Wille', '1'), (NULL, '2022-06-02', 'Klausurthemen, Anwendung und Grenzen des Kategorischen imperativs', 'Siehe Moodle für Themenübersicht', '1'), (NULL, '2022-06-16', 'Was ist Glück', 'Glück ist etwas wichtiges', '2'), (NULL, '2022-06-23', 'Start Klausurersatzleistung', 'Abgabe nächste Woche Montag', '2'), (NULL, '2022-06-08', 'Besprechung der Übungsklausur', 'Lösungen in Moodle freigeschaltet', '3'), (NULL, '2022-06-15', 'Softwareentwicklung: Projektorganisation, Vorgehensmodelle', '- \\'AB: Pflichten- und Lastenheft\\' bearbeiten und vorstellen: https://mo6292.schule.hessen.de/mod/resource/view.php?id=14696&redirect=1\\r\\n- \\'Qualität von Software\\': https://mo6292.schule.hessen.de/pluginfile.php/20204/mod_label/intro/ISO-25010-Qualitaetskriterien-fuer-Software.jpg\\r\\n- \\'Software Entwicklungs-Phasen\\': https://mo6292.schule.hessen.de/mod/resource/view.php?id=14705&redirect=1', '3'), (NULL, '2022-06-06', 'Klausurthemen', 'IP-Adressierung\\r\\n- Bedeutung + Berechnung von IP-Adresse\\r\\n- Netzmaske inkl. CIDR\\r\\n- Netzadresse\\r\\n- Wertebereich Hosts\\r\\n- Broadcast\\r\\n- Subnetting', '4'), (NULL, '2022-06-13', 'Klausur', 'Klausur geschrieben', '4'), (NULL, '2022-06-08', 'Project Work / finalize topics', 'Choose on which topic to make a poster about', '5'), (NULL, '2022-06-15', 'Project work: poster', 'Continue working on the posters', '5'), (NULL, '2022-06-20', 'Was ist Inflation', 'Inflation ist in Maßen wichtig --> AB Inflation', '6'), (NULL, '2022-06-27', 'Ausflug Geldmuseum', 'Ausflug ins Geldmuseum der Deutschen Bank', '6')\n");
        sql.query("INSERT INTO `student_entry` (`uID`, `eID`) VALUES ('2', '3'), ('2', '4'), ('3', '5'), ('4', '4'), ('4', '8'), ('5', '1'), ('5', '8'), ('6', '6'), ('6', '1'), ('7', '6'), ('8', '2'), ('8', '7'), ('9', '5'), ('9', '7')");
        sql.query("INSERT INTO `homework` (`hID`, `designation`, `eID`) VALUES (NULL, 'AB Glück01 fertigstellen', '3'), (NULL, 'Ersatzleistung zu Hause fertigstellen', '4'), (NULL, 'Topics per E-Mail mitteilen', '9'), (NULL, 'Bericht über den Ausflug schreiben', '12'), (NULL, 'Inflationsbegriff auswendig können', '11')");
        sql.query("INSERT INTO `exam` (`eID`, `designation`, `cID`) VALUES (NULL, 'Ethik Klausur Q2', '1'), (NULL, 'Ethik Klausurersatzleistung Q2', '2'), (NULL, 'PI LK Klausur Datenbanken Q2', '3'), (NULL, 'Klausur Q2 Netzwerktechnik', '4'), (NULL, 'Klausurersatzleistung Q2 PoWi', '6')");

        sql.query("INSERT INTO `student_exam` (`uID`, `eID`, `grade`) VALUES ('5', '1', '14'), ('6', '1', '12'), ('8', '1', '13'), ('2', '2', '10'), ('4', '2', '14'), ('9', '2', '11'), ('5', '3', '12'), ('3', '3', '13'), ('8', '3', '12'), ('4', '3', '15'), ('7', '3', '9'), ('2', '4', '8'), ('6', '4', '9'), ('4', '4', '14'), ('9', '4', '7'), ('2', '5', '12'), ('4', '5', '13'), ('2', '3', '11'), ('6', '3', '12')\n");
/*
        StudentManager sm = new StudentManager();

        System.out.println("Geladene Kurse: \n" + sm.getCourses() + "\n");
        System.out.println("Geladene Nutzer: \n" + sm.getUsers() + "\n");
        System.out.println(
                "Versuche Nutzer einzuloggen: " + sm.logIn("elias.roth@tls-giessen.eu", "1234"));
        System.out.println("Aktueller Nutzer: " + sm.getCurrentUser());
        System.out.println("Kurse des aktuellen Nutzers: \n" + sm.getMyCourses() + "\n");
        System.out.println("Hausaufgaben des aktuellen Nutzers: \n" + sm.getMyHomework() + "\n");
        System.out.println(
                "Vertretungsplan für den heutigen & morgigen Tag des aktuellen Nutzers: \n"
                        + sm.getSubstitutionPlan()
                        + "\n");
        System.out.println(
                "Erstellung eines Schülers: "
                        + sm.createUser("Mazur", "Justin", "justin.mazur@tls-giessen.eu", false));
        User user = sm.findUser("justin.mazur@tls-giessen.eu");
        System.out.println("Nutzer: " + user);

        System.out.println("Reset des Passworts: " + sm.resetUserPassword(user));

        System.out.println(
                "Nutzer zu Kurs hinzufügen: "
                        + sm.addUserToCourse(new ArrayList<>(List.of(user, sm.findUser(2))), sm.findCourse(1)));

        System.out.println("Kursmitglieder: " + sm.findCourse(1).getStudents());
        System.out.println(
                "Nutzer aus Kurs entfernen: " + sm.removeUserFromCourse(user, sm.findCourse(1)));

        System.out.println(
                "Kurs hinzufügen: " + sm.addCourse("12BGEthikBrasch", new ArrayList<>(List.of(4))));
        Course kurs = sm.findCourse("12BGEthikBrasch");
        System.out.println("Kurs: " + kurs + "\n");

        ArrayList<Student> participants = new ArrayList<>();
        participants.add((Student) sm.findUser(2));

        System.out.println(
                "Eintrag hinzufügen: "
                        + sm.addEntry(
                        kurs,
                        new Date(2022, 9, 5),
                        "Tierethik",
                        "Klausurthemen vorgetragen",
                        participants));
        System.out.println("Einträge: " + kurs.getEntries() + "\n");

        System.out.println(
                "Hausaufgabe hinzufügen: "
                        + sm.addHomework(kurs.getEntries().get(0), "Für Klausur lernen"));
        System.out.println("Hausaufgabe: " + kurs.getEntries().get(0).getHomework() + "\n");

        System.out.println("Exam: " + kurs.getExams().get(0) + "\n");

        sm.markHomeworkAsFinished(kurs.getEntries().get(0).getHomework());

        Student user2 = (Student) sm.findUser(2);
        System.out.println("Vom Nutzer erledigte Hausaufgaben: " + user2.getDoneHomework() + "\n");

        System.out.println(sm.findCourse(1));
        System.out.println(sm.findCourse("12BGPI") + "\n");
        System.out.println(sm.findUser(2));
        System.out.println(sm.findUser("elias.roth@tls-giessen.eu") + "\n");

        System.out.println(sm.findHomework(1) + "\n");

        System.out.println("Zufälliges Passwort: " + sm.generateRandomPassword());
        System.out.println("Zufälliges Passwort: " + sm.generateRandomPassword());

        System.out.println("Löschung des Nutzers: " + sm.deleteUser(user));

        sm.logOut();
        System.out.println("Aktueller Nutzer: " + sm.getCurrentUser());*/
    }
}
