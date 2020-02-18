package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.util.List;

/**
 *
 * @author annem
 */
public interface ICatMovieDAO {
    
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
    
}
