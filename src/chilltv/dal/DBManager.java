package chilltv.dal;

import chilltv.be.Category;
import java.util.List;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.util.List;

public class DBManager implements DBFacade {


    private final CategoryDAO catDAO;
    private final MovieDAO movDAO;
    /**
     * Constructs data access objects.
     */
    public DBManager() {
        catDAO = new CategoryDAO();
        movDAO = new MovieDAO();
    }

    @Override
    public void createCategory(String name) {
        catDAO.createCategory(name);
    }

    @Override
    public List<String> getAllCategories() {
        return catDAO.getAllCategories();
    }

    @Override
    public Category updateCategory(Category category, String editedName) {
        return catDAO.updateCategory(category, editedName);
    }

    @Override
    public void deleteCategory(String name) {
        catDAO.deleteCategory(name);
    }

    @Override
    public void createMovie(Movie movie) {
        movDAO.createMovie(movie);
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

    
}
