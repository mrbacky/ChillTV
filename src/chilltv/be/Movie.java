package chilltv.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Movie {

    private int id;
    private String title;
    private int duration;
    private float imdbRating;
    private int myRating;
    private String fileLink;
    private int lastView;
    private String stringDuration;
    private List<Category> categoryList;
    private String stringCat;

    public Movie(int id, String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> categoryList) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.imdbRating = imdbRating;
        this.myRating = myRating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.categoryList = categoryList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getMyRating() {
        return myRating;
    }

    public void setMyRating(int myRating) {
        this.myRating = myRating;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public int getLastView() {
        return lastView;
    }

    public void setLastView(int lastView) {
        this.lastView = lastView;
    }

    public String getStringDuration() {
        return stringDuration;
    }

    public void setStringDuration(String stringDuration) {
        this.stringDuration = stringDuration;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getStringCat() {
        return stringCat;
    }

    public void setStringCat(String stringCat) {
        this.stringCat = stringCat;
    }

    @Override
    public String toString(){
        return title;
    }
    
    
}
