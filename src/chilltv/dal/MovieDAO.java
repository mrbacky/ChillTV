package chilltv.dal;

import chilltv.be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class MovieDAO {

    DBConnectionProvider cp = new DBConnectionProvider();

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        String stat = "SELECT * FROM Movie";

        try ( Connection xd = cp.getConnection()) {
            Statement statement = xd.createStatement();
            ResultSet rs = statement.executeQuery(stat);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                int imdbRating = rs.getInt("imdbRating");
                int myRating = rs.getInt("myRating");
                String fileLink = rs.getString("fileLink");
                String lastView = rs.getString("lastView");
                
//                Movie movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("duration"), 
//                        rs.getInt("imdbRating"), rs.getInt("myRating"), rs.getString("fileLink"), rs.getInt("lastView"));
                allMovies.add(new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView));
            }
            return allMovies;
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
            return null;
        }
    }

    public Movie createMovie(String title, int duration, int imdbRating, int myRating, String fileLink, String lastView) {
        try ( Connection con = cp.getConnection()) {
            String sql = "INSERT INTO Movie(title, duration, imdbRating, myRating, fileLink, lastView) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, title);
            pstmt.setInt(2, duration);
            pstmt.setInt(3, imdbRating);
            pstmt.setInt(4, myRating);
            pstmt.setString(5, fileLink);
            pstmt.setString(6, lastView);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try ( ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteMovie(Movie movie) {
        String stat = "DELETE FROM movie WHERE id = ?";
        try ( Connection con = cp.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setInt(1, movie.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }

    public void updateMovie(Movie movie) {
        String stat = "UPDATE movie\n"
                + "SET title = ?, duration = ?, imdbRating = ?, myRating = ?, fileLink = ?, lastView = ?\n"
                + "WHERE id = ?";
        try ( Connection con = cp.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getDuration());
            stmt.setInt(3, movie.getImdbRating());
            stmt.setInt(4, movie.getMyRating());
            stmt.setString(5, movie.getFileLink());
            stmt.setString(6, movie.getLastView());
            stmt.setInt(7, movie.getId());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
