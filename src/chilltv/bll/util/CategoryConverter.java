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

    public String convertCategory(List<Category> cats){
    String cc = "";
    for (int i = 0; i < cats.size(); i++) {
            if(i == 0)cc=cats.get(i).getName();
            cc = cc + ", "+cats.get(i).getName();
        }
        return cc;
    }

}
