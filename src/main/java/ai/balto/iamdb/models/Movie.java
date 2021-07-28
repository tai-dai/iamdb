package ai.balto.iamdb.models;

import java.util.List;

public class Movie extends MovieField{


        private int year;
        private String name;
        private Origin origin;
        private List<Director> director;
        private List<Actor> cast;
        private List<Genre> genre;
        private String wiki = "unknown";
        private String plot;

    public Movie(int year, String name, Origin origin, List<Director> director, List<Actor> cast, List<Genre> genre, String wiki, String plot) {
        this.year = year;
        this.name = name;
        this.origin = origin;
        this.director = director;
        this.cast = cast;
        this.genre = genre;
        this.wiki = wiki;
        this.plot = plot;
    }

    //Getters and Setters
    public int getYear() {
        return year;
    }
    public String getName() {
        return name;
    }
    public Origin getOrigin() {
        return origin;
    }
    public List<Director> getDirector() {
        return director;
    }
    public List<Actor> getCast() {
        return cast;
    }
    public List<Genre> getGenre() {
        return genre;
    }
    public String getWiki() {
        return wiki;
    }
    public String getPlot() {
        return plot;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }
    public void setDirector(List<Director> director) {
        this.director = director;
    }
    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }
    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }
    public void setWiki(String wiki) {
        this.wiki = wiki;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
}
