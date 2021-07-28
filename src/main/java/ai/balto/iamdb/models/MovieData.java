package ai.balto.iamdb.models;

import ai.balto.iamdb.NameSorter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieData {

    private static final String DATA_FILE = "movie_plots.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<Movie> movies;
    private static ArrayList<String> allNames = new ArrayList<>();
    private static ArrayList<Origin> allOrigins = new ArrayList<>();
    private static ArrayList<Director> allDirectors = new ArrayList<>();
    private static ArrayList<Actor> allActors = new ArrayList<>();
    private static ArrayList<Genre> allGenres = new ArrayList<>();

    //Search function which lists everything
    public static ArrayList<Movie> findAll() {

        loadData();

        return new ArrayList<>(movies);
    }

    //Search function which lists movies matching input in the selected column
    public static ArrayList<Movie> findByColumnAndValue(String column, String value) {

        loadData();

        ArrayList<Movie> someMovies = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return findAll();
        }

        if (column.equals("all")){
            someMovies = findByValue(value);
            return someMovies;
        }
        for (Movie movie : movies) {

            String aType = getFieldValue(movie, column);

            if (aType != null && aType.toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            }
        }

        return someMovies;
    }

    public static String getFieldValue(Movie movie, String value){
        String theValue;
        if (value.equals("name")){
            theValue = movie.getName();
//            TODO add year search functionality
        } else if (value.equals("year")){
            theValue = String.valueOf(movie.getYear());
        } else if (value.equals("origin")){
            theValue = movie.getOrigin().toString();
        } else if (value.equals("director")){
            theValue = movie.getDirector().toString();
        } else if (value.equals("cast")){
            theValue = movie.getCast().toString();
        } else if (value.equals("genre")){
            theValue = movie.getGenre().toString();
        } else {
            theValue = movie.getPlot();
        }

        return theValue;
    }

    //Search function that looks for user input in every movie field
    public static ArrayList<Movie> findByValue(String value) {

        loadData();

        ArrayList<Movie> someMovies = new ArrayList<>();

        for (Movie movie : movies) {

            if (movie.getName().toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            } else if (String.valueOf(movie.getYear()).contains(value.toLowerCase())) {
                someMovies.add(movie);
            } else if (movie.getOrigin().toString().toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            } else if (movie.getDirector().toString().toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            } else if (movie.getCast().toString().toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            } else if (movie.getGenre().toString().toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            } else if (movie.getPlot().toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            }

        }

        return someMovies;
    }

    //Checks if Object exists
    private static Object findExistingObject(ArrayList list, String value){
        for (Object item : list){
            if (item.toString().toLowerCase().equals(value.toLowerCase())){
                return item;
            }
        }
        return null;
    }

    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Resource resource = new ClassPathResource(DATA_FILE);
            InputStream is = resource.getInputStream();
            Reader reader = new InputStreamReader(is);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            movies = new ArrayList<>();

            // Translates CSV into readable Strings
            for (CSVRecord record : records) {

                String aYear = record.get(0);
                String aName = record.get(1);
                String anOrigin = record.get(2);
                String directors = record.get(3);
                String actors = record.get(4);
                String genres = record.get(5);
                String aWiki = record.get(6);
                String aPlot = record.get(7);

                Origin newOrigin = (Origin) findExistingObject(allOrigins, anOrigin);
                if (newOrigin == null){
                    newOrigin = new Origin(anOrigin);
                    allOrigins.add(newOrigin);
                }

                ArrayList<Director> filmsDirectors = directorCleanup(directors);
                ArrayList<Actor> cast = actorCleanup(actors);
                ArrayList<Genre> filmsGenres = genreCleanup(genres);

                Movie newMovie = new Movie(Integer.parseInt(aYear), aName, newOrigin, filmsDirectors, cast, filmsGenres, aWiki, aPlot);

                movies.add(newMovie);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

    public static void addMovie(int year, String name, String origin, String directors, String actors, String genres, String wiki, String plot){
        Origin newOrigin = (Origin) findExistingObject(allOrigins, origin);
        if (newOrigin == null){
            newOrigin = new Origin(origin);
            allOrigins.add(newOrigin);
        }

        ArrayList<Director> filmsDirectors = directorCleanup(directors);
        ArrayList<Actor> cast = actorCleanup(actors);
        ArrayList<Genre> filmsGenres = genreCleanup(genres);

        Movie newMovie = new Movie(year, name, newOrigin, filmsDirectors, cast, filmsGenres, wiki, plot);

        movies.add(newMovie);
    }

    public static ArrayList<String> getAllNames() {
        loadData();
        allNames.sort(new NameSorter());
        return allNames;
    }

    public static ArrayList<Origin> getAllOrigins() {
        loadData();
        allOrigins.sort(new NameSorter());
        return allOrigins;
    }

    public static ArrayList<Director> getAllDirectors() {
        loadData();
        allDirectors.sort(new NameSorter());
        return allDirectors;
    }

    public static ArrayList<Actor> getAllActors() {
        loadData();
        allActors.sort(new NameSorter());
        return allActors;
    }

    public static ArrayList<Genre> getAllGenres() {
        loadData();
        allGenres.sort(new NameSorter());
        return allGenres;
    }

    //TODO dry this code out and make em 1 whole heccin method
    public static ArrayList<Director> directorCleanup(String directors){
        String cleanDirectors = directors.replace(" and ", ", ");
        String[] stringDirectors = cleanDirectors.split(", ");
        ArrayList<String> someDirectors = new ArrayList<>(Arrays.asList(stringDirectors));
        ArrayList<Director> filmsDirectors = new ArrayList<>();
        for (String aDirector : someDirectors){
            Director newDirector;
            newDirector= (Director) findExistingObject(allDirectors, aDirector);

            if (newDirector == null){
                newDirector = new Director(aDirector);
                allDirectors.add(newDirector);
            }
            filmsDirectors.add(newDirector);
        }
        return filmsDirectors;
    }

    public static ArrayList<Actor> actorCleanup(String actors){
        String cleanActors = actors.replace(" and ", ", ");
        String[] stringActors = cleanActors.split(", ");
        ArrayList<String> someActors = new ArrayList<>(Arrays.asList(stringActors));
        ArrayList<Actor> cast = new ArrayList<>();
        for (String anActor : someActors){
            Actor newActor;
            newActor = (Actor) findExistingObject(allActors, anActor);

            if (newActor == null){
                newActor = new Actor(anActor);
                allActors.add(newActor);
            }
            cast.add(newActor);
        }
        return cast;
    }

    public static ArrayList<Genre> genreCleanup(String genres){
        if (genres == "" || genres == "-"){
            genres = "unknown";
        }
        String cleanGenres = genres.replace(" / ", ", ");
        String cleanerGenres = cleanGenres.replace("/", ", ");
        String[] stringGenres = cleanerGenres.split(", ");
        ArrayList<String> someGenres = new ArrayList<>(Arrays.asList(stringGenres));
        ArrayList<Genre> filmsGenres = new ArrayList<>();

        for (String aGenre : someGenres){
            Genre newGenre;

            newGenre = (Genre) findExistingObject(allGenres, aGenre);

            if (newGenre == null){
                newGenre = new Genre(aGenre);
                allGenres.add(newGenre);
            }
            filmsGenres.add(newGenre);
        }
        return filmsGenres;
    }
}
