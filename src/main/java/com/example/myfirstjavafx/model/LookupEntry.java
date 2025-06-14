package com.example.myfirstjavafx.model;

public interface LookupEntry {
    String getKanji();
    String getMeaning();
    String getContent();
    String getSource();
    String getDisplayString(); // Dùng để hiển thị lịch sử hoặc kết quả
}
