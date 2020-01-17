package chilltv.bll.util;

import chilltv.be.Category;
import java.util.List;

/**
 * CategoryConverter Class is used to convert the category list of a movie to a
 * string for the view.
 *
 * @author Martin Emil Rune Wøbbe
 */
public class CategoryConverter {

    /**
     * Converts the category list of a movie to a string for the view.
     *
     * @param cats The list of categories.
     * @return The category list as a string.
     */
    public String convertCategory(List<Category> cats) {
        String cc = "";
        for (int i = 0; i < cats.size(); i++) {
            if (i == 0) {
                cc = cats.get(i).getName();
            } else {
                cc = cc + ", " + cats.get(i).getName();
            }
        }
        return cc;
    }
}
