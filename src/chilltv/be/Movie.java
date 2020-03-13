package chilltv.be;

import java.util.List;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

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
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty duration = new SimpleIntegerProperty();
    
    private final StringProperty title = new SimpleStringProperty();
    
    private final FloatProperty imdbRating = new SimpleFloatProperty();
    private final IntegerProperty myRating = new SimpleIntegerProperty();
    private final StringProperty fileLink = new SimpleStringProperty();
    private final IntegerProperty lastView = new SimpleIntegerProperty();
    private final StringProperty stringDuration = new SimpleStringProperty();
    private final ObjectProperty<List<Category>> categoryList = new SimpleObjectProperty<>();
    private final StringProperty stringCat = new SimpleStringProperty();
    
    public Movie(int id, String title, int duration, float imdbRating, int myRating, String fileLink, int lastView, List<Category> categoryList) {
        
        setId(id);
        setTitle(title);
        setDuration(duration);
        setImdbRating(imdbRating);
        setMyRating(myRating);
        setFileLink(fileLink);
        setLastView(lastView);
        setCategoryList(categoryList);
    }
    
    public List<Category> getCategoryList() {
        return categoryList.get();
    }
    
    public void setCategoryList(List value) {
        categoryList.set(value);
    }
    
    public ObjectProperty categoryListProperty() {
        return categoryList;
    }
    
    public int getDuration() {
        return duration.get();
    }
    
    public void setDuration(int value) {
        duration.set(value);
    }
    
    public IntegerProperty durationProperty() {
        return duration;
    }
    
    public int getId() {
        return id.get();
    }
    
    public void setId(int value) {
        id.set(value);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    public String getStringCat() {
        return stringCat.get();
    }
    
    public void setStringCat(String value) {
        stringCat.set(value);
    }
    
    public StringProperty stringCatProperty() {
        return stringCat;
    }
    
    public void setCategoryList(ObservableList value) {
        categoryList.set(value);
    }
    
    public String getStringDuration() {
        return stringDuration.get();
    }
    
    public void setStringDuration(String value) {
        stringDuration.set(value);
    }
    
    public StringProperty stringDurationProperty() {
        return stringDuration;
    }
    
    public int getLastView() {
        return lastView.get();
    }
    
    public void setLastView(int value) {
        lastView.set(value);
    }
    
    public IntegerProperty lastViewProperty() {
        return lastView;
    }
    
    public String getFileLink() {
        return fileLink.get();
    }
    
    public void setFileLink(String value) {
        fileLink.set(value);
    }
    
    public StringProperty fileLinkProperty() {
        return fileLink;
    }
    
    public int getMyRating() {
        return myRating.get();
    }
    
    public void setMyRating(int value) {
        myRating.set(value);
    }
    
    public IntegerProperty myRatingProperty() {
        return myRating;
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public void setTitle(String value) {
        title.set(value);
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    
    public float getImdbRating() {
        return imdbRating.get();
    }
    
    public void setImdbRating(float value) {
        imdbRating.set(value);
    }
    
    public FloatProperty imdbRatingProperty() {
        return imdbRating;
    }
    
}
