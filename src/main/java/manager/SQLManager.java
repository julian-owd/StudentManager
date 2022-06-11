package manager;

import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Julian Oswald
 * @date 28.03.2022
 */

@NoArgsConstructor
public class SQLManager {

    // variables for SQLManager to specify which database with which user we want to use
    private Connection connection;
    private String host;
    private String database;
    private String user;
    private String password;
    private int port;

    /**
     * Constructor with all the necessary variables
     *
     * @param host     the host of the database (most likely localhost / 127.0.0.1)
     * @param database the name of the database
     * @param user     the user to log in with
     * @param password the password of the user
     * @param port     which port to use (most likely 3306)
     */
    public SQLManager(String host, String database, String user, String password, int port) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;

        this.connect();

        // Create database & tables
        if (this.isConnected()) {
            try {
                Statement statement = this.connection.createStatement();

                // use executeUpdate for DDL statements
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.database); // create the database
                statement.executeUpdate("USE " + this.database); // use database

                // create table user
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS user " +
                        "(uID INT(8) NOT NULL AUTO_INCREMENT, " +
                        "lastName VARCHAR(50) NOT NULL, " +
                        "firstName VARCHAR(50) NOT NULL, " +
                        "email VARCHAR(100) NOT NULL, " +
                        "password VARCHAR(256) NOT NULL, " +
                        "PRIMARY KEY(uID))");

                // create table teacher
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS teacher " +
                        "(isSick TINYINT(1) NOT NULL, " +
                        "isAdmin TINYINT(1) NOT NULL, " +
                        "uID INT(8) NOT NULL, " +
                        "PRIMARY KEY(uID), " +
                        "FOREIGN KEY(uID) REFERENCES user(uID) ON DELETE CASCADE)");

                // create table course
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS course " +
                        "(cID INT(8) NOT NULL AUTO_INCREMENT, " +
                        "designation VARCHAR(50) NOT NULL, " +
                        "PRIMARY KEY(cID))");

                // create table entry
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS entry " +
                        "(eID INT(8) NOT NULL AUTO_INCREMENT, " +
                        "date DATE NOT NULL, " +
                        "title VARCHAR(100) NOT NULL, " +
                        "description VARCHAR(5000) NOT NULL, " +
                        "cID INT(8)," +
                        "PRIMARY KEY(eID)," +
                        "FOREIGN KEY(cID) REFERENCES course(cID) ON DELETE CASCADE)");

                // create table exam
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS exam " +
                        "(eID INT(8) NOT NULL AUTO_INCREMENT, " +
                        "designation VARCHAR(200) NOT NULL, " +
                        "cID INT(8)," +
                        "PRIMARY KEY(eID)," +
                        "FOREIGN KEY(cID) REFERENCES course(cID) ON DELETE CASCADE)");

                // create table homework
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS homework " +
                        "(hID INT(8) NOT NULL AUTO_INCREMENT, " +
                        "designation VARCHAR(500) NOT NULL, " +
                        "eID INT(8)," +
                        "PRIMARY KEY(hID)," +
                        "FOREIGN KEY(eID) REFERENCES entry(eID) ON DELETE CASCADE)");

                // create table student_course
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS student_course " +
                        "(uID INT(8) NOT NULL, " +
                        "cID INT(8) NOT NULL, " +
                        "PRIMARY KEY(uID, cID), " +
                        "FOREIGN KEY(uID) REFERENCES user(uID) ON DELETE CASCADE, " +
                        "FOREIGN KEY(cID) REFERENCES course(cID) ON DELETE CASCADE)");

                // create table student_entry
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS student_entry " +
                        "(uID INT(8) NOT NULL, " +
                        "eID INT(8) NOT NULL, " +
                        "PRIMARY KEY(uID, eID), " +
                        "FOREIGN KEY(uID) REFERENCES user(uID) ON DELETE CASCADE, " +
                        "FOREIGN KEY(eID) REFERENCES entry(eID) ON DELETE CASCADE)");

                // create table student_homework
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS student_homework " +
                        "(uID INT(8) NOT NULL, " +
                        "hID INT(8) NOT NULL, " +
                        "PRIMARY KEY(uID, hID), " +
                        "FOREIGN KEY(uID) REFERENCES user(uID) ON DELETE CASCADE, " +
                        "FOREIGN KEY(hID) REFERENCES homework(hID) ON DELETE CASCADE)");

                // create table student_exam
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS student_exam " +
                        "(uID INT(8) NOT NULL, " +
                        "eID INT(8) NOT NULL, " +
                        "grade INT (2) NOT NULL, " +
                        "PRIMARY KEY(uID, eID), " +
                        "FOREIGN KEY(uID) REFERENCES user(uID) ON DELETE CASCADE, " +
                        "FOREIGN KEY(eID) REFERENCES exam(eID) ON DELETE CASCADE)");

                // create table teacher_course
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS teacher_course " +
                        "(uID INT(8) NOT NULL, " +
                        "cID INT(8) NOT NULL, " +
                        "PRIMARY KEY(uID, cID), " +
                        "FOREIGN KEY(uID) REFERENCES user(uID) ON DELETE CASCADE, " +
                        "FOREIGN KEY(cID) REFERENCES course(cID) ON DELETE CASCADE)");

                // create table course_weekday
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS course_weekday " +
                        "(cID INT(8) NOT NULL, " +
                        "weekday INT(8) NOT NULL, " +
                        "PRIMARY KEY(cID, weekday), " +
                        "FOREIGN KEY(cID) REFERENCES course(cID) ON DELETE CASCADE)");

                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Connects with the database
     */
    public void connect() {
        // if we are not connected yet, then go ahead
        if (!this.isConnected()) {
            String url = "jdbc:mysql://" + this.host + ":" + this.port;
            try {
                // establishing the connection, if there are any errors, they will be printed to the console
                this.connection = DriverManager.getConnection(url, this.user, this.password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Disconnects from the database
     */
    public void disconnect() {
        if (this.isConnected()) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * runs a sql query, use anywhere where no return is expected
     *
     * @param sql the sql command line
     */
    public void query(String sql) {
        try {
            // creating, executing and then closing our statement we want to execute
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs a sql query, use anywhere where a return is expected
     *
     * @param sql the sql command line
     * @return the data given by the command line
     */
    public HashMap<Integer, ArrayList<String>> getData(String sql) {
        // Map where our data is going to be placed in
        HashMap<Integer, ArrayList<String>> data = new HashMap<>();
        try {
            // creating the statement
            Statement statement = this.connection.createStatement();
            // in resultSet our data is placed directly from the database
            ResultSet resultSet = statement.executeQuery(sql);

            // if our resultSet is null then something went wrong
            if (resultSet == null) {
                throw new SQLException();
            }

            // how many columns are we expecting to have
            int numberOfColumns = resultSet.getMetaData().getColumnCount();

            int counter = 0;

            // as long as there are new rows, go ahead
            while (resultSet.next()) {
                // we have to start with one because mysql doesn't start at 0
                int currentColumn = 1;

                // here is the row stored
                ArrayList<String> list = new ArrayList<>();

                // put any column expected into our data list, as long as we didn't hit the limit for columns
                while (currentColumn <= numberOfColumns) {
                    list.add(resultSet.getString(currentColumn));
                    currentColumn++;
                }
                data.put(counter, list);
                counter++;
            }
            // closing our statement
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // returning our data
        return data;
    }


    /**
     * check if the connection to the database is established
     *
     * @return true if the connection is established
     */
    public boolean isConnected() {
        return this.connection != null;
    }
}
