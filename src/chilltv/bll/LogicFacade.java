package chilltv.bll;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import java.util.List;

/**
 * This interface is a facade for the business logic layer.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
 */
public interface LogicFacade {

    //__________________________________________________________________________                       
    //      
    //      Movie  
    //__________________________________________________________________________
    /**
     * Creates and adds a new movie in the database.
     *
     * @param title The title of the movie.
     * @param duration The duration of the movie.
     * @param imdbRating The imdbRating of the movie.
     * @param myRating The personal rating of the movie.
     * @param fileLink The file location of the movie.
     * @param lastView The last viewed year of the movie.
     * @param cats The category list of the movie.
     * @return The newly created movie.
     */
    Movie createMovie(String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> cats);

    /**
     * Gets all the movies from the database.
     *
     * @return A list with all movies.
     */
    List<Movie> getAllMovies();

    /**
     * Updates a movie in the database after editing.
     *
     * @param movie The movie to be updated.
     * @param oldCatList The previous list of categories for the movie.
     */
    void updateMovie(Movie movie, List<Category> oldCatList);

    /**
     * Deletes a movie from the database.
     *
     * @param movie The movie to delete.
     */
    void deleteMovie(Movie movie);

    /**
     * Gets all movies from the database, which are last viewed more than x
     * years ago.
     *
     * @param year x number of years.
     * @return A list with movies older than x years.
     */
    List<Movie> getMoviesOlderThan(int year);

    /**
     * Gets all movies after filtering.
     *
     * @param f Filter which can filter by title, category and imdbRating.
     * @return A list with all movies after filter has been used.
     */
    List<Movie> getAllMoviesFiltered(Filter f);

    //__________________________________________________________________________                       
    //      
    //      Category  
    //__________________________________________________________________________
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

    //__________________________________________________________________________                       
    //      
    //      CatMovie  
    //__________________________________________________________________________
    /**
     * Adds a category list of a movie in the database.
     *
     * @param movie The movie the categories will be added to.
     * @param cats The list of categories added to the movie.
     */
    void addCategoryToMovie(Movie movie, List<Category> cats);

    /**
     * Deletes a category list of a movie from the database.
     *
     * @param movieId
     * @param cats The list of categories deleted from the movie.
     */
    void deleteCategoryFromMovie(int movieId, List<Category> cats);

    //__________________________________________________________________________                       
    //      
    //      Utilities  
    //__________________________________________________________________________
    /**
     * Converts the time from seconds to the format hh:mm:ss.
     *
     * @param sec The time in seconds.
     * @return The formatted time.
     */
    String sec_To_Format(int sec);

    /**
     * Converts the time from the format hh:mm:ss to seconds.
     *
     * @param timeString The time in the format hh:mm:ss.
     * @return The time in seconds.
     */
    int format_To_Sec(String timeString);

    /**
     * Converts the category list of a movie to a string for the view.
     *
     * @param cats The list of categories.
     * @return The category list as a string.
     */
    String convertCategory(List<Category> cats);
}
