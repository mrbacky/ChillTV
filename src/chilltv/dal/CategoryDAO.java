package chilltv.dal;

import chilltv.be.Category;
import chilltv.be.Movie;
import chilltv.dal.util.CategoryConverterV1;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The CategoryDAO class can perform CRUD operations on the Category database
 * table.
 *
 * @author Anne Luong
 */
public class CategoryDAO {

    private final DBConnectionProvider connectDAO;
    CategoryConverterV1 cc = new CategoryConverterV1();

    /**
     * Constructor, which creates the connection with the database.
     */
    public CategoryDAO() {
        connectDAO = new DBConnectionProvider();
    }

    /**
     * Creates and adds a new category to the database.
     *
     * @param category The category to create.
     * @return The newly created category.
     */
    public Category createCategory(String name) {
        try ( //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "INSERT INTO Category VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Set parameter value.
            pstmt.setString(1, name);
            //Execute SQL query.
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return new Category(id, name);

        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets all the categories from the database table Category. Gets all the
     * values from the Category table in the database using a SQL statement and
     * orders it by id ASC. Adds the values to a HashMap.
     *
     * @return allCategories
     */
    public List<Category> getAllCategories() {
        //Create a HashMap to store all categories.
        //HashMap<Integer, Category> allCategories = new HashMap<Integer, Category>();
        List<Category> allCats = new ArrayList<>();
        String stat = "SELECT * FROM Category";

        try ( //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            PreparedStatement pstmt = con.prepareStatement(stat);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //Add the categories to the HashMap.
                int id = rs.getInt("id");
                String name = rs.getString("name");
                //allCategories.put(id, new Category(id, name, movies));
                allCats.add(new Category(id, name));
                
            }
            return allCats;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets all movies in all categories. Performs an SQL statement with a JOIN
     * to get the values of the CatMovie table and Movie table. Adds the values
     * to a HashMap and converts it to an ArrayList.
     *
     * @return unhashedPlaylists
     * @throws SQLException
     */
    public List<Movie> getAllMoviesInCategories(int catid) throws SQLException {
        List<Movie> allMoviesWithCat = new ArrayList<>();
        try ( //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            String sql = "SELECT CatMovie.movieId, Movie.id, Movie.title, Movie.duration, Movie.fileLink, CatMovie.categoryId\n"
                    + "FROM CatMovie LEFT JOIN Movie ON CatMovie.movieId = Movie.id WHERE categoryId = " + catid;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("movieId");
                //int categoryId = rs.getInt("categoryId");
                int duration = rs.getInt("duration");
                String fileLink = rs.getString("fileLink");
                String title = rs.getString("title");
                String stringCat = getAllCategoriesOfMovie(id);
                List<Category> categoryList = null;
                //keep an eye on this, wøbbe
                
                allMoviesWithCat.add(new Movie(id, title, duration, categoryList, duration, duration, fileLink, title));
            }
            return allMoviesWithCat;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }

    public String getAllCategoriesOfMovie(int id) throws SQLException {
        ArrayList<String> categoryForMovieList = new ArrayList<String>();
        try ( Connection con = connectDAO.getConnection()) {
            String sql = "SELECT category.name, CatMovie.* FROM Category\n"
                    + "LEFT JOIN CatMovie on categoryid = Category.id\n"
                    + "WHERE CatMovie.movieID = " + id + " ORDER BY Category.id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categoryForMovieList.add(rs.getString("name"));
            }

        } catch (Exception e) {
        }
        String ac = "";
        for (int i = 0; i < categoryForMovieList.size(); i++) {
            if(i == 0)ac=categoryForMovieList.get(i);
            ac = ac + ", "+categoryForMovieList.get(i);
        }
        return ac;
    }

    
    //  shouldn
    public List<Category> getAllCategoriesForCatList(int id) throws SQLException {
        List<Category> movCatList = new ArrayList<>();
        try ( Connection con = connectDAO.getConnection()) {
            String sql = "SELECT category.name, CatMovie.* FROM Category\n"
                    + "LEFT JOIN CatMovie on categoryid = Category.id\n"
                    + "WHERE CatMovie.movieID = " + id + " ORDER BY Category.id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int categoryId = rs.getInt("categoryId");
                String name = rs.getString("name");

                
                movCatList.add(new Category(categoryId, name)); 
                        }

        } catch (Exception e) {
        }
        return movCatList;
    }
    /**
     * Updates the name of the category in the database.
     *
     * @param category The category to be updated.
     * @return The updated category.
     */
    public Category updateCategory(Category category) {
        try (//Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "UPDATE Category SET name = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getId());
            //Execute SQL query.
            pstmt.executeUpdate();

        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    /**
     * Deletes a category in the database. Uses DELETE CASCADE to delete all the
     * songs on the deleted playlist (in the CatMovie database table).
     *
     * @param category The category to delete.
     */
    public void deleteCategory(Category category) {
        //When the category is deleted, the corresponding data in the CatMovie is also deleted.
        try ( //Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "DELETE FROM Category WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setInt(1, category.getId());
            //Execute SQL query.
            pstmt.executeUpdate();
            
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
