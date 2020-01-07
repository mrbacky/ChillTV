package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.util.List;

/**
 * This class manages all operations on the database.
 *
 * @author annem
 */
public class DBManager implements DBFacade {

    private final CategoryDAO catDAO;

    /**
     * Constructs data access objects.
     */
    public DBManager() {
        catDAO = new CategoryDAO();
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
    public void createMovie(String title, int duration, int rating) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllMovies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Movie updateMovie(Movie movie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteMovie(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
