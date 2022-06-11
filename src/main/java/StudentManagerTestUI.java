import manager.SQLManager;

public class StudentManagerTestUI {

    public static void main(String[] args) {
        SQLManager sql = new SQLManager("localhost", "studentmanager", "root", "", 3306);

        sql.query("INSERT INTO `user` " + "(`uID`, `lastName`, `firstName`, `email`, `password`) VALUES" + "('1', 'Oswald', 'Julian', 'julian.oswald@tls-giessen.eu', '1234'), " + "('2', 'Roth', 'Elias', 'elias.roth@tls-giessen.eu', '1234')," + "('3', 'Iduh', 'Kenny', 'kenny.iduh@tls-giessen.eu', '1234')," + "('4', 'Drescher', 'Bela', 'bela.drescher@tls-giessen.eu', '1234')," + "('5', 'Nguyen', 'Cong', 'cong.ngyuen@tls-giessen.eu', '1234')");
        sql.query("INSERT INTO `teacher` " + "(`uID`, `isSick`, `isAdmin`) VALUES" + "('1', '1', '1'), " + "('5', '1', '0')");
        sql.query("INSERT INTO `course` " + "(`cID`, `designation`) VALUES" + "('1', '12BGPI')," + "('2', '12BGGeschi')");
        sql.query("INSERT INTO `course_weekday`" + "(`cID`, `weekday`) VALUES" + "('1', '1')," + "('1', '3')," + "('2', '3')," + "('2', '5')");
        sql.query("INSERT INTO `student_course`" + "(`uID`, `cID`) VALUES" + "('2', '1')," + "('3', '1')," + "('4', '1')," + "('2', '2')," + "('3', '2')");
        sql.query("INSERT INTO `teacher_course`" + "(`uID`, `cID`) VALUES" + "('1', '1')," + "('5', '2')");
        sql.query("INSERT INTO `entry`" + "(`eID`, `date`, `title`, `description`, `cID`) VALUES" + "('1', '2022-05-02', 'Einstieg Joins', 'Moodle Kurs AB Joins 2', '1')," + "('2', '2022-05-04', 'Netzwerkkabel patchen', 'Netzwerkkabel mit Auszubildenden gepatcht', '1')," + "('3', '2022-05-06', 'Einstieg Ersatzleistung', 'Vorstellung der Ersatzleistung & Arbeitsbeginn', '2')");
        sql.query("INSERT INTO `student_entry`" + "(`uID`, `eID`) VALUES" + "('2', '1')," + "('3', '1')," + "('4', '1')," + "('2', '2')," + "('3', '2')," + "('2', '3')," + "('4', '3')");
        sql.query("INSERT INTO `homework`" + "(`hID`, `designation`, `eID`) VALUES" + "('1', 'AB Joins 2 fertigstellen', '1')," + "('2', 'Ersatzleistung fortsetzen', '3')");
        sql.query("INSERT INTO `exam`" + "(`eID`, `designation`, `cID`) VALUES" + "('1', 'PI Leistungskontrolle 1 Q2', '1')," + "('2', 'Geschi Mündlich 2.2.22', '2')");

        sql.query("INSERT INTO `student_exam`" + "(`uID`, `eID`, `grade`) VALUES" + "('2', '1', '13')," + "('3', '1', '10')," + "('4', '1', '6')," + "('2', '2', '1')," + "('3', '2', '7')");
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
