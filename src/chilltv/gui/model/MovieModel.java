
package chilltv.gui.model;

import chilltv.be.Movie;
import chilltv.bll.LogicFacade;
import chilltv.bll.LogicManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovieModel {
    
    private LogicFacade logicManager;
    private ObservableList<Movie> libraryList;

    public MovieModel() {
        logicManager = new LogicManager();
        getAllMovies();
    }
    
    public ObservableList<Movie> getAllMovies(){
        List<Movie> allMovies = logicManager.getAllMovies();
        libraryList = FXCollections.observableArrayList(allMovies);
        return libraryList;
    }
    
    
}
