package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.gui.model.CategoryModel;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LibraryController implements Initializable {

    @FXML
    private Button btn_addMovie;
    @FXML
    private ImageView icon_search;
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
    private TextField txt_movieSearch;

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

    @FXML
    private TableView<Category> tbv_Categories;
    @FXML
    private TableColumn<Category, String> col_Name;
    @FXML
    private TableColumn<Category, Integer> col_numOfMovies;

    @FXML
    private Button btn_addCategoryVisible;
    @FXML
    private TextField txt_Cat;
    @FXML
    private Button btn_saveCategory;
    @FXML
    private Button btn_editCategory;
    @FXML
    private Button btn_deleteCategory;

    @FXML
    private ListView<Movie> lv_Category;

    private PrimaryController pCon;
    private Movie movie;
    private MovieModel movieModel;
    private CategoryModel catModel;
    private boolean edit;
    private Category categoryToEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settingTableViews();
        edit = false;

    }

    private void settingTableViews() {
        movieModel = MovieModel.getInstance();
        catModel = CategoryModel.getInstance();

        //  Library table view
        col_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_Duration.setCellValueFactory(new PropertyValueFactory<>("stringDuration"));
        col_MyRating.setCellValueFactory(new PropertyValueFactory<>("myRating"));
        col_iMDBRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        col_LastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));
        //  displaying content
        tbv_Movies.setItems(movieModel.getObsMovies());
        movieModel.loadAllMovies();

        //  Category table view
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_numOfMovies.setCellValueFactory(new PropertyValueFactory<>("numberOfMovies"));
        // In order to display, set reference to one ObservableList.
        tbv_Categories.setItems(catModel.getObsCategories());
        catModel.loadAllCategories();
    }

    private void getMoviesInCategory() {
        ObservableList<Movie> moviesInPlaylist = FXCollections.observableArrayList();
        moviesInPlaylist.clear();
        moviesInPlaylist.addAll(tbv_Categories.getSelectionModel().getSelectedItem().getMovies());
        lv_Category.setItems(moviesInPlaylist);
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
    private void handle_deleteMovie(ActionEvent event) throws IOException {
        Movie selectedMovie = tbv_Movies.getSelectionModel().getSelectedItem();
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/DeleteMoviePopUp.fxml"));
        root = (Parent) fxmlLoader.load();
        DeleteMoviePopUpController controller = (DeleteMoviePopUpController) fxmlLoader.getController();
        controller.setContr(this);
        controller.setDeleteMovieLabel(selectedMovie);

        Stage songStage = new Stage();
        Scene songScene = new Scene(root);

        //songStage.initStyle(StageStyle.UNDECORATED);
        songStage.setScene(songScene);
        songStage.show();
    }

    @FXML
    private void handle_addCategoryVisible(ActionEvent event) {
        txt_Cat.setVisible(true);
        btn_saveCategory.setVisible(true);
    }

    @FXML
    private void handle_editCategory(ActionEvent event) {
        edit = true;
        txt_Cat.setVisible(true);
        btn_saveCategory.setVisible(true);
        Category selectedCategory = tbv_Categories.getSelectionModel().getSelectedItem();
        //sets the existing info of the selected playlist.
        txt_Cat.setText(selectedCategory.getName());
    }

    @FXML
    public void handle_saveCategory(ActionEvent event) {
        if (!edit) {
            Category category = new Category(0, txt_Cat.getText().trim());
            catModel.createCategory(category);
        } else {
            //update the edited name
            Category categoryToEdit = tbv_Categories.getSelectionModel().getSelectedItem();
            categoryToEdit.setName(txt_Cat.getText().trim());
            catModel.updateCategory(categoryToEdit);
        }

        txt_Cat.setVisible(false); //makes the textfield invisible.
        btn_saveCategory.setVisible(false); //makes the button invisible.
    }
    
    @FXML
    private void handle_deleteCategory(ActionEvent event) throws IOException {
        Category selectedCategory = tbv_Categories.getSelectionModel().getSelectedItem();
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/DeleteCategoryPopUp.fxml"));
        root = (Parent) fxmlLoader.load();
        DeleteCategoryPopUpController controller = (DeleteCategoryPopUpController) fxmlLoader.getController();
        controller.setContr(this);
        controller.setDeleteCategoryLabel(selectedCategory);

        Stage songStage = new Stage();
        Scene songScene = new Scene(root);

        //songStage.initStyle(StageStyle.UNDECORATED);
        songStage.setScene(songScene);
        songStage.show();
    }

    @FXML
    private void handle_getCategoryContent(MouseEvent event) {
        Category selectedCategory = tbv_Categories.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            getMoviesInCategory();
            lbl_Category.setText(selectedCategory.getName());
        }
    }
}
