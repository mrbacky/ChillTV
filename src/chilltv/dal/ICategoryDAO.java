package chilltv.dal;

import chilltv.be.Category;
import java.util.List;

/**
 *
 * @author annem
 */
public interface ICategoryDAO {
    /**
     * Creates and adds a new category to the database.
     *
     * @param name The name of the new category.
     * @return The newly created category.
     */
    Category createCategory(String name);

    /**
     * Gets a list with all the categories.
     *
     * @return List with all categories.
     */
    List<Category> getAllCategories();

    /**
     * Updates the name of the category in the database.
     *
     * @param category The category to updated.
     * @return The updated category.
     */
    Category updateCategory(Category category);

    /**
     * Deletes a category in the database.
     *
     * @param category The category to delete.
     */
    void deleteCategory(Category category);

}
