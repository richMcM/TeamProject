package quotedecor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quotedecor.config.DB;
import quotedecor.util.URLS;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(URLS.LOGIN));
            primaryStage.setTitle("QuoteDecor");
            Scene scene = new Scene(root, 1200 , 600);
            scene.getStylesheets().add(Main.class.getResource("css/bootstrapfx.css").toExternalForm());
            scene.getStylesheets().add(Main.class.getResource("css/global.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException {

        DB db = new DB();
        db.printTableAll();
        db.printTableNames();
        launch(args);
    }
}
