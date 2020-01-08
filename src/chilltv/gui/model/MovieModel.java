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

    public MovieModel() {
        logicManager = new LogicManager();

    }

    public ObservableList<Movie> getAllMovies() {
        List<Movie> allMovies = logicManager.getAllMovies();
        
        return libraryList;
    }

}
