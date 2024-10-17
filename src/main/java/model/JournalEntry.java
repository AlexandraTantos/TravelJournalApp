package model;

public class JournalEntry {
    private Location location;
    private String notes;

    public JournalEntry(Location location, String notes) {
        this.location = location;
        this.notes = notes;
    }

    public Location getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }
}
