package chilltv.dal.util;

import chilltv.dal.DBConnectionProvider;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class CategoryConverter {

    DBConnectionProvider cp = new DBConnectionProvider();


    public String getCategoriesOnMovies(int id) throws SQLServerException, SQLException {
        ArrayList cat = new ArrayList();
        String stat ="SELECT category.name, CatMovie.* FROM Category\n" +
                "LEFT JOIN CatMovie on categoryid = Category.id\n" +
                "WHERE CatMovie.movieID = "+ id + " ORDER BY Category.id";
        try ( Connection con = cp.getConnection()) {
            java.sql.Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(stat);

            while(rs.next()){
                cat.add(rs.getString("name"));
            }
            String ac = String.join(", ", cat);
            return ac;
            //return cat;
        }
    }
}