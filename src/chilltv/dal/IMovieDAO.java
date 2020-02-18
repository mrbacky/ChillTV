package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import java.util.List;

/**
 *
 * @author annem
 */
public interface IMovieDAO {
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
    
}
