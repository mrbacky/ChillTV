package chilltv.bll;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.dal.DBFacade;
import chilltv.dal.DBManager;
import java.util.List;

/**
 * This class manages the connection between GUI and DAL.
 *
 * @author annem
 */
public class LogicManager implements LogicFacade {

    private final DBFacade dbManager;

    /**
     * Creates a connection to the database. Constructs a TimeConverter and
     * SearchFilter.
     */
    public LogicManager() {
        dbManager = new DBManager();
    }

    @Override
    public Category createCategory(Category category) {
        return dbManager.createCategory(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return dbManager.getAllCategories();
    }

    @Override
    public Category updateCategory(Category category) {
        return dbManager.updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        dbManager.deleteCategory(category);
    }

    @Override
    public Category addMovieToCategory(Category category, Movie movie) {
        return dbManager.addMovieToCategory(category, movie);
    }

    @Override
    public void deleteMovieFromCategory(Category category, Movie movie) {
        dbManager.deleteMovieFromCategory(category, movie);
    }

    @Override
    public void createMovie(Movie movie) {
        dbManager.createMovie(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return dbManager.getAllMovies();
    }

    @Override
    public void updateMovie(Movie movie) {
        dbManager.updateMovie(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        dbManager.deleteMovie(movie);
    }
}
