package chilltv.be;

import java.util.List;

/**
 * The Movie class is an entity class. It represents a table in the database,
 * where each entity instance corresponds to a row in the table. The columns of
 * each row is the attribute of the entity.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
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

    /**
     * Gets the ID of the movie.
     *
     * @return The ID of the movie.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the duration of the movie.
     *
     * @return The duration of the movie.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the duration of the movie.
     *
     * @param duration The duration to set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the imdbRating of the movie.
     *
     * @return The imdbRating of the movie.
     */
    public float getImdbRating() {
        return imdbRating;
    }

    /**
     * Sets the imdbRating of the movie.
     *
     * @param imdbRating The imdbRating to set.
     */
    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    /**
     * Gets the personal rating of the movie.
     *
     * @return The personal rating of the movie.
     */
    public int getMyRating() {
        return myRating;
    }

    /**
     * Sets the personal rating of the movie.
     *
     * @param myRating The personal rating to set.
     */
    public void setMyRating(int myRating) {
        this.myRating = myRating;
    }

    /**
     * Gets the file location of the movie.
     *
     * @return The file location of the movie.
     */
    public String getFileLink() {
        return fileLink;
    }

    /**
     * Sets the file location of the movie.
     *
     * @param fileLink The fileLink to set.
     */
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    /**
     * Gets the last view of the movie.
     *
     * @return The last view of the movie.
     */
    public int getLastView() {
        return lastView;
    }

    /**
     * Sets the last view of the movie.
     *
     * @param lastView The last view to set.
     */
    public void setLastView(int lastView) {
        this.lastView = lastView;
    }

    /**
     * Gets the duration of the movie as a String.
     *
     * @return The duration of the movie as a String.
     */
    public String getStringDuration() {
        return stringDuration;
    }

    /**
     * Sets the duration of the movie as a String.
     *
     * @param stringDuration The duration of the movie as a String.
     */
    public void setStringDuration(String stringDuration) {
        this.stringDuration = stringDuration;
    }

    /**
     * Gets the category list of the movie.
     *
     * @return The category list of the movie.
     */
    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * Sets the category list of the movie.
     *
     * @param categoryList The category list of the movie to set.
     */
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * Gets the category list of the movie as a String.
     *
     * @return The category list of the movie as a String.
     */
    public String getStringCat() {
        return stringCat;
    }

    /**
     * Sets the category list of the movie as a String.
     *
     * @param stringCat The category list of the movie as a String to set.
     */
    public void setStringCat(String stringCat) {
        this.stringCat = stringCat;
    }

    @Override
    public String toString() {
        return title;
    }
}
