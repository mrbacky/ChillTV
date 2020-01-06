package chilltv.dal;

import chilltv.be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DBManager {

    DBConnectionProvider cp = new DBConnectionProvider();

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        String stat = "SELECT * FROM Movie";

        try (Connection xd = cp.getConnection()) {
            Statement statement = xd.createStatement();
            ResultSet rs = statement.executeQuery(stat);
            while (rs.next()) {
                Movie movie = new Movie(rs.getInt("id"), rs.getString("name"), rs.getInt("rating"));
                allMovies.add(movie);
            }
            return allMovies;
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
            return null;
        }
    }

}
