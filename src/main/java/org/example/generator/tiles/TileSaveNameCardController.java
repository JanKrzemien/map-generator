package org.example.generator.tiles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.config.AppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class TileSaveNameCardController {
    private static final Logger logger = LogManager.getLogger(TileSaveNameCardController.class);

    @FXML
    private ImageView tileImg;
    @FXML
    private TextField tileName;
    @FXML
    private AnchorPane tileNameCardRoot;

    private File file;
    private AppConfig cfg;
    private Consumer<AnchorPane> removeAction;

    public TileSaveNameCardController() {}

    public TileSaveNameCardController(File f, AppConfig cfg, Consumer<AnchorPane> removeAction) {
        file = f;
        this.cfg = cfg;
        this.removeAction = removeAction;
    }

    @FXML
    public void initialize() {
        logger.info("TileSaveNameCardController initialized with {} file.", file);

        try {
            tileImg.setImage(new Image(new FileInputStream(file.getPath())));
        } catch (FileNotFoundException ex) {
            logger.error("tile image {} doesn't exist.", file.getPath());
        } catch (Exception ex) {
            logger.debug("Error while initializing TileSaveNameCardController with file.");
        }
    }

    @FXML
    private void setName() {
        String name = tileName.getText();

        // TODO if name is not unique show some error message
        if (!cfg.getTileManager().checkIfTileNameIsUnique(name))
            return;

        cfg.getTileManager().addTile(new Tile(name, file.getPath()));

        removeAction.accept(tileNameCardRoot);
    }

}
