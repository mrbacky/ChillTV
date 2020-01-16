package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.gui.model.CategoryModel;
import chilltv.gui.model.MovieModel;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private TextField txtField_title;
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

    private LibraryController libraryController;
    private MovieModel movieModel;
    private CategoryModel catModel;
    private boolean edit;
    private Movie movieToEdit;
    @FXML
    private ListView<Category> lv_categories;
    @FXML
    private TextField txtField_IMDbRating;
    @FXML
    private ComboBox<Category> comboBox_categories;
    @FXML
    private ComboBox<Integer> comboBox_rating;

    /**
     * Initializes the controller class. Upon initialization, the mode is set to
     * create a movie. The MovieModel and CategoryModel are initialized. All
     * categories are added to the ChoiceBox.
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

    private void loadCatsInComboBox() {
        ObservableList<Category> allCategories = catModel.getObsCategories();
        comboBox_categories.getItems().clear();
        for (Category cat : allCategories) {
            comboBox_categories.getItems().add(cat);
        }
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
     * Checks the selected mode (new or edit) and saves the movie.
     */
    @FXML
    private void handle_saveMovie(ActionEvent event) {
        if (!edit) {
            //  converting String entry to float
            float imdbRatingFloat = Float.parseFloat(txtField_IMDbRating.getText());
            List<Category> catsToAdd = lv_categories.getItems();
            System.out.println("cats to add  "+catsToAdd);
            movieModel.createMovie(
                                    
                    /*title*/       txtField_title.getText().trim(),
                    /*duration*/    movieModel.format_To_Sec(txtField_duration.getText()),
                    /*myRatin*/     comboBox_rating.getValue(), //imdbRating TO DO!!
                    /*imdbRating*/  imdbRatingFloat, //myRating TO DO!!
                    /*fileLink*/    txtField_filePath.getText(),
                    /*lastView*/    "2018",
                    /*categoryList*/catsToAdd); //lastView TO DO!!
        } //movieModel.createMovie(title, 0, 0, 0, filelink, lastView);
        else {
            movieToEdit.setTitle(txtField_title.getText().trim());
            //not getting the time of the new file T-T
            movieToEdit.setDuration(movieModel.format_To_Sec(txtField_duration.getText()));
            //category
            movieToEdit.setImdbRating(5); //imdbRating TO DO!!
            movieToEdit.setMyRating(3); //myRating TO DO!!
            movieToEdit.setFileLink(txtField_filePath.getText());
            movieToEdit.setLastView("2020"); //lastView TO DO!!
            movieModel.updateMovie(movieToEdit);
        }

        Stage stage;
        stage = (Stage) btn_saveMovie.getScene().getWindow();
        stage.close();
    }

    /**
     * Enables the edit mode, so the movie can be edited. The existing info of
     * the selected movie is displayed.
     *
     * @param selectedMovie The movie to edit.
     */
    public void editMode(Movie selectedMovie) {
        edit = true;
        movieToEdit = selectedMovie;

        //sets the existing info of the selected movie.
        txtField_title.setText(movieToEdit.getTitle());
        //category TO DO!!
        txtField_duration.setText(movieToEdit.getStringDuration());
        txtField_filePath.setText(movieToEdit.getFileLink());
    }

    /**
     * Closes the stage.
     */
    @FXML
    private void handle_cancelMovieScene(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handle_setCatToLV(ActionEvent event) {
        Category selectedCategory = comboBox_categories.getSelectionModel().getSelectedItem();
        if (!lv_categories.getItems().contains(selectedCategory)) {
            lv_categories.getItems().add(selectedCategory);
        }

    }

    @FXML
    private void handle_removeCatItem(MouseEvent event) {
        Category selectedCategory = lv_categories.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            lv_categories.getItems().remove(selectedCategory);

        }
    }

    private void loadRating() {

        for (int i = 0; i < 10; i++) {
            comboBox_rating.getItems().add(i+1);
        }
    }

}
