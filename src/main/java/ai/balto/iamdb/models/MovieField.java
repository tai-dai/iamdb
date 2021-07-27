package ai.balto.iamdb.models;

import java.util.Objects;

public class MovieField {
    private int id;
    private static int nextId = 1;
    private String type;

    public MovieField() {
        id = nextId;
        nextId++;
    }

    public MovieField(String type) {
        this();
        this.type = type;
    }

    //Overrride methods
    @Override
    public String toString() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieField)) return false;
        MovieField movieField = (MovieField) o;
        return id == movieField.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    // Getters and setters
    public int getId() { return id; }
    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
