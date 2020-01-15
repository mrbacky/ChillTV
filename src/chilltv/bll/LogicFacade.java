package chilltv.bll;

import chilltv.be.Category;
import chilltv.be.Filter;
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
     * @param category The category to create.
     * @return The newly created category.
     */
    Category createCategory(Category category);

    /**
     * Gets a list with the names of all the categories and their respective
     * contents from the database.
     *
     * @return List with all categories.
     */
    List<Category> getAllCategories();

    /**
     * Updates the name of a category in the database.
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
     * @param category The category the movie is added to.
     * @param movie The movie to be added to the category.
     * @return Category with the added movie.
     */
    Category addMovieToCategory(Category category, Movie movie);

    /**
     * Deletes a movie from a category in the database.
     *
     * @param category The category of the movie.
     * @param movie The movie to delete from the category.
     */
    void deleteMovieFromCategory(Category category, Movie movie);

    Movie createMovie(String title, int duration, int imdbRating, int myRating, String fileLink, String lastView);

    List<Movie> getAllMovies();

    void updateMovie(Movie movie);

    void deleteMovie(Movie movie);

    String sec_To_Format(int sec);

    int format_To_Sec(String timeString);
        
    List<Movie> getAllMoviesFiltered(Filter f);
}
