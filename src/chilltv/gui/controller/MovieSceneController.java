package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.gui.model.CategoryModel;
import chilltv.gui.model.MovieModel;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The MovieSceneController is the controller for the MovieScene. It sends
 * requests to the MovieModel when creating and updating a movie. The default
 * mode is to create a movie. The mode can be changed to edit a movie.
 *
 * @author annem
 */
public class MovieSceneController implements Initializable {

    @FXML
    private Label lbl_movie;
    @FXML
    private TextField txtField_title;
    @FXML
    private TextField txtField_duration;
    @FXML
    private TextField txtField_imdbRating;
    @FXML
    private TextField txtField_filePath;
    @FXML
    private Button btn_fileChooser;
    @FXML
    private Button btn_saveMovie;
    @FXML
    private Button btn_cancel;
    @FXML
    private ListView<Category> lv_categories;
    @FXML
    private ComboBox<Category> comboBox_categories;
    @FXML
    private ComboBox<Integer> comboBox_rating;

    private LibraryController libraryController;
    private MovieModel movieModel;
    private CategoryModel catModel;

    private boolean edit;
    private Movie movieToEdit;
    private List<Category> oldCategoryList;

    ObservableList<Category> catObsList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. Upon initialization, the mode is set to
     * create a movie. The MovieModel and CategoryModel are initialized. All
     * categories and personal ratings are added to the ComboBoxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edit = false;
        catModel = CategoryModel.getInstance();
        movieModel = MovieModel.getInstance();
        loadCatsInComboBox();
        loadRating();

        catModel.getInstance().getObsCategories().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                loadCatsInComboBox();
            }
        });
    }

    /**
     * Sets the controller for the LibraryScene
     *
     * @param libraryController LibraryController.
     */
    public void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    /**
     * Loads the ComboBox options for categories.
     */
    private void loadCatsInComboBox() {
        ObservableList<Category> allCategories = catModel.getObsCategories();
        comboBox_categories.getItems().clear();
        for (Category cat : allCategories) {
            comboBox_categories.getItems().add(cat);
        }
    }

    /**
     * Loads the ComboBox options for ratings.
     */
    private void loadRating() {
        for (int i = 0; i < 10; i++) {
            comboBox_rating.getItems().add(i + 1);
        }
    }

    /**
     * Uses the FileChooser class to choose a file for the movie. The duration
     * of the movie is automatically added for the user.
     */
    @FXML
    private void handle_openFileChooser(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp4 Files", "*.mp4"),
                new FileChooser.ExtensionFilter("mpeg4 Files", "*.mpeg4")
        );

        File movieFile = fileChooser.showOpenDialog(null);
        if (movieFile != null) {
            String moviePath = movieFile.getAbsolutePath();
            txtField_filePath.setText(moviePath);
            Media media = new Media(movieFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration movieDuration = media.getDuration();
                    if (movieDuration != null) {
                        int time = (int) (movieDuration.toSeconds());
                        String stringTime = movieModel.sec_To_Format(time);
                        //  bug: stringTime is not showing sometimes after choosing file
                        txtField_duration.setText(stringTime);
                    }

                }
            });
        }
    }

    /**
     * Adds a category chosen by the user in the ComboBox to the ListView of
     * categories.
     */
    @FXML
    private void handle_setCatToLV(ActionEvent event) {
        Category selectedCategory = comboBox_categories.getSelectionModel().getSelectedItem();
        if (!lv_categories.getItems().contains(selectedCategory)) {
            lv_categories.getItems().add(selectedCategory);
        }
    }

    /**
     * Removes a category chosen by the user from the ListView of categories.
     */
    @FXML
    private void handle_removeCatItem(MouseEvent event) {
        Category selectedCategory = lv_categories.getSelectionModel().getSelectedItem();
        lv_categories.getItems().remove(selectedCategory);
    }

    /**
     * Enables the edit mode, so the movie can be edited. The existing info of
     * the selected movie is displayed.
     *
     * @param selectedMovie The movie to edit.
     */
    public void editMode(Movie selectedMovie) {

        if (selectedMovie == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit movie error");
            alert.setHeaderText("Oh no!\nYou did not select a movie to edit.");
            alert.showAndWait();
            Stage.getWindows().clear();

        } else {
            edit = true;
            movieToEdit = selectedMovie;

            // Set existing title.
            txtField_title.setText(movieToEdit.getTitle());

            // Set existing category list.
            oldCategoryList = movieToEdit.getCategoryList();
            catObsList.clear();
            catObsList.addAll(oldCategoryList);

            lv_categories.setItems(catObsList);

            // myRating
            comboBox_rating.setValue(movieToEdit.getMyRating());

            // IMDbRating
            String imdbRatingToEdit = String.valueOf(movieToEdit.getImdbRating());
            txtField_imdbRating.setText(imdbRatingToEdit);

            // duration
            txtField_duration.setText(movieToEdit.getStringDuration());

            // fileLink
            txtField_filePath.setText(movieToEdit.getFileLink());
        }
    }

    /**
     * Checks the selected mode (new or edit) and saves the movie.
     */
    @FXML
    private void handle_saveMovie(ActionEvent event) {

        if (txtField_title.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add title error");
            alert.setHeaderText("Oh no!\nYou forgot to set the title.");

            alert.showAndWait();

        }
        if (comboBox_categories.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add category error");
            alert.setHeaderText("Oh no!\nYou forgot to set the categories.");

            alert.showAndWait();
        }
        if (txtField_filePath.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add file location error");
            alert.setHeaderText("Oh no!\nYou forgot to add the file location of the movie.");

            alert.showAndWait();
        }
        if (comboBox_rating.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add rating error");
            alert.setHeaderText("Oh no!\nYou forgot to add your rating for the movie.");

            alert.showAndWait();
        }
        if (txtField_imdbRating.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add IMDb rating error");
            alert.setHeaderText("Oh no!\nYou forgot to add the IMDb rating for the movie.");

            alert.showAndWait();
        }

        if (!edit) {
            // Convert the String entry to float
            float imdbRatingFloat = Float.parseFloat(txtField_imdbRating.getText());

            List<Category> catsToAdd = lv_categories.getItems();

            movieModel.createMovie(
                    /*title*/
                    txtField_title.getText().trim(),
                    /*duration*/
                    movieModel.format_To_Sec(txtField_duration.getText()),
                    /*myRating*/
                    comboBox_rating.getValue(), //imdbRating TO DO!!
                    /*imdbRating*/
                    imdbRatingFloat, //myRating TO DO!!
                    /*fileLink*/
                    txtField_filePath.getText(),
                    /*lastView*/
                    0,
                    /*categoryList*/
                    catsToAdd);
        } else {
            /*title*/
            movieToEdit.setTitle(txtField_title.getText().trim());
            /*duration*/
            movieToEdit.setDuration(movieModel.format_To_Sec(txtField_duration.getText()));
            /*myRating*/
            movieToEdit.setMyRating(3);
            /*imdbRating*/
            movieToEdit.setImdbRating(5);
            /*fileLink*/
            movieToEdit.setFileLink(txtField_filePath.getText());
            /*lastView*/
            movieToEdit.setLastView(LocalDate.now().getYear());
            /*categoryList*/
            movieToEdit.setCategoryList(lv_categories.getItems());
            //Use the new values and the old category list for the update method.
            movieModel.updateMovie(movieToEdit, oldCategoryList);
        }

        Stage stage;
        stage = (Stage) btn_saveMovie.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the stage.
     */
    @FXML
    private void handle_cancelMovieScene(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
}
