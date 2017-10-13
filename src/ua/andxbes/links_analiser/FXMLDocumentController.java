/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.andxbes.links_analiser;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FXMLDocumentController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="menu_file_group"
    private Menu menu_file_group; // Value injected by FXMLLoader

    @FXML // fx:id="menu_button_save"
    private MenuItem menu_button_save; // Value injected by FXMLLoader

    @FXML // fx:id="menu_button_about"
    private MenuItem menu_button_about; // Value injected by FXMLLoader

    @FXML // fx:id="button_start"
    private Button button_start; // Value injected by FXMLLoader

    @FXML // fx:id="button_stop"
    private Button button_stop; // Value injected by FXMLLoader

    @FXML // fx:id="text_searching_phrases"
    private TextArea text_searching_phrases; // Value injected by FXMLLoader

    @FXML // fx:id="table_general"
    private TableView<?> table_general; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_st"
    private TableColumn<?, ?> table_row_st; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_site"
    private TableColumn<?, ?> table_row_site; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_mysearcher"
    private TableColumn<?, ?> table_row_mysearcher; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_external"
    private TableColumn<?, ?> table_row_external; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_follower"
    private TableColumn<?, ?> table_row_follower; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_nofollow"
    private TableColumn<?, ?> table_row_nofollow; // Value injected by FXMLLoader

    @FXML // fx:id="table_row_price"
    private TableColumn<?, ?> table_row_price; // Value injected by FXMLLoader

    @FXML
    void buton_start_click(MouseEvent event) {

    }

    @FXML
    void buton_stop_click(MouseEvent event) {

    }

    @FXML
    void button_save(ActionEvent event) {

    }

    @FXML
    void menu_about(ActionEvent event) {

    }
    @Override
    @FXML // This method is called by the FXMLLoader when initialization is complete
    
    
    // Получаем данные из другого источника 
    // https://www.linkpad.ru/default.aspx?r=16&i=agsat.com.ua#/default.aspx?r=3&i=agsat.com.ua
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
