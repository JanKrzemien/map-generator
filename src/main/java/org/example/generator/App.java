package org.example.generator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.generator.config.AppConfig;
import org.example.generator.tiles.ArrayOfTiles;
import org.example.generator.tiles.Tile;
import org.example.generator.util.JSONHandler;

public class App extends Application {
    private static final Logger logger = LogManager.getLogger(App.class);

    private final static String STYLES_FILE_PATH = "styles.css";
    private final static String EMPTY_JSON_ARRAY_STRING = "[]";

    private AppConfig config = null;
    public AppConfig getConfig() {
        return config;
    }

    private AppConfig loadAppsConfig() {
        JSONHandler<AppConfig> configHandler = new JSONHandler<>(AppConfig.class);
        File settingsFile = new File(AppConfig.USER_SETTINGS_PATH);
        File defaultSettingsFile = new File(AppConfig.DEFAULT_SETTINGS_PATH);

        if (settingsFile.isFile())
            config = configHandler.load_from_file(settingsFile.getPath());
        else if (defaultSettingsFile.isFile()) {
            config = configHandler.load_from_file(defaultSettingsFile.getPath());
            copy_files_content(defaultSettingsFile, settingsFile);
        }
        return config;
    }

    private ArrayList<Tile> loadSavedTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        File tilesFile = new File(ArrayOfTiles.TILES_FILE);
        JSONHandler<ArrayOfTiles> tilesHandler = new JSONHandler<>(ArrayOfTiles.class);

        if (tilesFile.isFile())
            tiles.addAll(tilesHandler.load_from_file(ArrayOfTiles.TILES_FILE).getTiles());
        else try (BufferedWriter bw = new BufferedWriter(new FileWriter(tilesFile))) {
            bw.write(EMPTY_JSON_ARRAY_STRING);
        } catch (IOException e) {
            logger.error("error while writing empty json to new file.\n", e);
        }

        return tiles;
    }

    private Object controllerFactory(Class<?> c) {
        try {
            return c.getConstructor(AppConfig.class).newInstance(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Scene createSceneWithRoot(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(App.class.getResource("styles.css")));
        return scene;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // load apps view
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        logger.info("app's fxml loaded.");

        AppConfig config = loadAppsConfig();
        if (config == null) {
            logger.error("Could not load app config or settings file is empty. Closing application.");
            System.exit(-1);
        }

        config.getTileManager().addTiles( loadSavedTiles() );

        fxmlLoader.setControllerFactory(this::controllerFactory);
        stage.setTitle("Kiełbasa!");
        stage.setScene( createSceneWithRoot( fxmlLoader.load() ) );
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