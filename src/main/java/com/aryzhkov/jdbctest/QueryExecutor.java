package com.aryzhkov.jdbctest;

import java.sql.ResultSet;
import java.util.Properties;

public class QueryExecutor {
    // check and execute query via JDBC
    private Properties properties;

    public QueryExecutor(Properties properties) {
        this.properties = properties;
    }

    public TableData execute(String query) {
        // execute query
        // get result set
        ResultSet resultSet = null;
        return parseResultSet(resultSet);
    }

    TableData parseResultSet(ResultSet resultSet) {
        return null;
    }
}
