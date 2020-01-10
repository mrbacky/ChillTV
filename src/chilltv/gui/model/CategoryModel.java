package chilltv.gui.model;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.bll.LogicFacade;
import chilltv.bll.LogicManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryModel {

    private static CategoryModel catModel;
    private LogicFacade logicManager;
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

    public void loadAllCategories() {
        categoryList.clear();
        List<Category> allCategories = logicManager.getAllCategories();
        categoryList.addAll(allCategories);
    }

    public ObservableList<Category> getObsCategories() {
        return categoryList;
    }

    public void createCategory(Category category) {
        logicManager.createCategory(category);
        categoryList.add(category);
    }
    
    public void updateCategory(Category category){
        logicManager.updateCategory(category);
        int index = categoryList.indexOf(category);
        categoryList.set(index, category);
    }
    
    public void deleteCategory(Category category){
        logicManager.deleteCategory(category);
        categoryList.remove(category);
    }
    
    /**
     * Searches for all categories which matches the given query.
     *
     * @param query The search query
     */
    public void filteredCategories(String query) {
        //Create a temporary list which contains the categories obtained from the search method.
        List<Category> temp = logicManager.searchCategories(logicManager.getAllCategories(), query);
        //Clear all categories from the library and add the categories from the temporary list to the library list.
        categoryList.clear();
        categoryList.addAll(temp);
    }
}
