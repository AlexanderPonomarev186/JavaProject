package org.example;

import org.jfree.ui.RefineryUtilities;
import org.joda.time.DateTime;

import java.sql.*;
import java.text.SimpleDateFormat;


public class DatabaseControl {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn(String name) throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:"+name);

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB(String statement) throws ClassNotFoundException, SQLException
    {
        statement = "CREATE TABLE if not exists 'earthquakes' ('ID' text PRIMARY KEY,'depthInMeters' INT, 'typeOfMagnitude' text, 'magnitude' REAL, 'date' text, 'stateID' INT);";
        statmt = conn.createStatement();
        statmt.execute(statement);

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void addEarthquakeItem(earthquake earthquake) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        String form = "YYYY-MM-DD HH:MM:SS.SSS";
        SimpleDateFormat SDformat = new SimpleDateFormat(form);
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO earthquakes('ID', 'depthInMeters', 'typeOfMagnitude', 'magnitude', 'date', 'stateID') " +
                        "VALUES(?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, earthquake.ID);
            statement.setObject(2, earthquake.depthInMeters);
            statement.setObject(3, earthquake.typeOfMagnitude);
            statement.setObject(4, earthquake.magnitude);
            String date = SDformat.format(earthquake.date.toDate());
            statement.setObject(5,date);
            statement.setObject( 6, earthquake.stateID);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addStateItem(state state) {
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO states('stateID', 'state') " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, state.stateID);
            statement.setObject(2, state.state);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteEarthquakesItem(earthquake earthquake) {
        try (PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM earthquakes WHERE id = ?")) {
            statement.setObject(1, earthquake.ID);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteStateItem(state state) {
        try (PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM earthquakes WHERE id = ?")) {
            statement.setObject(1, state.stateID);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // -------- Вывод таблицы--------
    public static void ReadDB(String query, int numOfTask) throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        resSet = statmt.executeQuery(query);

        while(resSet.next())
        {
            if (numOfTask == 0){ System.out.println("state = " + resSet.getString("state"));}
            if (numOfTask == 1){System.out.println("Среднее значение = " + resSet.getDouble( "avg(earthquakes.magnitude)"));}
        }

    }
    public static double ReturnDBValue(String query) throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        resSet = statmt.executeQuery(query);

        return resSet.getDouble("avg(earthquakes.magnitude)");

    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}