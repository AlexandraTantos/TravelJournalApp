package model;

public class Photo extends MediaContent {
    public Photo(String filePath, String description) {
        super(filePath, description);
    }

    @Override
    public void displayContent() {
        System.out.println("Displaying photo: " + getDescription());
    }
}
