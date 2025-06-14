package com.example.myfirstjavafx.controller;

import com.example.myfirstjavafx.model.SearchHistoryEntry;
import com.example.myfirstjavafx.model.TraHanEntry;
import com.example.myfirstjavafx.service.SearchHistoryService;
import com.example.myfirstjavafx.service.tra_pinyin.TraPinyinService;
import com.example.myfirstjavafx.util.*;
import com.example.myfirstjavafx.model.LookupEntry;
import com.example.myfirstjavafx.service.tra_han.TraHanService;
import com.example.myfirstjavafx.service.tra_amhanviet.TraAmHanVietService;

import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {

    // UI đăng nhập/đăng xuất
    @FXML
    private Label usernameLabel;

    @FXML
    private HBox authOptionsPane; // chứa "Đăng nhập" / "Đăng ký"

    @FXML
    private HBox userPane; // chứa "Xin chào" + "Đăng xuất"

    // UI Tra Hán
    @FXML
    private TextField hanInputField;

    @FXML
    private TextArea hanResultArea;

    @FXML
    private WebView hanResultWebView;

    // UI Tra âm Hán-Việt
    @FXML
    private TextField hanVietInputField;


    @FXML
    private TextArea hanVietResultArea;

    @FXML
    private WebView hanVietResultWebView;

    @FXML
    private TextField pinyinInputField;

    @FXML
    private WebView pinyinResultWebView;


    @FXML
    private ListView historyListView;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab historyTab;

    @FXML
    private Tab pinyinTab;

    @FXML
    private Tab hanTab;

    @FXML
    private Tab hanvietTab;

    @FXML
    private Tab currentTab;

    @FXML
    private Label historyNotiLabel;

    private boolean isFromHistory = false;
    private String prevQuery = "";

    private boolean isContentExist = false;
    // Service
    private final TraHanService traHanService = new TraHanService();
    private final TraAmHanVietService traAmHanVietService = new TraAmHanVietService();
    private final TraPinyinService traPinyinService = new TraPinyinService();
    private final SearchHistoryService searchHistoryService = new SearchHistoryService();

    private final Map<Tab, Map<TextField, WebView>> tabWebViewMap = new HashMap<>();




    @FXML
    public void initialize() {
        SceneManager.setMainController(this);
        System.out.println(mainTabPane.getSelectionModel().getSelectedItem());
        currentTab = mainTabPane.getSelectionModel().getSelectedItem();

//        tabWebViewMap.put(hanTab, new HashMap<>());
//        tabWebViewMap.put(hanvietTab, hanVietResultWebView);
//        tabWebViewMap.put(pinyinTab, pinyinResultWebView);

        setUpHistoryListView();

        hanResultWebView.setVisible(false);
        hanVietResultWebView.setVisible(false);
        pinyinResultWebView.setVisible(false);

        hanResultWebView.setContextMenuEnabled(false);
        hanVietResultWebView.setContextMenuEnabled(false);
        pinyinResultWebView.setContextMenuEnabled(false);

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if(newTab == historyTab) {
                showHistory();
            }
//            System.out.println(newTab.getText());
        });

        hanResultWebView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) hanResultWebView.getEngine().executeScript("window");
                window.setMember("app", new JSConnector());
            }
        });

        hanVietResultWebView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) hanVietResultWebView.getEngine().executeScript("window");
                window.setMember("app", new JSConnector());
            }
        });

        pinyinResultWebView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) pinyinResultWebView.getEngine().executeScript("window");
                window.setMember("app", new JSConnector());
            }
        });
    }

