package chilltv.gui.model;

import chilltv.be.Category;
import chilltv.bll.LogicFacade;
import chilltv.bll.LogicManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel {

    private static CategoryModel catModel;
    private final LogicFacade logicManager;
    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();

    public static CategoryModel getInstance() {
        if (catModel == null) {

            catModel = new CategoryModel();
        }
        return catModel;
    }

    private CategoryModel() {
        logicManager = new LogicManager();
    }

        public void createCategory(String name) {
        Category category = logicManager.createCategory(name);
        categoryList.add(category);
        
    }

    public void loadAllCategories() {
        categoryList.clear();
        List<Category> allCategories = logicManager.getAllCategories();
        categoryList.addAll(allCategories);
    }

    public ObservableList<Category> getObsCategories() {
        return categoryList;
    }

    public void updateCategory(Category category) {
        logicManager.updateCategory(category);
        int index = categoryList.indexOf(category);
        categoryList.set(index, category);
    }

    public void deleteCategory(Category category) {
        logicManager.deleteCategory(category);
        categoryList.remove(category);
    }
}
