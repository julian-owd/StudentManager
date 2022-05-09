import manager.SQLManager;
import manager.StudentManager;

public class StudentManagerTestUI {

    public static void main(String[] args) {
        SQLManager sql = new SQLManager("localhost", "studentmanager", "root", "", 3306);

        sql.query(
                "INSERT INTO `user` "
                        + "(`uID`, `lastName`, `firstName`, `email`, `password`) VALUES"
                        + "('1', 'Oswald', 'Julian', 'julian.oswald@tls-giessen.eu', '1234'), "
                        + "('2', 'Roth', 'Elias', 'elias.roth@tls-giessen', '1234'),"
                        + "('3', 'Iduh', 'Kenny', 'kenny.iduh@tls-giessen', '1234'),"
                        + "('4', 'Drescher', 'Bela', 'bela.drescher@tls-giessen', '1234'),"
                        + "('5', 'Nguyen', 'Cong', 'cong.ngyuen@tls-giessen', '1234')");
        sql.query(
                "INSERT INTO `teacher` "
                        + "(`uID`, `isSick`, `isAdmin`) VALUES"
                        + "('1', '0', '1'), "
                        + "('5', '0', '0')");

        sql.query("INSERT INTO `course` " +
                "(`cID`, `designation`) VALUES" +
                "('1', '12BGPI')," +
                "('2', '12BGGeschi')");
        sql.query("INSERT INTO `course_weekday`" +
                "(`cID`, `weekday`) VALUES" +
                "('1', '1')," +
                "('1', '3')," +
                "('2', '3')," +
                "('2', '5')");
        sql.query("INSERT INTO `student_course`" +
                "(`uID`, `cID`) VALUES" +
                "('2', '1')," +
                "('3', '1')," +
                "('4', '1')," +
                "('2', '2')," +
                "('3', '2')");
        sql.query("INSERT INTO `teacher_course`" +
                "(`uID`, `cID`) VALUES" +
                "('1', '1')," +
                "('5', '2')");
        sql.query("INSERT INTO `entry`" +
                "(`eID`, `date`, `title`, `description`, `cID`) VALUES" +
                "('1', '2022-05-02', 'Einstieg Joins', 'Moodle Kurs AB Joins 2', '1')," +
                "('2', '2022-05-04', 'Netzwerkkabel patchen', 'Netzwerkkabel mit Auszubildenden gepatcht', '1')," +
                "('3', '2022-05-06', 'Einstieg Ersatzleistung', 'Vorstellung der Ersatzleistung & Arbeitsbeginn', '2')");
        sql.query("INSERT INTO `student_entry`" +
                "(`uID`, `eID`) VALUES" +
                "('2', '1')," +
                "('3', '1')," +
                "('4', '1')," +
                "('2', '2')," +
                "('3', '2')," +
                "('2', '3')," +
                "('4', '3')");
        sql.query("INSERT INTO `homework`" +
                "(`hID`, `designation`, `eID`) VALUES" +
                "('1', 'AB Joins 2 fertigstellen', '1')," +
                "('2', 'Ersatzleistung fortsetzen', '3')");
        sql.query("INSERT INTO `exam`" +
                "(`eID`, `designation`, `grade`, `cID`) VALUES" +
                "('1', 'PI Leistungskontrolle 1 Q2', '12', '1')," +
                "('2', 'PI Leistungskontrolle 1 Q2', '15', '1')," +
                "('3', 'PI Leistungskontrolle 1 Q2', '10', '1')," +
                "('4', 'Geschi Mündlich 2.2.22', '15', '2')");
        sql.query("INSERT INTO `student_exam`" +
                "(`uID`, `eID`) VALUES" +
                "('2', '1')," +
                "('3', '2')," +
                "('4', '3')," +
                "('2', '4')");

        /*StudentManager sm = new StudentManager();

            System.out.println(sm.findCourse(1));
            System.out.println();
            System.out.println(sm.findCourse("12BGPI"));
            System.out.println();
            System.out.println(sm.findUser(4));
            System.out.println();
            System.out.println(sm.findUser("kai.tuschner@mail.com"));
            System.out.println();
            System.out.println(sm.findHomework(1));
            System.out.println();
            System.out.println(sm.logIn("elias.roth@gmx.net", "KatzeColaFlugzeugMathe"));
            System.out.println();
            sm.logOut();
            System.out.println();
        System.out.println(sm.getSubstitutionPlan());
            System.out.println();
            sm.markHomeworkAsFinished(sm.findHomework(1));
            System.out.println();
            sm.deleteUser(sm.findUser(1));*/
    }
}
