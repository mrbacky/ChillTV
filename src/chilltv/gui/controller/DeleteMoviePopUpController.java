
package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeleteMoviePopUpController implements Initializable {

    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_cancel;
    @FXML
    private Label lbl_movieTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void handle_confirmDeleteMovie(ActionEvent event) {
    }

    @FXML
    private void handle_cancelDeleteMovie(ActionEvent event) {
    }
    
}
