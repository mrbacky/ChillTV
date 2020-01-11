package chilltv.be;

import java.util.ArrayList;
import java.util.List;

/**
 * The Category class is an entity class. It represents a table in the database,
 * where each entity instance corresponds to a row in the table. The columns of
 * each row is the attribute of the entity.
 *
 * @author annem
 */
public class Category {

    private final int id;
    private String name;
    private List<Movie> movies;
    private int numberOfMovies;

    /**
     * Constructs a new category.
     *
     * @param id ID of the category.
     * @param name Name of the category.
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        movies = new ArrayList();
        numberOfMovies = movies.size();
    }

    /**
     * Gets the ID of the category.
     *
     * @return The ID of the category.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the category.
     *
     * @return The name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of movies in the category.
     *
     * @return The list of movies.
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Gets the number of movies in the category.
     *
     * @return The number of movies.
     */
    public int getNumberOfMovies() {
        return numberOfMovies;
    }

    /**
     * Adds a movie to a category.
     *
     * @param movie The movie to add.
     */
    public void addMovie(Movie movie) {
        movies.add(movie);
        numberOfMovies = movies.size();
    }

    /**
     * Removes a movie from a category.
     *
     * @param movie The movie to remove.
     */
    public void removeMovie(Movie movie) {
        movies.remove(movie);
        numberOfMovies--;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}