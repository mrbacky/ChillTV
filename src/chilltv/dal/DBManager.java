package chilltv.dal;

import chilltv.be.Category;
import java.util.List;

/**
 * This class manages all operations on the database.
 *
 * @author annem
 */
public class DBManager implements DBFacade {

    private final CategoryDAO catDAO;

    /**
     * Constructs data access objects.
     */
    public DBManager() {
        catDAO = new CategoryDAO();
    }

    @Override
    public void createCategory(String name) {
        catDAO.createCategory(name);
    }

    @Override
    public List<String> getAllCategories() {
        return catDAO.getAllCategories();
    }

    @Override
    public Category updateCategory(Category category, String editedName) {
        return catDAO.updateCategory(category, editedName);
    }

    @Override
    public void deleteCategory(String name) {
        catDAO.deleteCategory(name);
    }

}
