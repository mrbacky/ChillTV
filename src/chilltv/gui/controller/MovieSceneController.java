package chilltv.gui.controller;

import chilltv.be.Movie;
import chilltv.gui.model.MovieModel;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private ChoiceBox<String> choiceBox_Category;
    @FXML
    private Button btn_createVisible;
    @FXML
    private Button btn_createCategory;
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

    private LibraryController libraryController;
    private MovieModel movieModel;
    private boolean edit;
    private Movie movieToEdit;

    /**
     * Initializes the controller class. Upon initialization, the mode is set to
     * create a movie. The MovieModel and CategoryModel are initialized. All
     * categories are added to the ChoiceBox.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edit = false;
        movieModel = MovieModel.getInstance();
        //catModel = CategoryModel.getInstance();
    }

    /**
     * Sets the controller for the LibraryScene
     *
     * @param libraryController LibraryController.
     */
    public void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    @FXML
    private void handle_createVisible(ActionEvent event) {
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
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
                    int time, hours, mins, secs;
                    Duration movieDuration = media.getDuration();
                    time = (int) (movieDuration.toSeconds());
                    txtField_duration.setText(movieModel.sec_To_Format(time));
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
            movieModel.createMovie(
                    txtField_title.getText().trim(),
                    movieModel.format_To_Sec(txtField_duration.getText()),
                    //category TO DO!!
                    9, //imdbRating TO DO!!
                    7, //myRating TO DO!!
                    txtField_filePath.getText(),
                    "2018"); //lastView TO DO!!
        } else {
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
    private void handle_createCategory(ActionEvent event) {
    }
}