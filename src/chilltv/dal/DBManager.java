package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Filter;
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
    private final CatMovieDAO catMovDao;

    /**
     * Constructs data access objects.
     */
    public DBManager() {
        catDAO = new CategoryDAO();
        movDAO = new MovieDAO();
        catMovDao = new CatMovieDAO();
    }

    @Override
    public Category createCategory(String name) {
        return catDAO.createCategory(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return catDAO.getAllCategories();
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
    public Movie createMovie(String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> cats) {
        return movDAO.createMovie(title, duration, myRating, myRating, fileLink, lastView, cats);
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

     @Override
    public void addMovieToCategory(Movie movie, List<Category> catToAdd) {
        catMovDao.addCategoriesToMovie(movie, catToAdd);
    }

    @Override
    public void deleteMovieFromCategory(int movieId, List<Category> catToDelete) {
        catMovDao.deleteCategoryFromMovie(movieId, catToDelete);
    }
    
    @Override
    public List<Movie> getMoviesOlderThan(int year) {
        return movDAO.getMoviesOlderThan(year);
    }
    public List<Movie> getAllMoviesFiltered(Filter f) {
        return movDAO.getAllMoviesFiltered(f);
    }

    

}