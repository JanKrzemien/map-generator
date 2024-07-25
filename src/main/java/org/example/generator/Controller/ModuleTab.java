package org.example.generator.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.config.AppConfig;
import org.example.generator.tiles.Tile;
import org.example.generator.tiles.TileImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ModuleTab extends Controller {
    private static final Logger logger = LogManager.getLogger(ModuleTab.class);

    private TileImageView selectedTileView = null;

    @FXML
    private FlowPane imageListView;
    @FXML
    private AnchorPane tileRulesetPane;
    
    @FXML
    public void initialize() {
        displayImages();
        getConfig().getTileManager().getObservableTilesList().addListener((observed, old_value, new_value) -> displayImages());
        getConfig().getTile_sizeObj().addListener((observed, old_value, new_value) -> imageListView.getChildren().forEach((imgView) -> {
            ((ImageView)imgView).setFitHeight((int)new_value);
            ((ImageView)imgView).setFitWidth((int)new_value);
        }));
    }

    private final EventHandler<MouseEvent> clickEventHandler = (MouseEvent mouseEvent) -> {
        mouseEvent.consume();

        if(selectedTileView != null)
            selectedTileView.getStyleClass().remove("single_selected");

        if (selectedTileView == mouseEvent.getSource()){
            selectedTileView = null;
            return;
        }

        selectedTileView = (TileImageView) mouseEvent.getSource();
        selectedTileView.getStyleClass().add("single_selected");

        logger.debug(selectedTileView.toString());
    };

    private void displayImages() {
        imageListView.getChildren().clear();
        getConfig().getTileManager().getObservableTilesList().forEach((Tile t) -> {
            try {
                TileImageView tile = new TileImageView( new Image(new FileInputStream(t.getPath())), t );
                tile.setFitWidth(getConfig().getTile_size());
                tile.setFitHeight(getConfig().getTile_size());
                tile.setPreserveRatio(false);
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, clickEventHandler);
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
