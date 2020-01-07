/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MovieSceneController implements Initializable {

    @FXML
    private TextField txtField_title;
    @FXML
    private ChoiceBox<?> choiceBox_Category;
    @FXML
    private Button btn_createVisible;
    @FXML
    private Button btn_deleteCategory;
    @FXML
    private TextField txt_createCategory;
    @FXML
    private TextField txtField_duration;
    @FXML
    private TextField txtField_filePath;
    @FXML
    private Button btn_fileChooser;
    @FXML
    private Button btn_saveMovie;
    @FXML
    private Button btn_cancel;
    @FXML
    private Label lbl_Categories;
    @FXML
    private Button btn_createCategory;
    private LibraryController libraryController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }
    @FXML
    private void handle_createVisible(ActionEvent event) {
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
    }

    @FXML
    private void handle_openFileChooser(ActionEvent event) {
    }

    @FXML
    private void handle_saveMovie(ActionEvent event) {
    }

    @FXML
    private void handle_cancelMovieScene(ActionEvent event) {
    }

    @FXML
    private void handle_createCategory(ActionEvent event) {
    }

    
    
}
