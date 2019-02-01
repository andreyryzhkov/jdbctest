package com.aryzhkov.jdbctest;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringJoiner;

public class JdbcTest {

    public static Connection getConnection(String dbproperties) {
        Properties properties = new Properties();
        Connection connection = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(dbproperties);
            properties.load(fileInputStream);

            connection = DriverManager.getConnection(properties.getProperty("DB_URL"),
                    properties.getProperty("DB_USERNAME"),
                    properties.getProperty("DB_PASSWORD"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void printTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        StringJoiner stringJoinerHeader = new StringJoiner(" | ");
        for (int i = 1; i <= columnCount; i++) {
            String colName = resultSetMetaData.getColumnName(i);
            stringJoinerHeader.add(colName);
        }
        System.out.println(stringJoinerHeader.toString().toUpperCase());

        while (resultSet.next()) {
            StringJoiner stringJoinerData = new StringJoiner(" | ");
            for (int i = 1; i <= columnCount; i++) {
                String data = resultSet.getString(resultSetMetaData.getColumnName(i));
                stringJoinerData.add(data);
            }
            System.out.println(stringJoinerData.toString());
        }
    }

    public static void generateHTML(ResultSet resultSet) throws IOException, SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        FileWriter fileWriter = new FileWriter("index.html");

        fileWriter.write("<html><body><table border=\"1\"><thead>");
        fileWriter.write("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            String colName = resultSetMetaData.getColumnName(i);
            fileWriter.write("<td>");
            fileWriter.write(colName);
            fileWriter.write("</td>");
        }
        fileWriter.write("</tr>");
        fileWriter.write("</thead><tbody>");

        while (resultSet.next()) {
            fileWriter.write("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                String data = resultSet.getString(resultSetMetaData.getColumnName(i));
                fileWriter.write("<td>");
                fileWriter.write(data);
                fileWriter.write("</td>");
            }
            fileWriter.write("</tr>");
        }

        fileWriter.write("</tbody></table></body></html>");
        fileWriter.close();
    }

    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();

        System.out.println(query);
        String[] split = query.split(" ");
        String dmlAction = split[0].toUpperCase();

        Connection connection = getConnection("db.properties");
        Statement statement = connection.createStatement();

        if (dmlAction.equals("SELECT")) {
            printTable(statement.executeQuery(query));
            generateHTML(statement.executeQuery(query));
        } else {
            int rowCount = statement.executeUpdate(query);
            System.out.println("Modified: " + rowCount + " rows");
        }
        connection.close();
    }
}