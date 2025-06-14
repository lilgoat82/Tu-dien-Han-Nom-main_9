package com.example.myfirstjavafx.model;

public class TraHanEntry implements LookupEntry {
    private String kanji;
    private String meaning;
    private String dictionary;
    private String content;
    private String set;
    private final String source = "han";

    public TraHanEntry(String kanji, String meaning, String dictionary, String content, String set) {
        this.kanji = kanji;
        this.meaning = meaning;
        this.dictionary = dictionary;
        this.content = content;
        this.set = set;
    }

    public String getKanji() {
        return kanji;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getDictionary() {
        return dictionary;
    }

    public String getContent() {
        return content;
    }

    public String getSet() {
        return set;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDisplayString() {
        return kanji + " (" + meaning + "): " + content;
    }
}
