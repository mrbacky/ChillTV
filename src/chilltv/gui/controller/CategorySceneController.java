package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.gui.model.CategoryModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The CategorySceneController is the controller for the CategoryScene. It sends
 * requests to the Category Model, when creating, updating and deleting a
 * category.
 *
 * @author annem
 */
public class CategorySceneController implements Initializable {

    @FXML
    private Label lbl_categories;
    @FXML
    private Label lbl_newCat;
    @FXML
    private ChoiceBox<Category> choiceBox_category;
    @FXML
    private Button btn_createCategoryVisible;
    @FXML
    private Button btn_createCat;
    @FXML
    private Button btn_editCat;
    @FXML
    private Button btn_deleteCat;
    @FXML
    private Button btn_cancel;
    @FXML
    private TextField txt_createCategory;

    private LibraryController libraryController;
    private CategoryModel catModel;
    private boolean edit;
    private Category selectedCategory;

    /**
     * Initializes the controller class. Upon initialization, the mode is set to
     * create a category. The CategoryModel is initialized. All categories are
     * added to the ChoiceBox.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Gets the current instance of the Category model.
        catModel = CategoryModel.getInstance();

        //Gets all existing categories into to ChoiceBox.
        getAllCatsInChoiceBox();

        catModel.getInstance().getObsCategories().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //catModel.loadAllCategories();
                getAllCatsInChoiceBox();
                //setCheckedCategoriesForMovie();
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

    public void refreshLibrary() {
        libraryController.refreshLibrary();
    }
    
    /**
     * Gets all the existing categories into the ChoiceBox.
     */
    private void getAllCatsInChoiceBox() {
        choiceBox_category.getItems().clear();
        ObservableList<Category> allCats = catModel.getObsCategories();
        for (Category cat : allCats) {
            choiceBox_category.getItems().add(cat);
        }
    }

    /**
     * Makes the the controls visible, when creating/editing a category.
     */
    private void setVisible() {
        btn_createCat.setVisible(true);
        lbl_newCat.setVisible(true);
        txt_createCategory.setVisible(true);
    }

    /**
     * Makes the the controls visible, when creating a category.
     */
    @FXML
    private void handle_createCategoryVisible(ActionEvent event) {
        setVisible();
    }

    /**
     * Makes the the controls visible, when editing a category.
     */
    @FXML
    private void handle_editCatVisible(ActionEvent event) {
        setVisible();
        selectedCategory = choiceBox_category.getSelectionModel().getSelectedItem();
        editMode(selectedCategory);
    }

    /**
     * Hides the controls after new/edit.
     */
    private void hideControls() {
        btn_createCat.setVisible(false);
        lbl_newCat.setVisible(false);
        txt_createCategory.setVisible(false);
    }

    /**
     * Enables the edit mode, so the category can be edited. The existing name
     * of the selected category is displayed.
     *
     * @param selectedCategory
     */
    public void editMode(Category selectedCategory) {
        edit = true;
        txt_createCategory.setText(selectedCategory.getName());
    }

    /**
     * Checks the selected mode (new or edit) and saves the category.
     */
    @FXML
    public void handle_saveCategory(ActionEvent event) {
        if (!edit) {
            catModel.createCategory(txt_createCategory.getText().trim());
            txt_createCategory.setText("");

        } else {
            
            selectedCategory.setName(txt_createCategory.getText().trim());
            catModel.updateCategory(selectedCategory);
            refreshLibrary();
        }

        hideControls();
    }

    /**
     * Deletes the selected the category.
     */
    @FXML
    private void handle_deleteCategory(ActionEvent event) {
        Category catToDelete = choiceBox_category.getSelectionModel().getSelectedItem();
        catModel.deleteCategory(catToDelete);
        refreshLibrary();
    }

    /**
     * Closes the stage.
     */
    @FXML
    private void handle_cancelScene(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
}
