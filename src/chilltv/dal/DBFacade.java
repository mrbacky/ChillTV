package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.util.List;

/**
 * This interface is a facade for the data access layer. It contains methods
 * used for database operations.
 *
 * @author annem
 */
public interface DBFacade {

    /**
     * Creates and adds a new category to the database.
     *
     * @param name The name of the category.
     */
    void createCategory(String name);

    /**
     * Gets a list with the names of all the categories in the database.
     *
     * @return List with all categories.
     */
    List<String> getAllCategories();

    /**
     * Updates the name of the category in the database to reflect the values in
     * the given Movie objects.
     *
     * @param category The category to be updated.
     * @param editedName
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
