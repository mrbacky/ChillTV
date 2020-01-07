/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilltv.be;

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
    private int lastView;

    public Movie(int id, String title, int duration, int imdbRating, int myRating, String fileLink, int lastView) {

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

    public void setTitle(String name) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(int rating) {
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

}
