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
    private int imdbRating;
    private int myRating;
    private String fileLink;
    private String lastView;
    private String stringDuration;

    public Movie(int id, String title, int duration, int imdbRating, int myRating, String fileLink, String lastView, List<Category> category) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.imdbRating = imdbRating;
        this.myRating = myRating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.category = category;
    }

    
    //  
    private List<Category> category;
    
    private String stringCat;

    

    //  JSoup for imDB Rating
    //  tmdb
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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStringDuration() {
        return stringDuration;
    }

    public void setStringDuration(String stringDuration) {
        this.stringDuration = stringDuration;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(int imdbRating) {
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

    public String getLastView() {
        return lastView;
    }

    public void setLastView(String lastView) {
        this.lastView = lastView;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getStringCat() {
        return stringCat;
    }

    public void setStringCat(String stringCat) {
        this.stringCat = stringCat;
    }
}
