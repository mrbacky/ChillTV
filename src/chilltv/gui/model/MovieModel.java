package chilltv.gui.model;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import chilltv.bll.LogicFacade;
import chilltv.bll.LogicManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The MovieModel gets and passes data about the movies to the BLL.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
 */
public class MovieModel {   

    private final LogicFacade logicManager;
    
    private final ObservableList<Movie> libraryList = FXCollections.observableArrayList();
    private static MovieModel movieModel;

    /**
     * Creates an instance of the model of the MovieModel. Ensures that the same
     * instance of the model is used in the entire program.
     *
     * @return The movieModel
     */
    public static MovieModel getInstance() {
        if (movieModel == null) {
            movieModel = new MovieModel();
        }
        return movieModel;
    }

    /**
     * Establish a connection to the BLL.
     */
    private MovieModel() {
        logicManager = new LogicManager();
        
        
    }
        

    public MovieModel(LogicFacade logicManager) {
        this.logicManager = logicManager;
    }

    //__________________________________________________________________________                       
    //      
    //      Movie  
    //__________________________________________________________________________ 
    /**
     * Gets all movies from the database.
     */
    public void loadAllMovies() {
        List<Movie> allMovies = logicManager.getAllMovies();
        for (Movie movie : allMovies) {
            //replaces duration in seconds with hh:mm:ss format before adding the movie to an ObservableList.
            movie.setStringDuration(sec_To_Format(movie.getDuration()));
            //replaces category in String before adding the movie to an ObservableList. 
            movie.setStringCat(convertCategory(movie.getCategoryList()));
        }
        libraryList.clear();
        libraryList.addAll(allMovies);
    }

    /**
     * Returns a list with all the movies (library) as an ObservableList.
     *
     * @return The list of all movies in the library.
     */
    public ObservableList<Movie> getObsMovies() {
        return libraryList;
    }

    /**
     * Creates and adds a new movie.The method calls the BLL to create a movie
     * in the database.The created movie is added to the library list (the
     * library consists of all the songs).
     *
     * @param title The title of the movie.
     * @param duration The duration of the movie.
     * @param imdbRating The imdbRating of the movie.
     * @param myRating The personal rating of the movie.
     * @param fileLink The file location of the movie.
     * @param lastView The last viewed year of the movie.
     * @param cats The category list of the movie.
     */
    public void createMovie(String title, int duration, int myRating, float imdbRating, String fileLink, int lastView, List<Category> cats) {
        Movie movie = logicManager.createMovie(title, duration, imdbRating, myRating, fileLink, lastView, cats);
        //replaces duration in seconds with hh:mm:ss format before adding the movie to an ObservableList.
        movie.setStringDuration(sec_To_Format(movie.getDuration()));
        //replaces category in String before adding the movie to an ObservableList. 
        movie.setStringCat(convertCategory(movie.getCategoryList()));
        libraryList.add(movie);
    }

    /**
     * Updates a movie. The method calls the BLL to update an edited movie in
     * the database.
     *
     * @param movie The movie to update.
     * @param oldCatList
     */
    public void updateMovie(Movie movie, List<Category> oldCatList) {
        //replaces duration in seconds with hh:mm:ss format before adding the movie to an ObservableList.
        movie.setStringDuration(sec_To_Format(movie.getDuration()));
        //replaces category in String before adding the movie to an ObservableList. 
        movie.setStringCat(convertCategory(movie.getCategoryList()));
        logicManager.updateMovie(movie, oldCatList);

        int index = libraryList.indexOf(movie);
        libraryList.set(index, movie);
    }

    /**
     * Deletes a movie from the library list. The method calls the BLL to delete
     * a movie from the database. The deleted movie is deleted from the library
     * list and is no longer connected with any category.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie) {
        logicManager.deleteMovie(movie);
        libraryList.remove(movie);
    }

    /**
     * Gets all movies from the database, which are last viewed more than x
     * years ago.
     *
     * @param year x number of years.
     * @return A list with movies older than x years.
     */
    public List<Movie> getMoviesOlderThan(int year) {
        return logicManager.getMoviesOlderThan(year);
    }

    /**
     * Gets all movies after filtering.
     *
     * @param f Filter which can filter by title, category and imdbRating.
     * @return A list with all movies after filter has been used.
     */
    public List<Movie> getAllMoviesFiltered(Filter f) {
        //Create a temporary list which contains the movies obtained from the filter method.
        List<Movie> temp = logicManager.getAllMoviesFiltered(f);
        //Clear all movies from the library and add the movies from the temporary list to the library list.
        for (Movie movie : temp) {
            //replaces duration in seconds with hh:mm:ss format before adding the movie to an ObservableList.
            movie.setStringDuration(sec_To_Format(movie.getDuration()));
            //replaces category in String before adding the movie to an ObservableList.            
            movie.setStringCat(convertCategory(movie.getCategoryList()));
        }

        libraryList.clear();
        libraryList.addAll(temp);

        return libraryList;
    }

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
    public void addCategoryToMovie(Movie movie, List<Category> cats) {
        logicManager.addCategoryToMovie(movie, cats);
    }

    /**
     * Deletes a category list of a movie from the database.
     *
     * @param movieId
     * @param cats The list of categories deleted from the movie.
     */
    public void deleteCategoryFromMovie(int movieId, List<Category> cats) {
        logicManager.deleteCategoryFromMovie(movieId, cats);
    }

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
    public String sec_To_Format(int sec) {
        return logicManager.sec_To_Format(sec);
    }

    /**
     * Converts the time from the format hh:mm:ss to seconds.
     *
     * @param timeString The time in the format hh:mm:ss.
     * @return The time in seconds.
     */
    public int format_To_Sec(String timeString) {
        return logicManager.format_To_Sec(timeString);
    }

    /**
     * Converts the category list of a movie to a string for the view.
     *
     * @param cats The list of categories.
     * @return The category list as a string.
     */
    public String convertCategory(List<Category> cats) {
        return logicManager.convertCategory(cats);
    }
}
