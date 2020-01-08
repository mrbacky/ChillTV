/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.util.Duration;

public class MovieSceneController implements Initializable {

    @FXML
    private TextField txtField_title;
    @FXML
    private ChoiceBox<?> choiceBox_Category;
    @FXML
    private Button btn_createVisible;
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
    @FXML
    private Button btn_createCategory;
    private LibraryController libraryController;
    private MovieModel movieModel;
    boolean edit = false;
    private Movie movieToEdit;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }
    
    
    @FXML
    private void handle_createVisible(ActionEvent event) {
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
    }

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
                    System.out.println(time);
                    txtField_duration.setText(movieModel.sec_To_Format(time));
                }
            });
        }
        
        
            
    }

    @FXML
    private void handle_saveMovie(ActionEvent event) {
        if (!edit) {
            movieModel.createMovie(movieee)
        }
    }
    
    public void editMode(Movie selectedMovie) {
        edit = true;
        movieToEdit = selectedMovie;
        
        txtField_title.setText(movieToEdit.getTitle());
        txtField_filePath.setText(movieToEdit.getFileLink());
        
    }
    
    @FXML
    private void handle_cancelMovieScene(ActionEvent event) {
    }

    @FXML
    private void handle_createCategory(ActionEvent event) {
    }

    
    
}
