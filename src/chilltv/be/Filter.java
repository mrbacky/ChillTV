package chilltv.be;

import java.util.List;

/**
 *
 * @author annem
 */
public class Filter {
    private String query;
    private List<Category> cats;
    private float imdbRating;

    public Filter(String query, List<Category> cats, float imdbRating) {
        this.query = query;
        this.cats = cats;
        this.imdbRating = imdbRating;
    } 

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Category> getCats() {
        return cats;
    }

    public void setCats(List<Category> cats) {
        this.cats = cats;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }    
}
