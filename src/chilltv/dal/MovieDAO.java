/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

        try (Connection xd = cp.getConnection()) {
            Statement statement = xd.createStatement();
            ResultSet rs = statement.executeQuery(stat);
            while (rs.next()) {
                Movie movie = new Movie(rs.getInt("id"), rs.getString("name"), rs.getInt("duration"), rs.getInt("rating"));
                allMovies.add(movie);
            }
            return allMovies;
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
            return null;
        }
    }
    
    public void addMovie(Movie movie){
        String stat = "INSERT INTO movie VALUES (?,?,?)";
        
        try (Connection xd = cp.getConnection()) {
            PreparedStatement stmt = xd.prepareStatement(stat, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, movie.getName());
            stmt.setInt(2, movie.getDuration());
            stmt.setInt(3, movie.getRating());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    public void deleteMovie(Movie movie) {
        String stat = "DELETE FROM movie WHERE ID=?";
        try (Connection con = cp.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setInt(1, movie.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }
    
    public void updateMovie(Movie movie) {
        String stat = "UPDATE movie\n"
                + "SET name=?, duration=?, rating=?\n"
                + "WHERE ID=?";
        try (Connection con = cp.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setString(1, movie.getName());
            stmt.setInt(2, movie.getDuration());
            stmt.setInt(3, movie.getRating());
            stmt.setInt(4, movie.getId());
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
