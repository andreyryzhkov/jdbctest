package com.aryzhkov.jdbctest;

import java.util.List;

public class TableData {
    //    boolean hasData; // ?
//    int rowsChanged; // ?
    List<String> columns;
    List<Object> values;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}