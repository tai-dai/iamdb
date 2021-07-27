package ai.balto.iamdb.models;

import java.util.List;

public class Movie {

    //properties:
        private int year;
        private String name;
        private Origin origin;
        // American, Australian, Bangladeshi, British, Canadian, Chinese, Egyptian, Hong Kong, Filipino, Assamese, Bengali, Bollywood, Kannada, Malayalam, Marathi, Punjabi, Tamil, Telugu, Japanese, Malaysian, Maldivian, Russian, South_Korean, Turkish
        private List<Director> director;
        private List<Actor> cast;
        private Genre genre;
        private String wiki = "unknown";
        private String plot;

    public Movie(int year, String name, Origin origin, List<Director> director, List<Actor> cast, Genre genre, String wiki, String plot) {
        this.year = year;
        this.name = name;
        this.origin = origin;
        this.director = director;
        this.cast = cast;
        this.genre = genre;
        this.wiki = wiki;
        this.plot = plot;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public List<Actor> getCast() {
        return cast;
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
