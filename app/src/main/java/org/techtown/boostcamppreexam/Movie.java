package org.techtown.boostcamppreexam;

import java.net.URL;
import java.util.Date;

public class Movie {
    private String title;
    private String actor;
    private String director;
    private int userRating;
    private String link;
    private String imageUrl;
    private String pubDate;

    public Movie(String title, String actor, String director, int userRating, String link, String imageUrl, String pubDate) {
        this.title = title;
        this.actor = actor;
        this.director = director;
        this.userRating = userRating;
        this.link = link;
        this.imageUrl = imageUrl;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

}
