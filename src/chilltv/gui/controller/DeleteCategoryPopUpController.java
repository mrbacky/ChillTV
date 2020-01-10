
package chilltv.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeleteCategoryPopUpController implements Initializable {

    @FXML
    private Button btn_cancel;
    @FXML
    private Button btn_confirm;
    @FXML
    private Label lbl_catName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void handle_cancelDeleteCategory(ActionEvent event) {
    }

    @FXML
    private void handle_confirmDeleteCategory(ActionEvent event) {
    }
    
}
