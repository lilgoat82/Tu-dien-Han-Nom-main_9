package com.example.myfirstjavafx.model;

public class TraAmHanVietEntry implements LookupEntry {
    private String kanji;
    private String meaning;
    private String content;
    private final String source = "am_han_viet";

    public TraAmHanVietEntry(String kanji, String meaning, String content) {
        this.kanji = kanji;
        this.meaning = meaning;
        this.content = content;
    }

    @Override
    public String getKanji() {
        return kanji;
    }

    @Override
    public String getMeaning() {
        return meaning;
    }

    @Override
    public String getContent() {
        return content;
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
