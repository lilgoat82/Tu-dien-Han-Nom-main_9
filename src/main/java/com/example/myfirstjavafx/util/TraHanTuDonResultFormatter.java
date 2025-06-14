package com.example.myfirstjavafx.util;

import com.example.myfirstjavafx.model.TraHanEntry;

import java.util.Arrays;

public class TraHanTuDonResultFormatter {

    public static String format(TraHanEntry entry) {
        if (entry == null) {
            return "Không tìm thấy kết quả.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html><style>.hanzi:hover { text-decoration: underline; font-weight: bolder }</style><body style='font-size: 16px; padding: 5px 10px; line-height: 1.6'>");
        // 1. Bộ thủ (set)
        if (entry.getSet() != null && !entry.getSet().isEmpty()) {
            sb.append("<span>")
                    .append(entry.getSet().trim())
                    .append("</span>")
                    .append("<br>");
        }

        // 2. Ký tự Hán + nghĩa Hán-Việt (meaning)
        String kanji = entry.getKanji() != null ? entry.getKanji().trim() : "";
        String meaning = entry.getMeaning() != null ? entry.getMeaning().replace(";", ",").replace("\"", "").trim() : "";
        if (!kanji.isEmpty() || !meaning.isEmpty()) {
            sb.append("<p style='font-size: 24px; color: #4ec766; font-weight: semibold; margin: 0'>")
                    .append(kanji)
                    .append(" ")
                    .append(meaning)
                    .append("</p>");
//                    .append("<br>");
        }

        // 3. Phiên âm (dictionary), các dòng nối lại thành một hàng, từ trên xuống hiển thị trái qua phải
        if (entry.getDictionary() != null && !entry.getDictionary().isEmpty()) {
//            System.out.println(entry.getDictionary());
            String[] parts = entry.getDictionary().split(";");
//            System.out.println(Arrays.toString(parts));
            StringBuilder dictLine = new StringBuilder();

            for (String part : parts) {
                String cleaned = part.trim().replace("\"", "");
                if (!cleaned.isEmpty()) {
                    if (dictLine.length() > 0) {
                        dictLine.append(" ");
                    }
                    dictLine.append(cleaned);
                }
            }


            if (dictLine.length() > 0) {
                sb.append("<p style='margin: 0; color: #821414'>")
                        .append(dictLine.toString())
                        .append("</p>");
//                        .append("<br>");
            }
        }

        // 4. Nội dung giải nghĩa (content) – hiển thị bắt đầu bằng hình thoi
        if (entry.getContent() != null && !entry.getContent().isEmpty()) {
            String[] sections = entry.getContent().split("♦");
            for (String section : sections) {
                String line = section.trim().replace("\"", "");

                if (!line.isEmpty()) {
                    StringBuilder wrappedLine = new StringBuilder();
                    for(Character c : line.toCharArray()) {
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
        sb.append("</body></html>");

        return sb.toString().trim();
    }
}
