package chilltv.be;

/**
 * The Category class is an entity class. It represents a table in the database,
 * where each entity instance corresponds to a row in the table. The columns of
 * each row is the attribute of the entity.
 *
 * @author Radoslav Backovsky
 * @author Louise Lauenborg
 * @author Anne Luong
 * @author Martin Emil Rune Wøbbe
 */
public class Category {

    private final int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
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
  
    @Override
    public String toString() {
        return name;
    }
}