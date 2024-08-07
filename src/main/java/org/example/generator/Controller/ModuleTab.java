package org.example.generator.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.config.AppConfig;
import org.example.generator.tiles.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ModuleTab extends Controller {
    private static final Logger logger = LogManager.getLogger(ModuleTab.class);

    @FXML
    private FlowPane imageListView;
    @FXML
    private AnchorPane tileRulesetPane;

    @FXML
    public void initialize() {
        displayImages();
        getConfig().getTilesObservableObj().addListener((observed, old_value, new_value) -> displayImages());
        getConfig().getTile_sizeObj().addListener((observed, old_value, new_value) -> {
            imageListView.getChildren().forEach((imgView) -> {
                ((ImageView)imgView).setFitHeight((int)new_value);
                ((ImageView)imgView).setFitWidth((int)new_value);
            });
        });
    }

    private void displayImages() {
        imageListView.getChildren().clear();
        getConfig().getTiles().forEach((Tile t) -> {
            try {
                ImageView tile = new ImageView( new Image(new FileInputStream(t.getPath())) );
                tile.setFitWidth(getConfig().getTile_size());
                tile.setFitHeight(getConfig().getTile_size());
                tile.setPreserveRatio(false);
                imageListView.getChildren().add(tile);
            } catch (FileNotFoundException e) {
                logger.error("error while reading image.\n", e);
            }
        });
    }

    public ModuleTab(AppConfig cfg) {
        super(cfg);
    }
}
