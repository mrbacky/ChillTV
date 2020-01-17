package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import chilltv.gui.model.CategoryModel;
import chilltv.gui.model.MovieModel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LibraryController implements Initializable {

    @FXML
    private Button btn_openCatLib;
    @FXML
    private Button btn_addMovie;
    @FXML
    private Button btn_editMovie;
    @FXML
    private Button btn_deleteMovie;
    @FXML
    private TextField txt_movieSearch;
    @FXML
    private TextField txt_Cat;
    @FXML
    private Label lbl_Categories;
    @FXML
    private ImageView icon_search;
    //__________________________________________________________________________
    //  Library Table View
    //__________________________________________________________________________
    @FXML
    private TableView<Movie> tbv_library;
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
    private TableColumn<Movie, Integer> col_LastView;
    //__________________________________________________________________________
    //  Context Menu for Movie selection
    //__________________________________________________________________________
    @FXML
    private ContextMenu con_ContextMenu;
    @FXML
    private MenuItem menuItem_playMovie;
    @FXML
    private MenuItem menuItem_Edit;
    @FXML
    private MenuItem menuItem_Delete;
    //__________________________________________________________________________
    //private CheckMenuItem catItem;
    //private List<CheckMenuItem> catItemList = new ArrayList<>();
    private Menu menu_Category;
    @FXML
    private MenuButton menuButton_filterCategory;
    @FXML
    private MenuButton menuButton_filterRating;

    private Stage playerStage;
    //  Controllers
    private PlayerController playerController;
    private LibraryController libraryController;
    private PlayerController pCon;
    //  Models
    private MovieModel movieModel;
    private CategoryModel catModel;

    private Movie movie;
    private Category categoryToEdit;
    private boolean edit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edit = false;
        settingTableViews();

        setCategoriesIntoFilter();
        setRatingsIntoFilter();

        searchByTitle();
        filterCategory();
        filterRating();

        catModel.getInstance().getObsCategories().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                setCategoriesIntoFilter();
            }
        });

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Old movies");
        alert.setHeaderText("Not seen in more than 2 years");
        List<Movie> oldMovies = movieModel.getMoviesOlderThan(LocalDate.now().getYear() - 2);
        String movieString = "";

        for (Movie oldMovie : oldMovies) {
            movieString += oldMovie + "\n";
        }
        alert.setContentText(movieString);
        alert.showAndWait();

    }

    public void showScene(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //__________________________________________________________________________                       
    //
    //      setters
    //__________________________________________________________________________
    private void settingTableViews() {
        // get models
        movieModel = MovieModel.getInstance();
        catModel = CategoryModel.getInstance();

        //  set Library table view
        col_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_Category.setCellValueFactory(new PropertyValueFactory<>("stringCat"));
        col_Duration.setCellValueFactory(new PropertyValueFactory<>("stringDuration"));
        col_MyRating.setCellValueFactory(new PropertyValueFactory<>("myRating"));
        col_iMDBRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        col_LastView.setCellValueFactory(new PropertyValueFactory<>("lastView"));

        //  displaying content of library list
        tbv_library.setItems(movieModel.getObsMovies());
        //  loading all movies from DB
        movieModel.loadAllMovies();
        //  loading all categories from DB
        catModel.loadAllCategories();
    }

//    void setContr(LibraryController libraryController) {
//        this.libraryController = libraryController;
//    }

    public void setCategoriesIntoFilter() {
        //Get all categories.
        menuButton_filterCategory.getItems().clear();
        ObservableList<Category> allCategories = catModel.getObsCategories();
        //Add all the categories as created CheckMenuItems. Add MenuItems to the MenuButton.
        for (Category allCat : allCategories) {
            CheckMenuItem mi = new CheckMenuItem(allCat.toString());
            menuButton_filterCategory.getItems().add(mi);
            //Add userdata from the Category objects to the MenuItems.
            mi.setUserData(allCat);
        }
    }

    public void setRatingsIntoFilter() {
        //Create list for the rating filter.
        List<Integer> ratings = new ArrayList();
        int r = 0;
        for (int i = 0; i < 10; i++) {
            r++;
            ratings.add(r);
        }
        //System.out.println("ratings menu items are " + ratings);
        //Add all rating options to created CheckMenuItems. Add MenuItems to the MenuButton.
        for (Integer rat : ratings) {
            CheckMenuItem mi = new CheckMenuItem(rat.toString());
            menuButton_filterRating.getItems().add(mi);
        }
    }

    //__________________________________________________________________________                       
    //
    //      Scene openers - AddMovie, EditMovie, DeleteMovie, 
    //      Category Library Player
    //__________________________________________________________________________
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
        Movie selectedMovie = tbv_library.getSelectionModel().getSelectedItem();
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/MovieScene.fxml"));
        root = (Parent) fxmlLoader.load();

        MovieSceneController movieSceneController = (MovieSceneController) fxmlLoader.getController();
        movieSceneController.setContr(this);
        movieSceneController.editMode(selectedMovie);
        showScene(root);

    }

    @FXML
    public void handle_deleteMovie(ActionEvent event) throws IOException {

        Movie selectedMovie = tbv_library.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete movie error");
            alert.setHeaderText("Oh!\nyou did not select a movie to delete.");
            alert.showAndWait();
            Stage.getWindows().clear();

        } else {

            Parent root;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/DeleteMoviePopUp.fxml"));
            root = (Parent) fxmlLoader.load();
            DeleteMoviePopUpController controller = (DeleteMoviePopUpController) fxmlLoader.getController();
            controller.setContr(this);
            controller.setDeleteMovieLabel(selectedMovie);
            showScene(root);

        }
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
    private void handle_openPlayer(ActionEvent event) throws IOException, URISyntaxException {
        Movie selectedMovie = tbv_library.getSelectionModel().getSelectedItem();

        if (playerStage == null) {
            Parent root;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chilltv/gui/view/PlayerScene.fxml"));
            root = (Parent) fxmlLoader.load();
            playerController = (PlayerController) fxmlLoader.getController();
            fxmlLoader.<PlayerController>getController().setContr(this);
            playerController.setContr(this);

            playerStage = new Stage();
            Scene scene = new Scene(root);
            playerStage.setScene(scene);
        }

        playerController.playFile(selectedMovie, playerStage);
        playerStage.setAlwaysOnTop(true);
        playerStage.setAlwaysOnTop(false);
        selectedMovie.setLastView(LocalDate.now().getYear());
        //movieModel.updateMovie(selectedMovie);
        playerStage.show();

        //showScene(root);
    }

    //__________________________________________________________________________                       
    //
    //      Filter methods
    //__________________________________________________________________________
    private void searchByTitle() {
        txt_movieSearch.textProperty().addListener((observable) -> {
            movieModel.getAllMoviesFiltered(new Filter(txt_movieSearch.getText(), accessCategoriesinFilter(), accessRatinginFilter()));
        });
    }

    private List<Category> accessCategoriesinFilter() {
        List<Category> cats = new ArrayList();
        for (MenuItem item : menuButton_filterCategory.getItems()) {
            //If the CheckMenu item is selected, add it (with UserData) to the list). 
            if (((CheckMenuItem) item).isSelected()) {
                cats.add((Category) item.getUserData());
            }
        }
        return cats;
    }

    private float accessRatinginFilter() {
        float f = 0;
        for (MenuItem item : menuButton_filterRating.getItems()) {
            //If the CheckMenu item is selected, get the value and make it float. 
            if (((CheckMenuItem) item).isSelected()) {
                f = Float.parseFloat(item.getText());
            }
        }
        return f;
    }

    private void filterCategory() {
        //Get access to the CheckMenuItem in the MenuButton.
        for (MenuItem it : menuButton_filterCategory.getItems()) {
            //Listen to changes of the MenuItems.
            CheckMenuItem mi = (CheckMenuItem) it;
            mi.selectedProperty().addListener((observable, oldValue, newValue) -> {
                //Make list to store the categories to filter.
                List<Category> cats = new ArrayList();
                for (MenuItem item : menuButton_filterCategory.getItems()) {
                    //If the CheckMenu item is selected, add it (with UserData) to the list). 
                    if (((CheckMenuItem) item).isSelected()) {
                        cats.add((Category) item.getUserData());
                    }
                }
                System.out.println("check!!!!!!!!!!!!!");
                System.out.println("LibraryContr. in filterCatgory() f is " + accessRatinginFilter());

                movieModel.getAllMoviesFiltered(new Filter(txt_movieSearch.getText(), accessCategoriesinFilter(), accessRatinginFilter()));
            });
        }
    }

    private void filterRating() {
        //Get access to the CheckMenuItem in the MenuButton.
        for (MenuItem it : menuButton_filterRating.getItems()) {
            CheckMenuItem mi = (CheckMenuItem) it;
            //Listen to changes of the MenuItems.
            mi.selectedProperty().addListener((observable, oldValue, newValue) -> {
                for (MenuItem item : menuButton_filterRating.getItems()) {
                    //If the CheckMenu item is selected, get the value and make it float. 
                    if (((CheckMenuItem) item).isSelected()) {
                        float f = Float.parseFloat(item.getText());
                        System.out.println("LibraryContr. this is value of f in filterRating(): " + f);
                        movieModel.getAllMoviesFiltered(new Filter(txt_movieSearch.getText(), accessCategoriesinFilter(), f));
                        menuButton_filterRating.setText(item.getText());
                        //Deselect previous MenuItem.
                        mi.setSelected(false);
                    }
                }
            });
        }
    }
    
    public void refreshLibrary(){
        movieModel.loadAllMovies();

    }
}
