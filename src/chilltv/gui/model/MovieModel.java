package chilltv.gui.model;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import chilltv.bll.LogicFacade;
import chilltv.bll.LogicManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieModel {

    private LogicFacade logicManager;
    private final ObservableList<Movie> libraryList = FXCollections.observableArrayList();
    private static MovieModel movieModel;

    public static MovieModel getInstance() {
        if (movieModel == null) {
            movieModel = new MovieModel();
        }
        return movieModel;
    }

    private MovieModel() {
        logicManager = new LogicManager();
    }

    public void loadAllMovies() {
        List<Movie> allMovies = logicManager.getAllMovies();
        for (Movie movie : allMovies) {
            //replaces duration in seconds with hh:mm:ss format before adding the movie to an ObservableList.
            movie.setStringDuration(sec_To_Format(movie.getDuration()));
            //movie.setStringCat(convert(movie));
        }
        libraryList.clear();
        libraryList.addAll(allMovies);

    }

    public ObservableList<Movie> getObsMovies() {
        List<Movie> allMovies = logicManager.getAllMovies();
        for (Movie movie : allMovies) {
            //replaces duration in seconds with hh:mm:ss format before adding the movie to an ObservableList.
            movie.setStringDuration(sec_To_Format(movie.getDuration()));
            //movie.setStringCat(convert(movie));
        }
        libraryList.clear();
        libraryList.addAll(allMovies);
        return libraryList;
    }

    /**
     * Creates and adds a new movie. The method calls the BLL to create a movie
     * in the database. The created movie is added to the library list (the
     * library consists of all the songs).
     *
     * @param title The title of the movie.
     * @param duration The duration of the movie.
     * @param myRating The rating given by the user.
     * @param imdbRating The rating from imdb.
     * @param filelink The location of the movie.
     * @param lastView The date for when the user last viewed the movie.
     */
    public void createMovie(String title, int duration, int myRating, float imdbRating, String filelink, String lastView, List<Category> cats) {
        Movie movie = logicManager.createMovie(title, duration, imdbRating, myRating, filelink, lastView, cats);
//        movie.setStringDuration(sec_To_Format(movie.getDuration()));
//        movie.setStringCat(convert(movie));
        libraryList.add(movie);
    }

    /**
     * Updates a movie. The method calls the BLL to update an edited movie in
     * the database.
     *
     * @param movie The movie to update.
     */
    public void updateMovie(Movie movie) {
        logicManager.updateMovie(movie);
        int index = libraryList.indexOf(movie);
        libraryList.set(index, movie);
    }

    /**
     * Deletes a movie from the library list. The method calls the BLL to delete
     * a movie from the database. The deleted song is deleted from the library
     * list and all other playlists.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie) {
        logicManager.deleteMovie(movie);
        libraryList.remove(movie);

    }

    /**
     * Converts the time from the format hh:movieModel:ss to seconds.
     *
     * @param timeString The time in the format hh:movieModel:ss.
     * @return The time in seconds.
     */
    public int format_To_Sec(String timeString) {
        return logicManager.format_To_Sec(timeString);
    }

    /**
     * Converts the time from seconds to the format hh:movieModel:ss.
     *
     * @param sec The time in seconds.
     * @return The formatted time.
     */
    public String sec_To_Format(int sec) {
        return logicManager.sec_To_Format(sec);
    }

    public List<Movie> getAllMoviesFiltered(Filter f) {
        //Create a temporary list which contains the songs obtained from the search method.
        List<Movie> temp = logicManager.getAllMoviesFiltered(f);
        //Clear all songs from the library and add the songs from the temporary list to the library list.
        libraryList.clear();
        libraryList.addAll(temp);

        for (Movie movie : temp) {
            System.out.println(movie);
        }

        return libraryList;
    }

    public void test() {

    }
}
