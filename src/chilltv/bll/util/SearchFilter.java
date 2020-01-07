package chilltv.bll.util;

import chilltv.be.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 * The SearchFilter class is a tool used to filter out song items, which match
 * the search query.
 *
 * @author Anne Luong
 */
public class SearchFilter {

    /**
     * Filters a list of movies and returns a filtered list matching the search
     * query.
     *
     * @param searchBase The list of movies to filter.
     * @param query The search query.
     * @return A list of movies that matches the search query.
     */
    public List<Movie> search(List<Movie> searchBase, String query) {
        //case insensitive and partial search
        List<Movie> filtered = new ArrayList();

        if (query.isEmpty()) {
            return searchBase;
        }
        for (Movie movie : searchBase) {
            if (movie.getTitle().toLowerCase().contains(query.trim().toLowerCase())) {
                filtered.add(movie);
            }
        }
        return filtered;
    }
}
