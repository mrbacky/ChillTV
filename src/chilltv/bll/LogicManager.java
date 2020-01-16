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
 * @author annem
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

//    @Override
//    public Category addMovieToCategory(Category category, Movie movie) {
//        return dbManager.addMovieToCategory(category, movie);
//    }
//    @Override
//    public void deleteMovieFromCategory(Category category, Movie movie) {
//        dbManager.deleteMovieFromCategory(category, movie);
//    }
    @Override
    public Movie createMovie(String title, int duration, float imdbRating, int myRating, String fileLink, String lastView, List<Category> cats) {
        return dbManager.createMovie(title, duration, imdbRating, myRating, fileLink, lastView, cats);
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

    @Override
    public String sec_To_Format(int sec) {
        return timeConverter.sec_To_Format(sec);
    }

    @Override
    public int format_To_Sec(String timeString) {
        return timeConverter.format_To_Sec(timeString);
    }

    @Override
    public List<Movie> getAllMoviesFiltered(Filter f) {
        return dbManager.getAllMoviesFiltered(f);
    }

   
    @Override
    public String convertCategory(List<Category> catList) {
        return categoryConverter.convertCategory(catList);
    }

    @Override
    public String convertCategory(Movie movie) {
            return categoryConverter.convertCategory(movie);
        }

    @Override
   public List<Movie> getMoviesOlderThan(int year){
       return dbManager.getMoviesOlderThan(year);
   }
}
