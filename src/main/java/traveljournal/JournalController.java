package traveljournal;

import database.DatabaseManager;
import model.Location;
import model.JournalEntry;

public class JournalController {
    private DatabaseManager databaseManager;

    public JournalController() {
        databaseManager = new DatabaseManager();
    }

    public void addLocation(Location location) {
        databaseManager.addLocation(location);
    }

    public void addJournalEntry(JournalEntry entry) {
        databaseManager.addJournalEntry(entry);
    }

}
