package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatMovieDAO {

    private DBConnectionProvider connectDAO;

    public CatMovieDAO() {
        connectDAO = new DBConnectionProvider();

    }

    /**
     * Adds a movie to a category in the database.
     *
     * @param category The category the movie is added to.
     * @param movie The movie to be added to the category.
     * @return Updated category with newly added movie.
     */
    public Category addMovieToCategory(Category category, Movie movie) {
        try ( //Get a connection to the database.
                Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "INSERT INTO CatMovie VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setInt(1, category.getId());
            pstmt.setInt(2, movie.getId());
            //Execute SQL query.
            pstmt.executeUpdate();
            //Add the song to the playlist.
            category.addMovie(movie);
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    /**
     * Deletes a movie from a selected category in the database.
     *
     * @param category The category the movie is deleted from.
     * @param movie The movie to be deleted.
     */
    public void deleteMovieFromCategory(Category category, Movie movie) {
        try ( //Get a connection to the database.
                Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "DELETE FROM CatMovie WHERE categoryId = ? and movieId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setInt(1, category.getId());
            pstmt.setInt(2, movie.getId());
            //Execute SQL query.
            pstmt.executeUpdate();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
