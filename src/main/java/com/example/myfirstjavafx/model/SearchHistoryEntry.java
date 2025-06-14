package com.example.myfirstjavafx.model;

public class SearchHistoryEntry {
    private String query;
    private String timeStamp;
    private String type;

    public SearchHistoryEntry() {}

    public SearchHistoryEntry(String query, String timeStamp) {
        this.query = query;
        this.timeStamp = timeStamp;
    }

    public SearchHistoryEntry(String query, String timeStamp, String type) {
        this.query = query;
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
