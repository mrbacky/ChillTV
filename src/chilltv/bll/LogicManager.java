package chilltv.bll;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import chilltv.bll.util.CategoryConverter;
import chilltv.bll.util.TimeConverter;
import chilltv.dal.DBFacade;
import chilltv.dal.DBManager;
import java.util.List;

/**
 * This class manages the connection between GUI and DAL.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
 */
public class LogicManager implements LogicFacade {

    private final DBFacade dbManager;
    private final TimeConverter timeConverter;
    private final CategoryConverter categoryConverter;

    /**
     * Creates a connection to the database. Constructs a TimeConverter.
     *
     */
    public LogicManager() {
        dbManager = new DBManager();
        timeConverter = new TimeConverter();
        categoryConverter = new CategoryConverter();
    }

    //__________________________________________________________________________                       
    //      
    //      Movie  
    //__________________________________________________________________________
    @Override
    public Movie createMovie(String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> cats) {
        return dbManager.createMovie(title, duration, imdbRating, myRating, fileLink, lastView, cats);
    }

    @Override
    public List<Movie> getAllMovies() {
        return dbManager.getAllMovies();
    }

    @Override
    public void updateMovie(Movie movie, List<Category> oldCatList) {
        dbManager.updateMovie(movie, oldCatList);
    }

    @Override
    public void deleteMovie(Movie movie) {
        dbManager.deleteMovie(movie);
    }

    @Override
    public List<Movie> getMoviesOlderThan(int year) {
        return dbManager.getMoviesOlderThan(year);
    }
    
    @Override
    public List<Movie> getAllMoviesFiltered(Filter f) {
        return dbManager.getAllMoviesFiltered(f);
    }
    
    //__________________________________________________________________________                       
    //      
    //      Category  
    //__________________________________________________________________________
    @Override
    public Category createCategory(String name) {
        return dbManager.createCategory(name);
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
        
    //__________________________________________________________________________                       
    //      
    //      CatMovie  
    //__________________________________________________________________________
    @Override
    public void addCategoryToMovie(Movie movie, List<Category> cats) {
        dbManager.addCategoryToMovie(movie, cats);
    }
    
    @Override
    public void deleteCategoryFromMovie(int movieId, List<Category> cats) {
        dbManager.deleteCategoryFromMovie(movieId, cats);
    }

    //__________________________________________________________________________                       
    //      
    //      Utilities  
    //__________________________________________________________________________
    @Override
    public String sec_To_Format(int sec) {
        return timeConverter.sec_To_Format(sec);
    }

    @Override
    public int format_To_Sec(String timeString) {
        return timeConverter.format_To_Sec(timeString);
    }   

    @Override
    public String convertCategory(List<Category> cats) {
        return categoryConverter.convertCategory(cats);
    }
}
