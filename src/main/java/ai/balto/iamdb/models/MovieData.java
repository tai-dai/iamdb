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

            String aType = getFieldType(movie, column);

            if (aType != null && aType.toLowerCase().contains(value.toLowerCase())) {
                someMovies.add(movie);
            }
        }

        return someMovies;
    }

    public static String getFieldType(Movie movie, String fieldName){
        String theValue;
        if (fieldName.equals("name")){
            theValue = movie.getName();
        } else if (fieldName.equals("year")){
            theValue = String.valueOf(movie.getYear());
        } else if (fieldName.equals("origin")){
            theValue = movie.getOrigin().toString();
        } else if (fieldName.equals("director")){
            theValue = movie.getDirector().toString();
        } else if (fieldName.equals("cast")){
            theValue = movie.getCast().toString();
        } else if (fieldName.equals("genre")){
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
                String aGenre = record.get(5);
                String aWiki = record.get(6);
                String aPlot = record.get(7);

                Origin newOrigin = (Origin) findExistingObject(allOrigins, anOrigin);
                if (newOrigin == null){
                    newOrigin = new Origin(anOrigin);
                    allOrigins.add(newOrigin);
                }

                Genre newGenre = (Genre) findExistingObject(allGenres, aGenre);
                if (newGenre == null){
                    newGenre = new Genre(aGenre);
                    allGenres.add(newGenre);
                }

                //Splits translated strings into individual directors
                //TODO: also split at "and"
                String[] stringDirectors = directors.split(", ");
                ArrayList<String> someDirectors = (ArrayList<String>) Arrays.asList(stringDirectors);
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

                //Splits translated strings into individual actors
                //TODO: also split at "and"
                String[] stringActors = actors.split(", ");
                ArrayList<String> someActors = (ArrayList<String>) Arrays.asList(stringActors);
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

                Movie newMovie = new Movie(Integer.getInteger(aYear), aName, newOrigin, filmsDirectors, cast, newGenre, aWiki, aPlot);

                movies.add(newMovie);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
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
}
