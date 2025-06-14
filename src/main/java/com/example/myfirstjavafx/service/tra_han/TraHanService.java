package com.example.myfirstjavafx.service.tra_han;

import com.example.myfirstjavafx.model.TraHanEntry;
import com.example.myfirstjavafx.model.LookupEntry;
import com.example.myfirstjavafx.service.LookupService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TraHanService implements LookupService {

    private final Map<String, TraHanEntry> tuDonMap = new HashMap<>();
    private final Map<String, TraHanEntry> tuGhepMap = new HashMap<>();

    private HashMap<String, TraHanEntry> cache = new LinkedHashMap<>(200, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, TraHanEntry> eldest) {
            return size() > 200;
        }
    };

    public TraHanService() {
        loadTuDonData("com/example/myfirstjavafx/data/han_nom/tudon_split_clean.csv", tuDonMap);
        loadTuGhepData("com/example/myfirstjavafx/data/han_nom/tughep_split.csv", tuGhepMap);
    }

    private void loadTuDonData(String filePath, Map<String, TraHanEntry> map) {
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
//                System.out.println(line);
//                break;
                String[] parts = line.split(";", -1);

//                    System.out.println(Arrays.toString(parts));
//                    System.out.println(parts[3]);
                if (parts.length >= 6) {
                    String set = parts[1].trim();
                    String kanji = parts[2].trim();
                    String meaning = parts[3].trim();
                    String dictionary = parts[4].trim();
                    String content = parts[5].trim();

                    map.put(kanji, new TraHanEntry(kanji, meaning, dictionary, content, set));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTuGhepData(String filePath, Map<String, TraHanEntry> map) {
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

                if (parts.length >= 3) {
                    String kanji = parts[1].trim();
                    String meaning = parts[2].trim();
                    String content = parts.length > 3 ? parts[3].trim() : "";

                    map.put(kanji, new TraHanEntry(kanji, meaning, "", content, ""));
                }
            }
//
//            System.out.println("SỐ TỪ GHÉP ĐÃ LOAD: " + map.size());
//            map.keySet().stream().limit(10).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LookupEntry search(String query) {
        if (query == null || query.isEmpty()) return null;

        if(cache.containsKey(query)) {
            return cache.get(query);
        }

        TraHanEntry result = query.length() == 1 ? tuDonMap.get(query) : tuGhepMap.get(query);

        if(result != null) {
            cache.put(query, result);
        }

        return result;
    }
}
