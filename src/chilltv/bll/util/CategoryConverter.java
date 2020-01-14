/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilltv.bll.util;

import chilltv.be.Category;
import chilltv.be.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin
 */
public class CategoryConverter {

    public String convertCategory(Movie movie) {
        List<Category> categories = movie.getCategory();
        List<String> stringCats = new ArrayList<>();
        if (categories!=null) {
            
            for (Category category : categories) {
                stringCats.add(category.getName());
                //  id...
            }
            
            System.out.println(stringCats);
        }

        //String ac = String.join(", ", categoryArrayList.toString());
        return "asd";
    }

}
