package org.example.generator;

import com.google.gson.JsonParseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.generator.config.AppConfig;
import org.example.generator.config.JSONConfigHandler;

public class App extends Application {
    private static final Logger logger = LogManager.getLogger(App.class);

    private AppConfig config = null;
    public AppConfig getConfig() {
        return config;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // load apps view
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        logger.info("app's fxml loaded.");

        // load apps config
        try {
            File settingsFile;
            JSONConfigHandler configHandler = new JSONConfigHandler();
            settingsFile = new File(JSONConfigHandler.USER_SETTINGS_PATH);
            if (settingsFile.isFile())
                config = configHandler.load_from_file(JSONConfigHandler.USER_SETTINGS_PATH);
            else { // if default settings exist and user settings does not copy default settings to user settings
                settingsFile = new File(JSONConfigHandler.DEFAULT_SETTINGS_PATH);
                if (settingsFile.isFile()) {
                    config = configHandler.load_from_file(JSONConfigHandler.DEFAULT_SETTINGS_PATH);
                    copy_files_content(settingsFile, new File(JSONConfigHandler.USER_SETTINGS_PATH));
                }
            }
        } catch (JsonParseException ex) {
            logger.error("Could not parse config file, possibly could not convert between data types.");
            System.exit(-1);
        }

        if (config == null) {
            logger.error("Could not load app config or settings file is empty. Closing application.");
            System.exit(-1);
        }

        // create and add scene
        fxmlLoader.setControllerFactory(c -> {
            try {
                return c.getConstructor(AppConfig.class).newInstance(config);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Kiełbasa!");
        stage.setScene(scene);
        logger.info("scene created and set.");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void copy_files_content(File fileA, File fileB) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileA));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileB))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            bw.write(content.toString());
        } catch (IOException e) {
            logger.error("error while copying default settings to user settings.\n", e);
        }
    }
}