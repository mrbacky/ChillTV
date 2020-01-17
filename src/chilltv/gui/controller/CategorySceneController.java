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

public class CategorySceneController implements Initializable {

    @FXML
    private Label lbl_Categories;
    @FXML
    private TextField txt_createCategory;
    @FXML
    private Label lbl_newCat;
    @FXML
    private Button btn_createCat;
    @FXML
    private Button btn_cancel;
    @FXML
    private Button btn_addCategoryVisible;
    @FXML
    private Button btn_deleteCat;
    private boolean edit;
    private CategoryModel catModel;
    private LibraryController libraryController;
    private Category selectedCategory;
    @FXML
    private ChoiceBox<Category> choiceBox_category;
    @FXML
    private Button btn_editCat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //  gets model
        catModel = CategoryModel.getInstance();

        //  gets categories into checkboxes
        getAllCatsForCheckBox();

        catModel.getInstance().getObsCategories().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //catModel.loadAllCategories();
                getAllCatsForCheckBox();
                //setCheckedCategoriesForMovie();
            }
        });

    }

    private void getAllCatsForCheckBox() {
        choiceBox_category.getItems().clear();
        ObservableList<Category> allCats = catModel.getObsCategories();
        for (Category cat : allCats) {
            choiceBox_category.getItems().add(cat);
        }
    }

    void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }
    
    public void refreshLibrary() {
        libraryController.refreshLibrary();
    }
    
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

    


    @FXML
    private void handle_cancelScene(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
        Category catToDelete = choiceBox_category.getSelectionModel().getSelectedItem();
        catModel.deleteCategory(catToDelete);

    }

    @FXML

    private void handle_addCategoryVisible(ActionEvent event) {
        setVisible();
    }

    private void setVisible() {

        btn_createCat.setVisible(true);
        lbl_newCat.setVisible(true);
        txt_createCategory.setVisible(true);

    }

    private void hideControls() {
        btn_createCat.setVisible(false);
        lbl_newCat.setVisible(false);
        txt_createCategory.setVisible(false);
    }

    

    @FXML
    private void handle_editCatVisible(ActionEvent event) {
        setVisible();
        selectedCategory = choiceBox_category.getSelectionModel().getSelectedItem();
        editMode(selectedCategory);
        
    }
    
    public void editMode(Category selectedCategory) {
        edit = true;
        txt_createCategory.setText(selectedCategory.getName());
    }

}
