package com.example.myfirstjavafx.util;

import javafx.application.Platform;
import javafx.scene.control.Tab;

public class JSConnector {
    public void traHan(String hanzi) {
        Platform.runLater(() -> {
            System.out.println(hanzi);
//            Tab curTab = SceneManager.getMainController().getCurrentTab();

            SceneManager.switchTab(hanzi, "han");
        });

    }
}
