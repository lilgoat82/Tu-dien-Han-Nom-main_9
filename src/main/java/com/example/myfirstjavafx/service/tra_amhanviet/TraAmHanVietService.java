package com.example.myfirstjavafx.service.tra_amhanviet;

import com.example.myfirstjavafx.model.TraAmHanVietEntry;
import com.example.myfirstjavafx.model.LookupEntry;
import com.example.myfirstjavafx.service.LookupService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TraAmHanVietService implements LookupService {

    private final List<TraAmHanVietEntry> entries = new ArrayList<>();

    public TraAmHanVietService() {
        loadTuDonData("com/example/myfirstjavafx/data/han_nom/tudon_split_clean.csv");
        loadTuGhepData("com/example/myfirstjavafx/data/han_nom/tughep_split.csv");
    }

    private void loadTuDonData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath)),
                StandardCharsets.UTF_8))) {

            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(";", -1);
                if (parts.length >= 6) {
                    String kanji = parts[2].trim();
                    String meaning = parts[3].trim().toLowerCase();
                    String content = parts[5].trim();
                    entries.add(new TraAmHanVietEntry(kanji, meaning, content));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTuGhepData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath)),
                StandardCharsets.UTF_8))) {

            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(";", -1);
                if (parts.length >= 4) {
                    String kanji = parts[1].trim();
                    String meaning = parts[2].trim().toLowerCase();
                    String content = parts[3].trim();
                    entries.add(new TraAmHanVietEntry(kanji, meaning, content));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LookupEntry search(String query) {
        if (query == null || query.isEmpty()) return null;
        String lowerQuery = query.toLowerCase().trim();

        for (TraAmHanVietEntry entry : entries) {
            if (entry.getMeaning().equalsIgnoreCase(lowerQuery)) {
                return entry;
            }
        }

        return null;
    }
}
