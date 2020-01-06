
package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LibraryController implements Initializable {

    @FXML
    private Button btn_addMovie;

    private PrimaryController pCon;
    @FXML
    private TableView<?> tbv_Categories;
    @FXML
    private TableView<?> tbv_Movies;
    @FXML
    private ListView<?> lv_Category;
    @FXML
    private Label lbl_Categories;
    @FXML
    private TextField txt_catSearch;
    @FXML
    private Label lbl_Category;
    @FXML
    private Label lbl_Library;
    @FXML
    private Button btn_editMovie;
    @FXML
    private Button btn_deleteMovie;
    @FXML
    private Button btn_addCategory;
    @FXML
    private Button btn_editCategory;
    @FXML
    private Button btn_deleteCategory;
    @FXML
    private TextField txt_movieSearch;
    @FXML
    private TextField txt_Cat;
    @FXML
    private Button btn_saveCategory;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setContr(PrimaryController pCon) {
        this.pCon = pCon;
    }

    @FXML
    private void btn_openAddMovie(ActionEvent event) {
    }

    @FXML
    private void handle_openEditMovie(ActionEvent event) {
    }

    @FXML
    private void handle_deleteMovie(ActionEvent event) {
    }

    @FXML
    private void handle_addCategory(ActionEvent event) {
    }

    @FXML
    private void handle_editCategory(ActionEvent event) {
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
    }

    @FXML
    private void handle_saveCategory(ActionEvent event) {
    }
    
}