//    public Tab getCurrentTab() {
//        return mainTabPane.getSelectionModel().getSelectedItem();
//    }

    /**
     * Tra cứu từ theo ký tự Hán
     */
    @FXML
    public void handleHanSearch() {
        String query = hanInputField.getText().trim();
        LookupEntry result = traHanService.search(query);

        if (result instanceof TraHanEntry) {
            TraHanEntry hanEntry = (TraHanEntry) result;
            String formatted;

            if (query.length() == 1) {
                formatted = TraHanTuDonResultFormatter.format(hanEntry);
            } else {
                formatted = TraHanTuGhepResultFormatter.format(hanEntry);
            }


            hanResultWebView.getEngine().loadContent(formatted);

            if(!isFromHistory) {
                String searchedQuery = query + " - " + result.getMeaning();
                searchHistoryService.addHistotry(searchedQuery, "han");
            }
            isFromHistory = false;
        } else {
            hanResultWebView.getEngine().loadContent("Không tìm thấy kết quả");
        }

        hanResultWebView.setVisible(true);
    }

    /**
     * Tra cứu theo âm Hán-Việt
     */
    @FXML
    public void handleHanVietSearch() {
        String query = hanVietInputField.getText().trim();

        LookupEntry result = traAmHanVietService.search(query);
//        System.out.println(result.getDisplayString());
        if(result == null) {
            hanVietResultWebView.getEngine().loadContent("Không tìm thấy kết quả");
            hanVietResultWebView.setVisible(true);
            return ;
        }


        StringBuilder displayResult = new StringBuilder();
        displayResult.append("<html><body style='font-size: 16px; padding: 5px; line-height: 1.6'>")
                .append("<p style='color: #4ec766; font-size: 24px; font-weight: semibold; margin: 0'>")
                .append(result.getKanji())
                .append(" (")
                .append(result.getMeaning())
                .append(")</p>");

        String[] contents = result.getContent().split("♦");
//        System.out.println(c);

        for(String content : contents) {
//            System.out.println(content);
            if(!content.isEmpty()) {
                StringBuilder wrappedLine = new StringBuilder();
                for(Character c : content.toCharArray()) {
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

                displayResult.append("<p style='margin: 0'>♦ ").append(wrappedLine).append("</p>");
            }

        }

        displayResult.append("</body></html>");
        System.out.println(displayResult.toString());

        hanVietResultWebView.getEngine().loadContent(displayResult.toString());
        hanVietResultWebView.setVisible(true);

        if(!isFromHistory) {
            searchHistoryService.addHistotry(query, "amHanViet");
        }
        isFromHistory = false;
    }

    @FXML
    public void handlePinyinSearch() {
        String query = pinyinInputField.getText().trim();

        List<String> pinyinHanziList = traPinyinService.searchPinyin(query);

        if(pinyinHanziList == null || pinyinHanziList.isEmpty()){
            pinyinResultWebView.getEngine().loadContent("Không tìm thấy kết quả");
            return ;
        }


        StringBuilder result = new StringBuilder();
        result.append("<html><body style='font-size: 16px; padding: 5px 10px; line-height: 1.6'>");
        for(String hanzi : pinyinHanziList){
            LookupEntry entry = traHanService.search(hanzi);

            if(entry instanceof TraHanEntry && entry.getContent() != null && !entry.getContent().isEmpty()){
                isContentExist = true;
                result.append("<div style='margin-bottom: 80px'>")
                        .append(TraHanTuDonResultFormatter.format((TraHanEntry) entry))
                        .append("</div>");
            }
        }
        result.append("</body></html>");

        if(!isContentExist) {
            pinyinResultWebView.getEngine().loadContent("Không tìm thấy kết quả");
            pinyinResultWebView.setVisible(true);
            return ;
        }

        pinyinResultWebView.getEngine().loadContent(result.toString());
        pinyinResultWebView.setVisible(true);

        if(!isFromHistory) {
            searchHistoryService.addHistotry(query, "pinyin");
        }
        isFromHistory = false;
//        isContentExist = false;
    }

    @FXML
    public void showHistory() {
        List<SearchHistoryEntry> historyList = searchHistoryService.getHistory();
        if(historyList == null || historyList.isEmpty()){
            historyListView.setVisible(false);
            historyNotiLabel.setVisible(true);
            return ;
        }

        historyListView.setVisible(true);
        historyNotiLabel.setVisible(false);

        historyListView.getItems().setAll(historyList);
    }

    private void setUpHistoryListView() {
        historyListView.setCellFactory(lv -> new ListCell<SearchHistoryEntry>() {
            @Override
            protected void updateItem(SearchHistoryEntry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    Text wordText = new Text(item.getQuery());
                    Text timeText = new Text(item.getTimeStamp());

                    HBox hBox = new HBox(wordText, new Region(), timeText);
                    HBox.setHgrow(hBox.getChildren().get(1), Priority.ALWAYS);

                    setGraphic(hBox);

                }
            }
        });

        historyListView.setOnMouseClicked(e -> {
            SearchHistoryEntry selected = (SearchHistoryEntry) historyListView.getSelectionModel().getSelectedItem();
            if(selected != null){
                isFromHistory = true;

                switchTab(selected.getQuery(), selected.getType());

            }
        });
    }

    private void switchTab(String query, String type) {
        switch (type) {
            case "pinyin":
                mainTabPane.getSelectionModel().select(pinyinTab);
                pinyinInputField.setText(query);
                handlePinyinSearch();
                break;
            case "han":
                mainTabPane.getSelectionModel().select(hanTab);
                hanInputField.setText(query.split(" - ")[0]);
                handleHanSearch();
                break;
            case "amHanViet":
                mainTabPane.getSelectionModel().select(hanvietTab);
                hanVietInputField.setText(query);
                handleHanVietSearch();
                break;
        }
    }

    public void switchToHanTab(String query) {
        if(currentTab == hanTab) {
            mainTabPane.getSelectionModel().select(hanTab);
            hanInputField.setText(query.split(" - ")[0]);
            handleHanSearch();
            return ;
        }
        switchTab(query, "han");
    }


    @FXML
    public void handleClearHistory() {
        searchHistoryService.deleteHistory();
        historyListView.getItems().clear();
        historyNotiLabel.setVisible(true);
        historyListView.setVisible(false);
    }

    @FXML
    public void clearAll() {
        Tab currentTab = mainTabPane.getSelectionModel().getSelectedItem();
        try {
            if(currentTab == hanTab) {
                hanInputField.clear();
                hanResultWebView.getEngine().loadContent("");
                hanResultWebView.setVisible(false);
            }
            else if(currentTab == hanvietTab) {
                hanVietInputField.clear();
                hanVietResultWebView.getEngine().loadContent("");
                hanVietResultWebView.setVisible(false);
            }
            else if(currentTab == pinyinTab) {
                pinyinInputField.clear();
                pinyinResultWebView.getEngine().loadContent("");
                pinyinResultWebView.setVisible(false);
            }

            prevQuery = "";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tab getCurrentTab() {
        return currentTab;
    }


}