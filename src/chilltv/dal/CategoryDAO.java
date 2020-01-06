package chilltv.dal;

import chilltv.be.Category;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The CategoryDAO class can perform CRUD operations on the Category database table.
 *
 * @author Anne Luong
 */
public class CategoryDAO {

    private final DBConnectionProvider connectDAO;

    public CategoryDAO() {
        connectDAO = new DBConnectionProvider();
    }

    /**
     * Creates and adds a new category to the database.
     *
     * @param name The name of the newly created category.
     */
    public void createCategory(String name) {
        try ( //Get a connection to the database.
            Connection con = connectDAO.getConnection()) {
            String sql = "INSERT INTO Category VALUES (?)";
            //Create a prepared statement.
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter value.
            pstmt.setString(1, name);
            //Execute SQL query.
            pstmt.executeUpdate();

        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets all the categories from the database table Category.
     *
     * @return A list of all the categories.
     */
    public List<String> getAllCategories() {
        //Create a String array to store all categories.
        List<String> allCategories = new ArrayList();
        try ( //Get a connection to the database.
            Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "SELECT * FROM Category";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //Add the categories to the String array.
                allCategories.add(rs.getString("name"));
            }
            return allCategories;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates the name of the category in the database.
     *
     * @param category The category to be updated.
     * @param editedName The edited name of the category.
     * @return The updated category.
     */
    public Category updateCategory(Category category, String editedName) {
        try (//Get a connection to the database.
            Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "UPDATE Category SET name = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, editedName);
            pstmt.setInt(2, category.getId());
            //Execute SQL query.
            pstmt.executeUpdate();
            category.setName(editedName);
            return category;

        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Deletes a category in the database.
     *
     * @param name The name of the deleted category.
     */
    public void deleteCategory(String name) {
        try ( //Get a connection to the database.
            Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "DELETE FROM Category WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, name);
            //Execute SQL query.
            pstmt.executeUpdate();
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
