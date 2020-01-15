package chilltv.gui.controller;

import chilltv.be.Category;
import chilltv.gui.model.CategoryModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private Button btn_confirm;
    @FXML
    private Button btn_cancel;
    private LibraryController libCon;
    @FXML
    private Button btn_addCategoryVisible;
    @FXML
    private Button btn_deleteCat;
    private boolean edit;
    private CategoryModel catModel;
    private LibraryController libraryController;
    private Category categoryToEdit;
    @FXML
    private ChoiceBox<Category> choiceBox_category;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //getAllCatsForCheckBox();
        catModel = CategoryModel.getInstance();
        getAllCatsForCheckBox();
        catModel.getObsCategories();
    }

    private void getAllCatsForCheckBox() {
        
        
        ObservableList<Category> allCats = catModel.getObsCategories();
        System.out.println("obs cats in contr  "+ allCats);
        for (Category cat : allCats) {
            choiceBox_category.getItems().add(cat);
        }
    }

    @FXML
    public void handle_saveCategory(ActionEvent event) {

        if (!edit) {
            catModel.createCategory(txt_createCategory.getText().trim());
            String catName = txt_createCategory.getText().trim();
            //choiceBox_category.getItems().add(catName);

        } else {
            categoryToEdit.setName(txt_createCategory.getText().trim());
            catModel.updateCategory(categoryToEdit);
            
            

            //update the edited name
            //categoryToEdit.setName(txt_createCategory.getText().trim());
            //catModel.updateCategory(categoryToEdit);
        }

        //txt_Cat.setVisible(false); //makes the textfield invisible.
        //btn_saveCategory.setVisible(false); //makes the button invisible.
    }

    @FXML
    private void handle_confirmCat(ActionEvent event) {

    }

    @FXML
    private void handle_cancelScene(ActionEvent event) {
    }

    @FXML
    private void handle_deleteCategory(ActionEvent event) {
    }

    @FXML
    private void handle_addCategoryVisible(ActionEvent event) {
        btn_createCat.setVisible(true);
        lbl_newCat.setVisible(true);
        txt_createCategory.setVisible(true);
    }

    void setContr(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

}
