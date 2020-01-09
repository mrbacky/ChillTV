package chilltv.gui.controller;

import chilltv.be.Movie;
import chilltv.gui.model.MovieModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LibraryController implements Initializable {

    @FXML
    private Button btn_addMovie;
    @FXML
    private ImageView icon_search;
    @FXML
    private TableView<?> tbv_Categories;
   
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
    private TableView<Movie> tbv_Movies;
    @FXML
    private TableColumn<Movie, String> col_Title;
    @FXML
    private TableColumn<Movie, String> col_Category;
    @FXML
    private TableColumn<Movie, Integer> col_Duration;
    @FXML
    private TableColumn<Movie, Integer> col_MyRating;
    @FXML
    private TableColumn<Movie, Integer> col_iMDBRating;
    @FXML
    private TableColumn<Movie, String> col_LastView;    //LocalDateTime
    
    
    private PrimaryController pCon;
    private Movie movie;
    private MovieModel movieModel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settingTableViews();
        
    }

    private void settingTableViews() {
        movieModel =  MovieModel.getInstance();
        
        //  Category table view
        //  col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        //  Library table view
        col_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        //col_Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_Duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        col_MyRating.setCellValueFactory(new PropertyValueFactory<>("myRating"));
        col_iMDBRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        col_LastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));
        //  displaying content
        
        
        tbv_Movies.setItems(movieModel.getObsMovies());
        movieModel.loadAllMovies();
        
        
        
        
    }

    void setContr(PrimaryController pCon) {
        this.pCon = pCon;
    }

    //  openCreateMovie
    //  saveCreateMovie
    //  openCreateCategory
    //  saveCreateCategory
    @FXML
    private void btn_openAddMovie(ActionEvent event) throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/MovieScene.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<MovieSceneController>getController().setContr(this);

        Stage libraryStage = new Stage();
        Scene libraryScene = new Scene(root1);

        //songStage.initStyle(StageStyle.UNDECORATED);
        libraryStage.setScene(libraryScene);
        libraryStage.show();

    }

    @FXML
    private void handle_openEditMovie(ActionEvent event) throws IOException {
        Movie selectedMovie = tbv_Movies.getSelectionModel().getSelectedItem();
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/MovieScene.fxml"));
        root1 = (Parent) fxmlLoader.load();
        
        MovieSceneController controller = (MovieSceneController) fxmlLoader.getController();
        controller.setContr(this);
        controller.editMode(selectedMovie);

        Stage libraryStage = new Stage();
        Scene libraryScene = new Scene(root1);

        //songStage.initStyle(StageStyle.UNDECORATED);
        libraryStage.setScene(libraryScene);
        libraryStage.show();
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
