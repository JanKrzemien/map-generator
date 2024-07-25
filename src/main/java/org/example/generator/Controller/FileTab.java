package org.example.generator.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.config.AppConfig;
import org.example.generator.tiles.JSONTilesHandler;
import org.example.generator.tiles.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTab extends Controller {
    private static final Logger logger = LogManager.getLogger(FileTab.class);

    private JSONTilesHandler tilesHandler;

    @FXML
    private Label uploadFilesLabel;

    public void uploadFilesHandler(ActionEvent e) {
        FileChooser fc = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        // get selected files
        List<File> files = fc.showOpenMultipleDialog(new Stage());
        if(files == null)
            return;

        ArrayList<File> uploadedFiles = new ArrayList<>(files);

        // display how many files got uploaded
        uploadFilesLabel.setText("Wgrano " + uploadedFiles.size() + " plików.");

        // for each File create Tile with constructor
        ArrayList<Tile> newTiles = new ArrayList<Tile>();
        uploadedFiles.forEach(f -> newTiles.add(new Tile(f.getPath())));

        // add new tiles to config
        getConfig().getTileManager().addTiles(newTiles);

        // write updated tiles from config to file
        tilesHandler.write_to_file(JSONTilesHandler.TILES_FILE, getConfig().getTileManager().getTiles());
    }

    public FileTab(AppConfig cfg) {
        super(cfg);
        tilesHandler = new JSONTilesHandler();
    }
}
