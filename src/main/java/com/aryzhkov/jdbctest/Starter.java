package com.aryzhkov.jdbctest;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Starter {

    public static String parseQuery(String[] args) {
        return null;
    }

    public static String parsePropertiesPath(String[] args) {
        return null;
    }

    public static void main(String[] args) {
        String propertiesPath = parsePropertiesPath(args);

        String query = parseQuery(args);

        Properties properties = getProperties(propertiesPath);
        QueryExecutor queryExecutor = new QueryExecutor(properties);
        ConsolePrinter consolePrinter = new ConsolePrinter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();

        TableData tableData = queryExecutor.execute(query);
        String html = htmlGenerator.generateHtml(tableData);
        //  writeHtmlToFile(html, pathToFile);

        consolePrinter.print(tableData);
    }

    private static Properties getProperties(String propertiesPath) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
