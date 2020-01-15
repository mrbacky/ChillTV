package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.gui.model.CategoryModel;
import chilltv.gui.model.MovieModel;
import java.awt.color.ICC_ColorSpace;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LibraryController implements Initializable {

    @FXML
    private Button btn_addMovie;
    @FXML
    private ImageView icon_search;
    @FXML
    private Label lbl_Categories;
    private TextField txt_catSearch;
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

    private TableView<Category> tbv_Categories;
    private TableColumn<Category, String> col_Name;
    private TableColumn<Category, Integer> col_numOfMovies;

    @FXML
    private TextField txt_Cat;
    private Button btn_saveCategory;

    private PlayerController pCon;
    private Movie movie;
    private MovieModel movieModel;
    private CategoryModel catModel;
    private Category categoryToEdit;
    @FXML
    private ContextMenu con_ContextMenu;
    @FXML
    private MenuItem menuItem_Edit;
    @FXML
    private MenuItem menuItem_Delete;
    @FXML
    private SeparatorMenuItem separator;
    @FXML
    private MenuItem menuItem_playMovie;
    @FXML
    private Menu menu_Category;
    private RadioMenuItem rawAction;
    @FXML
    private CheckMenuItem rawHorror;
    private List<CheckMenuItem> catItemList = new ArrayList<>();
    private CheckMenuItem catItem;
    @FXML
    private Button btn_openCatLib;
    private LibraryController libraryController;
    @FXML
    private Button btn_refresh;
    private boolean edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edit = false;
        settingTableViews();
        //setSearchMovies();
        //setSearchCategories();

        loadCategoriesIntoMenu();
        //setCheckedCategoriesForMovie();

        catModel.getInstance().getObsCategories().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //catModel.loadAllCategories();
                loadCategoriesIntoMenu();
                //setCheckedCategoriesForMovie();
            }
        });

    }

    public void loadCategoriesIntoMenu() {
        ObservableList<Category> allCategories = catModel.getObsCategories();
        System.out.println("these are all cats   "+allCategories);
//        menu_Category.getItems().clear();
//        int counter = 0;
//        for (CheckMenuItem checkMenuItem : catItemList) {
//            for (Category cat : catModel.getObsCategories()) {
//                catItemList.add(new CheckMenuItem(cat.toString()));
//                //System.out.println(catItemList.get(0));
//                counter++;
//
//            }
//        }

        
    }

    @FXML
    private void handle_loadCheckedCategories(MouseEvent event) {
        /*
        Movie selectedMovie = tbv_Movies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            for (Category cat : catModel.getObsCategories()) {
                for (Movie mov : cat.getMovies()) {
                    for (Category movieCat : mov.getCategory()) {
                        if (movieCat.getName().equals(cat.getName())) {

                        }
                    }
                }

                if (cat.getMovies().contains(selectedMovie)) {
                    catItem.setSelected(true);
                    System.out.println("asd");
                }
            }
        }*/
    }

    public void setCheckedCategoriesForMovie() {
        //  get selected movie. get its categories checked
        Movie selectedMovie = tbv_Movies.getSelectionModel().getSelectedItem();
        //catItem.selectedProperty().addListener(((observable, oldValue, newValue) -> {
        for (Category cat : catModel.getObsCategories()) {
            if (cat.getMovies().contains(selectedMovie)) {
                catItem.setSelected(true);
            }

        }

    }

    public void showScene(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        catModel.loadAllCategories();
        

        //  content of listView is displayed after choosing category - handle_getCategoryContent
    }

    private void setSearchMovies() {
        //Set the filter Predicate when the filter changes. Any changes to the
        //search textfield activates the filter.
        txt_movieSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            movieModel.filteredMovies(newVal);
        });
    }

    private void setSearchCategories() {
        //Set the filter Predicate when the filter changes. Any changes to the
        //search textfield activates the filter.
        txt_catSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            catModel.filteredCategories(newVal);
        });
    }

    void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    @FXML
    private void btn_openAddMovie(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/MovieScene.fxml"));
        root = (Parent) fxmlLoader.load();
        fxmlLoader.<MovieSceneController>getController().setContr(this);
        showScene(root);
    }

    @FXML
    private void handle_openEditMovie(ActionEvent event) throws IOException {
        Movie selectedMovie = tbv_Movies.getSelectionModel().getSelectedItem();
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/MovieScene.fxml"));
        root = (Parent) fxmlLoader.load();

        MovieSceneController movieSceneController = (MovieSceneController) fxmlLoader.getController();
        movieSceneController.setContr(this);
        movieSceneController.editMode(selectedMovie);
        showScene(root);

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
        showScene(root);

    }

//    private void handle_addCategoryVisible(ActionEvent event) {
//        txt_Cat.setVisible(true);
//        btn_saveCategory.setVisible(true);
//    }
    private void handle_editCategory(ActionEvent event) {
        edit = true;
        txt_Cat.setVisible(true);
        btn_saveCategory.setVisible(true);
        Category selectedCategory = tbv_Categories.getSelectionModel().getSelectedItem();
        //sets the existing info of the selected playlist.
        txt_Cat.setText(selectedCategory.getName());
    }

    

    private void handle_deleteCategory(ActionEvent event) throws IOException {
        Category selectedCategory = tbv_Categories.getSelectionModel().getSelectedItem();
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/DeleteCategoryPopUp.fxml"));
        root = (Parent) fxmlLoader.load();
        DeleteCategoryPopUpController controller = (DeleteCategoryPopUpController) fxmlLoader.getController();
        controller.setContr(this);
        controller.setDeleteCategoryLabel(selectedCategory);
        showScene(root);

    }

    @FXML
    private void handle_openPlayer(ActionEvent event) throws IOException, URISyntaxException {
        Movie selectedMovie = tbv_Movies.getSelectionModel().getSelectedItem();
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/PlayerScene.fxml"));
        root = (Parent) fxmlLoader.load();
        PlayerController playerController = (PlayerController) fxmlLoader.getController();

        playerController.setContr(this);

        playerController.playFile(selectedMovie);
        showScene(root);

    }

    @FXML
    private void handle_openCatLib(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/CategoryScene.fxml"));
        root = (Parent) fxmlLoader.load();
        CategorySceneController controller = (CategorySceneController) fxmlLoader.getController();
        fxmlLoader.<CategorySceneController>getController().setContr(this);
        showScene(root);

    }

    @FXML
    private void handle_refresh(ActionEvent event) {
        movieModel.loadAllMovies();
    }

}
