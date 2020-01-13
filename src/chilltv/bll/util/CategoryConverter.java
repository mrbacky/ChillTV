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
        List<Category> categoryList = movie.getCategory();
        ArrayList<String> categoryArrayList = new ArrayList<>();
//        for (Category category : categoryList) {
//            categoryArrayList.add(category.getName());
//
//        }

        String ac = String.join(", ", categoryArrayList.toString());
        return ac;
    }

}
