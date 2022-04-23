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
    private static String host;
    private static String database;
    private static String user;
    private static String password;
    private static int port;

    /**
     * Constructor with all the necessary variables
     *
     * @param host
     * @param database
     * @param user
     * @param password
     * @param port
     */
    public SQLManager(String host, String database, String user, String password, int port) {
        SQLManager.host = host;
        SQLManager.database = database;
        SQLManager.user = user;
        SQLManager.password = password;
        SQLManager.port = port;

        this.connect();
    }

    /**
     * Connects with the database
     */
    public void connect() {
        // if we are not connected yet, then go ahead
        if (!this.isConnected()) {
            String url = "jdbc:mysql://" + SQLManager.host + ":" + SQLManager.port + "/" + SQLManager.database;
            try {
                // establishing the connection, if there are any errors, they will be printed to the console
                this.connection = DriverManager.getConnection(url, SQLManager.user, SQLManager.password);
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
    public HashMap getData(String sql) {
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
