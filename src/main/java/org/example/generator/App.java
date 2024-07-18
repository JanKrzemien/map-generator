package org.example.generator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.generator.config.AppConfig;
import org.example.generator.config.JSONConfigReader;;

public class App extends Application {
    final public static String DEFAULT_SETTINGS_PATH = "./src/main/resources/settings.default.json";
    final public static String USER_SETTINGS_PATH = "./src/main/resources/settings.json";

    private static final Logger logger = LogManager.getLogger(App.class);

    private AppConfig config = null;

    @Override
    public void start(Stage stage) throws IOException {
        // load apps view
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        logger.info("app's fxml loaded.");

        // load apps config
        if (new File(USER_SETTINGS_PATH).isFile())
            config = JSONConfigReader.load_from_file(USER_SETTINGS_PATH);
        else if (new File(DEFAULT_SETTINGS_PATH).isFile())
            config = JSONConfigReader.load_from_file(DEFAULT_SETTINGS_PATH);

        if (config == null) {
            logger.error("Could not load app config or settings file is empty. Closing application.");
            System.exit(-1);
        }

        //TODO load map tiles from file

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Kiełbasa!");
        stage.setScene(scene);
        logger.info("scene created and set.");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}