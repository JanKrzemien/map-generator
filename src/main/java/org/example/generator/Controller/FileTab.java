package org.example.generator.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.App;
import org.example.generator.config.AppConfig;
import org.example.generator.tiles.JSONTilesHandler;
import org.example.generator.tiles.TileSaveNameCardController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTab extends Controller {
    private static final Logger logger = LogManager.getLogger(FileTab.class);

    @FXML
    private Label uploadFilesLabel;
    @FXML
    private FlowPane saveTileNameCardContainer;

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

        // ask user to set name for each Tile
        uploadedFiles.forEach(f -> {
            try {
                FXMLLoader saveTileNameLoader = new FXMLLoader(App.class.getResource("saveTileNameCard.fxml"));
                saveTileNameLoader.setController(new TileSaveNameCardController(f, getConfig(), this::removeSaveTileNameCard));
                saveTileNameCardContainer.getChildren().add(saveTileNameLoader.load());
            } catch (IOException ex) {
                logger.error("could not load tile name card, IOException.\n", ex);
            } catch (Exception ex) {
                logger.error("fucked it's self over.\n", ex);
            }

        });
    }

    private void removeSaveTileNameCard(AnchorPane root) {
        saveTileNameCardContainer.getChildren().remove(root);
    }

    public FileTab(AppConfig cfg) {
        super(cfg);
    }
}
