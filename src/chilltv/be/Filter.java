package chilltv.be;

import java.util.List;

/**
 *
 * @author annem
 */
public class Filter {
    private String query;
    private List<Category> cats;

    public Filter(String query, List<Category> cats) {
        this.query = query;
        this.cats = cats;
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
}
