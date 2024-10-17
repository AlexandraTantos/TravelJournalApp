package model;

public class Location {
    private String name;
    private String country;
    private String dateVisited;
    private String description;
    private String imagePath;

    public Location(String name, String country, String dateVisited, String description, String imagePath) {
        this.name = name;
        this.country = country;
        this.dateVisited = dateVisited;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getDateVisited() {
        return dateVisited;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

