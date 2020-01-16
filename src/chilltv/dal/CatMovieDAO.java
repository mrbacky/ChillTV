package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author annem
 */
public class CatMovieDAO {

    private final DBConnectionProvider connectDAO;

    /**
     * Constructor, which creates the connection with the database.
     */
    public CatMovieDAO() {
        connectDAO = new DBConnectionProvider();
    }

    /**
     * Adds a list of categories to a movie in the database.
     *
     * @param movie The movie, the categories will be added to.
     * @param cats The list of categories to add.
     */
    public void addCategoriesToMovie(Movie movie, List<Category> cats) {
        try ( //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "INSERT INTO CatMovie(categoryId,movieId) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Set parameter values.
            for(Category category: cats){
                pstmt.setInt(1, category.getId());
                pstmt.setInt(2, movie.getId());
                pstmt.addBatch();
            }
            //Execute SQL query.
            pstmt.executeBatch();            
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes a category from a movie in the database.
     *
     * @param category The category deleted from the movie.
     * @param movie The movie the category was added to.
     */
    public void deleteCategoryFromMovie(int movieId, List<Category> catToDelete){
        try ( //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "DELETE FROM CatMovie WHERE categoryId = ? and movieId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            for (Category category : catToDelete) {
                pstmt.setInt(1, category.getId());
                pstmt.setInt(2, movieId);
            }
            //Execute SQL query.
            pstmt.executeUpdate();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
