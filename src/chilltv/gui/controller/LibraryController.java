package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    @FXML
    private TableColumn<?, String> col_Name;
    @FXML
    private TableColumn<?, Integer> col_numOfMovies;
    @FXML
    private TableColumn<?, String> col_Title;
    @FXML
    private TableColumn<?, String> col_Category;
    @FXML
    private TableColumn<?, String> col_Duration;
    @FXML
    private TableColumn<?, String> col_MyRating;
    @FXML
    private TableColumn<?, Integer> col_iMDBRating;
    @FXML
    private TableColumn<?, String> col_LastView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //settingTableViews();
    }

    private void settingTableViews() {
        //  Category table view
        //  col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        //  Library table view
        col_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_Category.setCellValueFactory(new PropertyValueFactory<>("no sure"));
        col_Duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col_MyRating.setCellValueFactory(new PropertyValueFactory<>("myRating"));
        col_iMDBRating.setCellValueFactory(new PropertyValueFactory<>("imbdRating"));
        
        

    }

    void setContr(PrimaryController pCon) {
        this.pCon = pCon;
    }

    //  openCreateMovie
    //  saveCreateMovie
    //  openCreateCategory
    //  saveCreateCategory
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
