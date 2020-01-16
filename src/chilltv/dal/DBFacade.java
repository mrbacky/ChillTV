
package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Filter;
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
     * @param category The category to create.
     * @return The newly created category.
     */
    Category createCategory(String name);
    
    
    

    /**
     * Gets a list with the names of all the categories and their respective
     * contents (movies) in the database.
     *
     * @return List with all categories.
     */
    List<Category> getAllCategories();

    /**
     * Updates the name of the category in the database to reflect the values in
     * the given Movie objects.
     *
     * @param category The category to be updated.
     * @return The updated category.
     */
    Category updateCategory(Category category);

    /**
     * Deletes a category in the database.
     *
     * @param category The category to delete.
     */
    void deleteCategory(Category category);

    /**
     * Adds a movie to a category in the database.
     *
     * @param category The category the movie should be added to.
     * @param movie The movie to be added to the category.
     * @return Category with the added movie.
     */
    void addMovieToCategory(Movie movie, List<Category> catToAdd);

    /**
     * Deletes a movie from a category in the database.
     *
     * @param category The category of the movie.
     * @param movie The movie to delete from the category.
     */
    void deleteMovieFromCategory(int movieId, List<Category> catToDelete);

    Movie createMovie(String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> cats);

    List<Movie> getAllMovies();

    void updateMovie(Movie movie);

    void deleteMovie(Movie movie);
    
    List<Movie> getMoviesOlderThan(int year);

    List<Movie> getAllMoviesFiltered(Filter f);
}


