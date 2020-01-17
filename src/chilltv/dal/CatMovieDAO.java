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
 * This DAO class can perform CRUD operations on the database CatMovie table.
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
     * Adds a category to a movie in the database. The relationship is added to
     * the CatMovie table with the categoryId and movieId as primary keys.
     *
     * @param movie The movie, the categories will be added to.
     * @param cats The list of categories to add.
     */
    public void addCategoryToMovie(Movie movie, List<Category> cats) {
        try (
                //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "INSERT INTO CatMovie(categoryId,movieId) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Set parameter values and add the PreparedStatement to a batch.
            for (Category category : cats) {
                pstmt.setInt(1, category.getId());
                pstmt.setInt(2, movie.getId());
                pstmt.addBatch();
            }
            //Execute SQL query as a batch.
            pstmt.executeBatch();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes a category from a movie in the database.The relationship is
     * deleted from the CatMovie table with the categoryId and movieId as
     * primary keys.
     *
     * @param movieId The id of the movie.
     * @param catToDelete The list of categories for the movie.
     */
    public void deleteCategoryFromMovie(int movieId, List<Category> catToDelete) {//!!!!!!!!!!!!!!!!!!!!!!!Change parameter?
        try (
            //Get a connection to the database.
            Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "DELETE FROM CatMovie WHERE categoryId = ? and movieId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values and add the a batch.
            for (Category category : catToDelete) {
                pstmt.setInt(1, category.getId());
                pstmt.setInt(2, movieId);
                pstmt.addBatch();
            }
            //Execute SQL query of the batch.
            pstmt.executeBatch();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
