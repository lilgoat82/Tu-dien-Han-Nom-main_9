package com.example.myfirstjavafx.service.tra_pinyin;

import com.example.myfirstjavafx.model.PinyinDictionary;

import java.util.Collections;
import java.util.List;

public class TraPinyinService {
    private final PinyinDictionary pinyinDictionary =  new PinyinDictionary();

    public TraPinyinService() {

    }

    public List<String> searchPinyin(String query) {
        if(query == null || query.isEmpty()) {
            return Collections.emptyList();
        }

        return pinyinDictionary.getPinyinMap().getOrDefault(query.toLowerCase(), Collections.emptyList());
    }
}
