package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CategorySceneController implements Initializable {

    @FXML
    private Label lbl_Categories;
    @FXML
    private TextField txt_createCategory;
    @FXML
    private Label lbl_newCat;
    @FXML
    private Button btn_createCat;
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_cancel;
    private LibraryController libCon;
    @FXML
    private Button btn_addCategoryVisible;
    @FXML
    private Button btn_deleteCat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handle_saveCategory(ActionEvent event) {

    }

    @FXML
    private void handle_confirmCat(ActionEvent event) {
    }

    @FXML
    private void handle_cancelScene(ActionEvent event) {
    }

    void setContr(LibraryController libCon) {
        this.libCon = libCon;
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
    }

    

    @FXML
    private void handle_addCategoryVisible(ActionEvent event) {
        btn_createCat.setVisible(true);
        lbl_newCat.setVisible(true);
        txt_createCategory.setVisible(true);
    }

}
