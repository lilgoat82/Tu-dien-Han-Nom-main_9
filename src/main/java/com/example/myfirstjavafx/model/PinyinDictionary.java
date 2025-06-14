package com.example.myfirstjavafx.model;

import java.io.InputStream;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PinyinDictionary {
    private Map<String, List<String>> pinyinMap = new HashMap<>();


    public PinyinDictionary() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = getClass().getClassLoader().getResourceAsStream("com/example/myfirstjavafx/data/pinyin/pinyin-hanzi-table.json");


            if(input != null) {
                pinyinMap = mapper.readValue(input, new TypeReference<Map<String, List<String>>>() {});
            }
            else {
                System.out.println("Không tìm thấy file");
            }
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Map<String, List<String>> getPinyinMap() {
        return pinyinMap;
    }


}
