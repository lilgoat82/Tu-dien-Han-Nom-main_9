package com.example.myfirstjavafx.util;

public class CheckHanzi {
    public static boolean isHanzi(Character ch) {
        return (ch >= 0x4E00 && ch <= 0x9FFF) // CJK Unified
                || (ch >= 0x3400 && ch <= 0x4DBF) // CJK Extension A
                || (ch >= 0xF900 && ch <= 0xFAFF); // CJK Compatibility
    }
}
