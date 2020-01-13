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
    
    public String convertCategory(Movie movie){
        ArrayList categoryArray = new ArrayList();
        List<Category> categoryList = movie.getCategory();
        for (Category category : categoryList) {
            categoryArray.add(category.getName());
        }
       
        String ac = String.join(", ",categoryArray);
            return ac;
    }
    
}
