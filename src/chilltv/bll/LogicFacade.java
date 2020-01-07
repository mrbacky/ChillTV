package chilltv.bll;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.util.List;

/**
 * This interface is a facade for the business logic layer.
 *
 * @author annem
 */
public interface LogicFacade {

    /**
     * Creates and adds a new category to the database.
     *
     * @param name The name of the category.
     */
    void createCategory(String name);

    /**
     * Gets a list of the names of all the categories from the database,
     *
     * @return A list of all the categories.
     */
    List<String> getAllCategories();

    /**
     * Updates the name of a category in the database.
     *
     * @param category The category to be updated.
     * @param editedName The edited name of the category.
     * @return The updated category.
     */
    Category updateCategory(Category category, String editedName);

    /**
     * Deletes a category in the database.
     *
     * @param name The name of the category.
     */
    void deleteCategory(String name);

    
    
    
    void createMovie(Movie movie);
    
    List<Movie> getAllMovies();
    
    void updateMovie(Movie movie);
    
    void deleteMovie(Movie movie);
    
}
