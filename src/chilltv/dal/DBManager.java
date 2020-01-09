package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages all operations on the database.
 *
 * @author annem
 */
public class DBManager implements DBFacade {

    private final CategoryDAO catDAO;
    private final MovieDAO movDAO;

    /**
     * Constructs data access objects.
     */
    public DBManager() {
        catDAO = new CategoryDAO();
        movDAO = new MovieDAO();
    }

    @Override
    public Category createCategory(Category category) {
        return catDAO.createCategory(category);
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return catDAO.getAllMoviesInCategories();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return catDAO.updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        catDAO.deleteCategory(category);
    }

    @Override
    public Category addMovieToCategory(Category category, Movie movie) {
        return catDAO.addMovieToCategory(category, movie);
    }

    @Override
    public void deleteMovieFromCategory(Category category, Movie movie) {
        catDAO.deleteMovieFromCategory(category, movie);
    }

    @Override
    public Movie createMovie(String title, int duration, int imdbRating, int myRating, String fileLink, String lastView) {
        return movDAO.createMovie(title, duration, imdbRating, myRating, fileLink, lastView);
    }
    
    @Override
    public List<Movie> getAllMovies() {
        return movDAO.getAllMovies();
    }

    @Override
    public void updateMovie(Movie movie) {
        movDAO.updateMovie(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movDAO.deleteMovie(movie);
    }
}
