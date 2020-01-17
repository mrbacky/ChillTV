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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This DAO class can perform CRUD operations on mainly the database Movie
 * table.
 *
 * @author annem
 */
public class MovieDAO {

    private final DBConnectionProvider cp;
    private final CatMovieDAO catMovDAO;
    private final CategoryDAO catDAO;

    /**
     * Constructor, which creates the connection with the database and the DAO
     * for Category and CatMovie.
     */
    public MovieDAO() {
        cp = new DBConnectionProvider();
        catDAO = new CategoryDAO();
        catMovDAO = new CatMovieDAO();
    }

    /**
     * Creates and adds a new movie in the database.
     *
     * @param title The title of the movie.
     * @param duration The duration of the movie.
     * @param imdbRating The imdbRating of the movie.
     * @param myRating The personal rating of the movie.
     * @param fileLink The file location of the movie.
     * @param lastView The last viewed year of the movie.
     * @param catList The category list of the movie.
     * @return The newly created movie.
     */
    public Movie createMovie(String title, int duration, int imdbRating, int myRating, String fileLink, int lastView, List<Category> catList) {
        try ( Connection con = cp.getConnection()) {
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

            catMovDAO.addCategoryToMovie(movie, catList);

            return movie;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets all the movies from the database.
     *
     * @return A list with all movies.
     */
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
                float imdbRating = rs.getFloat("imdbRating");
                int myRating = rs.getInt("myRating");
                String fileLink = rs.getString("fileLink");
                int lastView = rs.getInt("lastView");
                List<Category> categoryList = catDAO.getAllCatsForMovie(id);//


                allMovies.add(new Movie(id, title, duration, imdbRating, myRating, fileLink, lastView, categoryList));
            }

            return allMovies;

        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
            return null;
        }
    }

    /**
     * Updates a movie in the database after editing.
     *
     * @param movie The movie to be updated.
     * @param oldCatList The previous list of categories for the movie.
     */
    public void updateMovie(Movie movie, List<Category> oldCatList) {
        String stat = "UPDATE movie\n"
                + "SET title = ?, duration = ?, imdbRating = ?, myRating = ?, fileLink = ?, lastView = ?\n"
                + "WHERE id = ?";

        try ( Connection con = cp.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(stat);
            pstmt.setString(1, movie.getTitle());
            pstmt.setInt(2, movie.getDuration());
            pstmt.setFloat(3, movie.getImdbRating());
            pstmt.setInt(4, movie.getMyRating());
            pstmt.setString(5, movie.getFileLink());
            pstmt.setInt(6, movie.getLastView());
            pstmt.setInt(7, movie.getId());
            pstmt.execute();

            List<Category> deleteCatMovList = new ArrayList<>();
            List<Category> addCatMovList = new ArrayList<>();

            //Compare each category from the old list with the new list.
            //If the old cats are not in the new list, the relationship will be deleted from CatMovie table.
            for (Category o : oldCatList) {
                if (!inBothList(o, movie.getCategoryList())) {
                    deleteCatMovList.add(o);
                }
            }

            //Compare each category from the new list withe the old list.
            //If the new cats are not in the old list, the relationship will be inserted into CatMovie table.
            for (Category n : movie.getCategoryList()) {
                if (!inBothList(n, oldCatList)) {
                    addCatMovList.add(n);
                }
            }

            //Call method only if there are new categories.
            if (addCatMovList.size() > 0) {
                catMovDAO.addCategoryToMovie(movie, addCatMovList);
            }

            //Call method only if categories are removed.
            if (deleteCatMovList.size() > 0) {
                catMovDAO.deleteCategoryFromMovie(movie.getId(), deleteCatMovList);
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks if a category from one list is present in another list.
     *
     * @param category A category from one list.
     * @param categoryList Another category list.
     * @return
     */
    private boolean inBothList(Category category, List<Category> categoryList) {
        for (Category n : categoryList) {
            if (n.getId() == category.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a movie from the database.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie) {
        //When the movie is deleted, it should also be removed from all categories. DOES IT?
        try ( Connection con = cp.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, movie.getId());
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }

    /**
     * Gets all movies from the database, which are last viewed more than x
     * years ago.
     *
     * @param year x number of years.
     * @return A list with movies older than x years.
     */
    public List<Movie> getMoviesOlderThan(int year) {
        List<Movie> allMovies = new ArrayList<>();

        String sql = "SELECT * FROM Movie WHERE lastView<=?";

        try ( Connection con = cp.getConnection()) {

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
                List<Category> category = catDAO.getAllCatsForMovie(id);
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
    
    //__________________________________________________________________________                       
    //
    //      Filter  
    //__________________________________________________________________________
    /**
     * Gets all movies after filtering.
     *
     * @param f Filter which can filter by title, category and imdbRating.
     * @return A list with all movies after filter has been used.
     */
    public List<Movie> getAllMoviesFiltered(Filter f) {
        List<Movie> filteredMovies = new ArrayList<>();

        String sql = "SELECT Movie.* FROM Movie JOIN CatMovie ON Movie.id = CatMovie.movieId WHERE "; //Only adds distinct movies.
        String sqlFinal = prepStatment(sql, f);

        try ( Connection con = cp.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sqlFinal);

            int i = 0;
            for (Category cat : f.getCats()) {
                pstmt.setInt(i + 1, cat.getId());
                i++;
            }

            pstmt.setString(i + 1, "%" + f.getQuery() + "%");

            if (f.getImdbRating() > 0) {
                pstmt.setFloat(i + 2, f.getImdbRating());
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                float imdbRating = rs.getFloat("imdbRating");
                int myRating = rs.getInt("myRating");
                String fileLink = rs.getString("fileLink");
                List<Category> categoryList = catDAO.getAllCatsForMovie(id);//
                filteredMovies.add(new Movie(id, title, duration, imdbRating, myRating, fileLink, myRating, categoryList));
                //This list contains duplicates when searching for x categories, will add x rows to the ResultSet.
            }

            Map duplicates = elementsInArray(filteredMovies);

            return generateCorrectList(duplicates, filteredMovies, f.getCats().size());

        } catch (SQLException ex) {
            System.out.println("chilltv.dal.MovieDAO.libraryFilter()" + ex);
        }
        return null;
    }

    /**
     * The conditions of the SQL PreparedStatement for getAllMoviesFiltered().
     *
     * @param sql The SQL PreparedStatement.
     * @return f The filter.
     */
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

        if (f.getImdbRating() > 0) {
            sql += "AND Movie.imdbRating >= ?";
        }

        return sql;
    }

    /**
     * Gets a list used to obtain and differentiate duplicates if any present.
     *
     * @param arrayToFind
     * @return dupElements
     */
    private Map elementsInArray(List<Movie> arrayTofind) {
        Map<Integer, Integer> dupElements = new HashMap<>(); //movieId + count

        for (int i = 0; i < arrayTofind.size(); i++) { //Loop through list
            if (!dupElements.containsKey(arrayTofind.get(i).getId())) { //Set key for HashMap to movieId
                dupElements.put(arrayTofind.get(i).getId(), 0); //Set value for HashMap for all to 0
            }
        }

        for (int i = 0; i < arrayTofind.size(); i++) {
            Integer newInt = dupElements.get(arrayTofind.get(i).getId());
            newInt = newInt + 1;
            dupElements.replace(arrayTofind.get(i).getId(), newInt); //Change the value for duplicates. +1 for each duplicate.
        }
        return dupElements; //List with duplicates have changed values in the HashMap. List without duplicates keep the value 0.
    }

    /**
     * Generates a correct list of results from using filter.
     *
     * @param duplicates The manipulated HashMap with duplicates.
     * @param movListWithDup The list of movies with duplicates before manipulation (correction).
     * @param catFilterSize The size of the category filter (number of categories selected).
     * @return A corrected, filtered list of movies.
     */
    private List<Movie> generateCorrectList(Map duplicates, List<Movie> movListWithDup, int catFilterSize) {
        List<Movie> movList = new ArrayList<>();

        Iterator entries = duplicates.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer key = (Integer) entry.getKey(); //Get key from HashMap.
            Integer value = (Integer) entry.getValue(); //Get value from HashMap.
            for (Movie movie : movListWithDup) {
                if (movie.getId() == key && value > catFilterSize - 1) { //Filters out the duplicates by comparing the value with x categories.
                    movList.add(movie);
                    break;
                }
            }
        }
        return movList;
    }
}
