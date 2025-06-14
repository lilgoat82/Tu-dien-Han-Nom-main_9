package com.example.myfirstjavafx.util;

import com.example.myfirstjavafx.model.TraHanEntry;

public class TraHanTuGhepResultFormatter {

    public static String format(TraHanEntry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-size: 16px; padding: 5px 10px; line-height: 1.6'>");

        // Dòng 1: Hiển thị kanji + meaning
        String kanji = entry.getKanji() != null ? entry.getKanji().trim() : "";
        String meaning = entry.getMeaning() != null ? entry.getMeaning().trim() : "";

        if (!meaning.isEmpty()) {
            // Chuẩn hóa nghĩa: bỏ khoảng trắng dư, thay ; bằng ,
            meaning = meaning.replaceAll("\\s*;\\s*", ", ");
            sb.append("<p style='font-size: 24px; color: #4ec766; font-weight: semibold; margin: 0'>")
                    .append(kanji)
                    .append(" (")
                    .append(meaning)
                    .append(")</p><br>");
        } else {
            sb.append("<span style='font-size: 24px; color: #4ec766; font-weight: semibold'>").append(kanji).append("</span><br>");
        }

        // Các dòng tiếp theo: Hiển thị content – bắt đầu mỗi dòng với ♦
        String content = entry.getContent();
        if (content != null && !content.isBlank()) {
            String[] parts = content.split("♦");
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    StringBuilder wrappedLine = new StringBuilder();
                    for(Character c : trimmed.toCharArray()) {
                        if(CheckHanzi.isHanzi(c)) {
                            wrappedLine.append("<span class='hanzi' style = 'cursor: pointer; color: blue;' onclick=\"app.traHan('")
                                    .append(c)
                                    .append("')\">")
                                    .append(c)
                                    .append("</span>");
                        }
                        else {
                            wrappedLine.append(c);
                        }
                    }
                    sb.append("<p style='margin: 0'>♦ ").append(wrappedLine).append("</p>");
                }
            }
        }

        return sb.toString().trim();
    }
}
