
package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class LibraryController implements Initializable {

    @FXML
    private TableView<?> tbv_Library;
    @FXML
    private Button btn_addMovie;

    private PrimaryController pCon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setContr(PrimaryController pCon) {
        this.pCon = pCon;
    }
    
}
