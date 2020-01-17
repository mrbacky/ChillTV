package chilltv.gui.controller;

import chilltv.be.Movie;
import chilltv.gui.model.CategoryModel;
import chilltv.gui.model.MovieModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller class for the DeleteMoviePopUpScene. It sends requests to the
 * MovieModel when deleting a movie.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
 */
public class DeleteMoviePopUpController implements Initializable {

    @FXML
    private Label lbl_movieTitle;
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_cancel;

    private LibraryController libCon;
    private MovieModel movieModel;
    private CategoryModel catModel;
    private Movie selectedMovie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movieModel = MovieModel.getInstance();
    }

    /**
     * Sets the controller for the LibraryScene.
     *
     * @param libCon LibraryController
     */
    public void setContr(LibraryController libCon) {
        this.libCon = libCon;
    }

    /**
     * Sets the title of the selected movie on a label.
     *
     * @param movie The movie to delete.
     */
    public void setDeleteMovieLabel(Movie movie) {
         
       
        selectedMovie = movie;
        lbl_movieTitle.setText(selectedMovie.getTitle());
    }

    @FXML
    private void handle_confirmDeleteMovie(ActionEvent event) {
        //Deletes the selected movie from the database.
        movieModel.deleteMovie(selectedMovie);
        
    

        Stage stage;
        stage = (Stage) btn_confirm.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the stage.
     */
    @FXML
    private void handle_cancelDeleteMovie(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
}
