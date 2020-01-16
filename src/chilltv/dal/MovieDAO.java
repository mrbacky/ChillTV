package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Filter;
import chilltv.be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MovieDAO {

    private final DBConnectionProvider cp;
    private final CatMovieDAO catMovDAO;
    private final CategoryDAO catDAO;

    public MovieDAO() {
        cp = new DBConnectionProvider();
        catDAO = new CategoryDAO();
        catMovDAO = new CatMovieDAO();
    }

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        String stat = "SELECT * FROM Movie";

        try (Connection xd = cp.getConnection()) {
            Statement statement = xd.createStatement();
            ResultSet rs = statement.executeQuery(stat);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                float imdbRating = rs.getFloat("imdbRating");
                int myRating = rs.getInt("myRating");
                String fileLink = rs.getString("fileLink");
                int lastView = rs.getInt("lastView");
//                String stringCat = catDAO.getAllCategoriesOfMovie(id);
                List<Category> categoryList = catDAO.getAllCategoriesForCatList(id);//

//                Movie movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("duration"), 
//                        rs.getInt("imdbRating"), rs.getInt("myRating"), rs.getString("fileLink"), rs.getInt("lastView"));
                allMovies.add(new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView, categoryList));
            }
            return allMovies;
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
            return null;
        }

    }

    public List<Movie> getMoviesOlderThan(int year){
        List<Movie> allMovies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE lastView<=?";
        
        try (Connection con = cp.getConnection()) {
            
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                int imdbRating = rs.getInt("imdbRating");
                int myRating = rs.getInt("myRating");
                String fileLink = rs.getString("fileLink");
                int lastView = rs.getInt("lastView");
                List<Category> category = catDAO.getAllCategoriesForCatList(id);
//                Movie movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("duration"), 
//                        rs.getInt("imdbRating"), rs.getInt("myRating"), rs.getString("fileLink"), rs.getInt("lastView"));
                allMovies.add(new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView, category));

            }
            return allMovies;
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
            return null;
        }
    }
    
    public Movie createMovie(String title, int duration, int imdbRating, int myRating, String fileLink, int lastView,List<Category> catList) {
        try (Connection con = cp.getConnection()) {
            String sql = "INSERT INTO Movie(title, duration, imdbRating, myRating, fileLink, lastView) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); //Prepared or Stat?
            pstmt.setString(1, title);
            pstmt.setInt(2, duration);
            pstmt.setFloat(3, imdbRating);
            pstmt.setInt(4, myRating);
            pstmt.setString(5, fileLink);
            pstmt.setInt(6, lastView);
            pstmt.executeUpdate();
                
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            Movie movie = new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView, catList);
            catMovDAO.addCategoriesToMovie(movie, catList);
            return movie;
            /*int affectedRows = pstmt.executeUpdate();
            List<Category> category = null;
            String stringCat = "asd";

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView, category,stringCat);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }*/
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteMovie(Movie movie) {
        //When the movie is deleted, it should also be removed from all categories. DOES IT?
        try (Connection con = cp.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, movie.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }

    public void updateMovie(Movie movie) {
        String stat = "UPDATE movie\n"
                + "SET title = ?, duration = ?, imdbRating = ?, myRating = ?, fileLink = ?, lastView = ?\n"
                + "WHERE id = ?";
        try (Connection con = cp.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getDuration());
            stmt.setFloat(3, movie.getImdbRating());
            stmt.setInt(4, movie.getMyRating());
            stmt.setString(5, movie.getFileLink());
            stmt.setInt(6, movie.getLastView());
            stmt.setInt(7, movie.getId());
            
            
            stmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*SELECT M.*,
      C.*
      FROM [PrivateMovieCollection_AL].[dbo].[CatMovie] CM,
           [PrivateMovieCollection_AL].[dbo].[Movie] M,
           [PrivateMovieCollection_AL].[dbo].[Category] C
      WHERE (categoryId = 2 OR categoryId = 5)
      AND  CM.movieId = M.id
      AND M.title LIKE '%A%'
      AND C.id = CM.categoryId
      
      SELECT Movie.*, Category.*  FROM Movie  JOIN CatMovie on Movie.id = CatMovie.movieId JOIN Category ON categoryId=Category.id 
      WHERE Movie.title like '%%' 
      AND  CatMovie.categoryId = 2 OR CatMovie.categoryId = 3 OR CatMovie.categoryId = 1
      ORDER BY Movie.title ASC
     */
    //public List<Movie> getAllMoviesFiltered(String query, List<Category> cats){
    public List<Movie> getAllMoviesFiltered(Filter f) {
        List<Movie> filteredMovies = new ArrayList<>();
        String sql = "SELECT Movie.* FROM Movie JOIN CatMovie ON Movie.id = CatMovie.movieId WHERE "; //Only adds distinct movies.

        String sqlFinal = prepStatment(sql, f);
        try (Connection con = cp.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sqlFinal);

            int i = 0;
            for (Category cat : f.getCats()) {
                pstmt.setInt(i + 1, cat.getId());
                i++;
            }

            pstmt.setString(i + 1, "%" + f.getQuery() + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                float imdbRating = rs.getFloat("imdbRating");
                int myRating = rs.getInt("myRating");
                String fileLink = rs.getString("fileLink");
                String lastView = rs.getString("lastView");
                String stringCat = catDAO.getAllCategoriesOfMovie(id);
                List<Category> categoryList = null;
                filteredMovies.add(new Movie(id, title, duration, imdbRating, myRating, fileLink, myRating, categoryList));
                //This list has duplicates. Searching for x categories, will add x rows to the ResultSet.
            }

            Map dublicates = elementsInArray(filteredMovies);
            return generateCorrectList(dublicates, filteredMovies, f.getCats().size());
        } catch (SQLException ex) {
            System.out.println("chilltv.dal.MovieDAO.libraryFilter()" + ex);
        }

        return null;
    }

    private String prepStatment(String sql, Filter f) {
        boolean firstItem = true;
        for (Category c : f.getCats()) { //last one omits OR.
            if (firstItem) {
                sql += "categoryId=? ";
                firstItem = false;
            } else {
                sql += "OR categoryId=? ";
            }
        }

        if (f.getCats().size() > 0) { //use AND only if the category filter is used.
            sql += "AND ";
        }

        sql += "Movie.title LIKE ? "; //? = %query%
        System.out.println(sql);
        return sql;
    }

    private Map elementsInArray(List<Movie> arrayTofind) {

        Map<Integer, Integer> dupElements = new HashMap<Integer, Integer>(); //movieId + count

        for (int i = 0; i < arrayTofind.size(); i++) {
            if (!dupElements.containsKey(arrayTofind.get(i).getId())) { //Set key for HashMap
                dupElements.put(arrayTofind.get(i).getId(), 0); //Set value for HashMap for all to 0
            }
        }
        for (int i = 0; i < arrayTofind.size(); i++) {
            Integer newInt = dupElements.get(arrayTofind.get(i).getId());
            newInt = newInt + 1;
            dupElements.replace(arrayTofind.get(i).getId(), newInt); //Change the value for duplicates. +1 for each duplicate.
        }
        return dupElements;
    }

    private List<Movie> generateCorrectList(Map duplicates, List<Movie> movListWithDup, int filterSize) {
        List<Movie> movList = new ArrayList<Movie>();

        Iterator entries = duplicates.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer key = (Integer) entry.getKey();
            Integer value = (Integer) entry.getValue();
            for (Movie movie : movListWithDup) {
                if (movie.getId() == key && value > filterSize - 1) {
                    movList.add(movie);
                    break;
                }
            }
        }
        return movList;
    }
}
