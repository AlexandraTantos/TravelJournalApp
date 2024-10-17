package model;

public abstract class MediaContent {
    protected String filePath;
    protected String description;

    public MediaContent(String filePath, String description) {
        this.filePath = filePath;
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDescription() {
        return description;
    }

    public abstract void displayContent();
}
