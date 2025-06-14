package com.example.myfirstjavafx.service;

import com.example.myfirstjavafx.model.SearchHistoryEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistoryService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path file = Path.of("history.json");
    private final List<SearchHistoryEntry> historyList = new ArrayList<>();

    public SearchHistoryService() {
        loadHistoryFromFile();
    }

    public List<SearchHistoryEntry> getHistory() {
        return historyList;
    }

    private void loadHistoryFromFile() {
        if(Files.exists(file)) {
            try {
                SearchHistoryEntry[] loaded = objectMapper.readValue(
                        new File(file.toUri()), SearchHistoryEntry[].class
                );
                historyList.clear();
                historyList.addAll(Arrays.asList(loaded));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addHistotry(String query, String type) {
        String[] time = LocalDateTime.now().toString().split("T");

        String formattedTime = time[0] + "  " + time[1].substring(0, 5);
        historyList.add(0, new SearchHistoryEntry(query, formattedTime, type));
        if(historyList.size() > 100) {
            historyList.remove(historyList.size() - 1);
        }

        saveHistoryToFile();
    }

    private void saveHistoryToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), historyList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteHistory() {
        historyList.clear();
        saveHistoryToFile();
    }



}
