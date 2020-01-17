package chilltv.be;

import java.util.List;

/**
 * This class is a Filter class. It is used for methods related to filtering
 * movies.
 *
 * @author annem
 */
public class Filter {

    private String query;
    private List<Category> cats;
    private float imdbRating;

    /**
     * The constructor.
     *
     * @param query
     * @param cats
     * @param imdbRating
     */
    public Filter(String query, List<Category> cats, float imdbRating) {
        this.query = query;
        this.cats = cats;
        this.imdbRating = imdbRating;
    }

    /**
     * Gets the search query.
     *
     * @return
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the search query.
     *
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets the list of categories.
     *
     * @return
     */
    public List<Category> getCats() {
        return cats;
    }

    /**
     * Sets the list of categories.
     *
     * @param cats
     */
    public void setCats(List<Category> cats) {
        this.cats = cats;
    }

    /**
     * Gets the imdbRating.
     *
     * @return
     */
    public float getImdbRating() {
        return imdbRating;
    }

    /**
     * Sets the imdbRating.
     *
     * @param imdbRating
     */
    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }
}
