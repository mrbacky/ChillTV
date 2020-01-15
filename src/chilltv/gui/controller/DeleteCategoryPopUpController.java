package chilltv.gui.controller;

import chilltv.be.Category;
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
 * Controller class for the DeleteCategoryPopUp. It sends requests to the
 * CategoryModel when deleting a category.
 *
 * @author annem
 */
public class DeleteCategoryPopUpController implements Initializable {

    @FXML
    private Label lbl_catName;
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_cancel;

    private CategoryModel catModel;
    private MovieModel movieModel;
    private LibraryController libCon;
    private Category selectedCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catModel = CategoryModel.getInstance();
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
     * Sets the title of the selected category on a label.
     *
     * @param category The category to delete.
     */
    public void setDeleteCategoryLabel(Category category) {
        selectedCategory = category;
        lbl_catName.setText(selectedCategory.getName());
    }

    @FXML
    private void handle_confirmDeleteCategory(ActionEvent event) {
        //Deletes the selected category.
        catModel.deleteCategory(selectedCategory);
        //movieModel.loadAllMovies();
        Stage stage;
        stage = (Stage) btn_confirm.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the stage.
     */
    @FXML
    private void handle_cancelDeleteCategory(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
}
