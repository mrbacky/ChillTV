package chilltv.gui.model;

import chilltv.be.Movie;
import chilltv.bll.LogicFacade;
import chilltv.bll.LogicManager;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

public class MovieModel {

    private LogicFacade logicManager;
    private final ObservableList<Movie> libraryList = FXCollections.observableArrayList();
    private static MovieModel movieModel;
    
    public static MovieModel getInstance(){
        if(movieModel==null)
            movieModel = new MovieModel();
        return movieModel;
    }
    
    private MovieModel() {
        logicManager = new LogicManager();
    }

    public void loadAllMovies() {
        libraryList.clear();
        List<Movie> allMovies = logicManager.getAllMovies();    
        libraryList.addAll(allMovies);
        
    }
    
    public ObservableList<Movie> getObsMovies() {
        return libraryList;
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

    
    
    

}
