package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import java.util.List;

/**
 * This class manages all operations on the database.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
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
    
    //__________________________________________________________________________                       
    //      
    //      Movie  
    //__________________________________________________________________________
    @Override
    public Movie createMovie(String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> cats) {
        return movDAO.createMovie(title, duration, imdbRating, myRating, fileLink, lastView, cats);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movDAO.getAllMovies();
    }

    @Override
    public void updateMovie(Movie movie, List<Category> oldCatList) {
        movDAO.updateMovie(movie, oldCatList);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movDAO.deleteMovie(movie);
    }
        
    @Override
    public List<Movie> getMoviesOlderThan(int year) {
        return movDAO.getMoviesOlderThan(year);
    }
    
    @Override
    public List<Movie> getAllMoviesFiltered(Filter f) {
        return movDAO.getAllMoviesFiltered(f);
    }
        
    //__________________________________________________________________________                       
    //      
    //      Category  
    //__________________________________________________________________________
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
    
    //__________________________________________________________________________                       
    //      
    //      CatMovie  
    //__________________________________________________________________________
    @Override
    public void addCategoryToMovie(Movie movie, List<Category> cats) {
        catMovDao.addCategoryToMovie(movie, cats);
    }

    @Override
    public void deleteCategoryFromMovie(int movieId, List<Category> cats) {
        catMovDao.deleteCategoryFromMovie(movieId, cats);
    }
}