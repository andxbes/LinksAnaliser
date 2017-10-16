/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.andxbes.links_analiser.browser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ua.andxbes.links_analiser.LinksAnaliser;

/**
 *
 * @author andr
 */
public class Top_20_Google {

    protected static final Map<String, List<String>> top = new HashMap<>();

    protected final static String SEARCH_MASHINE = "https://www.google.com/search";
    protected final static int PER_PEGE = 20;

    private String query;
    private boolean autoclose;
    private Stage window ;
    
    public Top_20_Google(String query) {
        this(query, true);
    }

    public Top_20_Google(String query, boolean autoclouse){
        this.autoclose = autoclouse;
        this.query = query;
        this.createBrauserWindow();
    }

    public boolean get_autoclose() {
        return this.autoclose;
    }

    protected void createBrauserWindow() {

        this.window = new Stage();
        StackPane pane = new StackPane();

        WebView wview = new WebView();
        initWebView(wview);

        pane.getChildren().add(wview);
        Scene scene = new Scene(pane, 400, 400);

        this.window.setScene(scene);
        this.window.setTitle(this.query);
        this.window.show();
    }

    private void initWebView(WebView wview){
        String queryLink;
         
        wview.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        WebEngine we = wview.getEngine();
        try {
            queryLink = this.getQueryLink();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Top_20_Google.class.getName()).log(Level.SEVERE, null, ex);
            queryLink="";
        }
        
        we.load(queryLink);
        we.documentProperty().addListener(new ChangeListener<Document>() {
            @Override
            public void changed(ObservableValue<? extends Document> observable, Document oldValue, Document newValue) {
                if (newValue != null) {
                    NodeList list = newValue.getElementsByTagName("a");

                    List<String> links = new ArrayList<>();
                    for (int i = 0; i < list.getLength(); i++) {
                        Element node = (Element) list.item(i);
                        links.add(node.getAttribute("href"));
                    }
                    if (links.size() > 0) {
                        Top_20_Google.top.put(Top_20_Google.this.query, links);
                    }

                    if (Top_20_Google.this.autoclose) {
                        Top_20_Google.this.window.close();
                    }
                }
            }
        });
    }
    
    private String getQueryLink() throws UnsupportedEncodingException{
        String url_query = URLEncoder.encode(this.query, LinksAnaliser.CHARSET);
        String query_link = Top_20_Google.SEARCH_MASHINE + "?q=" + url_query + "&num=" + Top_20_Google.PER_PEGE + "&ie=UTF-8&lr=lang_ru"; 
        return query_link;
    }
    
    
    
    
    
}
