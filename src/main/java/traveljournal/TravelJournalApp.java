package traveljournal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.TravelJournalView;

public class TravelJournalApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        TravelJournalView travelJournalView = new TravelJournalView();
        Scene scene = new Scene(travelJournalView.getRoot(), 800, 600);
        primaryStage.setTitle("Travel Journal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
