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
import org.example.generator.tiles.TileSaveNameCardController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileTab extends Controller {
    private static final Logger logger = LogManager.getLogger(FileTab.class);

    @FXML
    private Label uploadFilesLabel;
    @FXML
    private FlowPane saveTileNameCardContainer;

    private void setExtensionFilters(FileChooser fc) {
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fc.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
    }

    private List<File> getSelectedFiles(FileChooser fc) {
        return fc.showOpenMultipleDialog(new Stage());
    }

    private void displaySaveNameCard(File f) {
        try {
            FXMLLoader saveTileNameLoader = new FXMLLoader(App.class.getResource("saveTileNameCard.fxml"));
            saveTileNameLoader.setController(new TileSaveNameCardController(f, getConfig(), this::removeSaveTileNameCard));
            saveTileNameCardContainer.getChildren().add(saveTileNameLoader.load());
        } catch (IOException e) {
            logger.error("could not load tile name card, IOException.\n", e);
        } catch (Exception e) {
            logger.error("fucked it's self over.\n", e);
        }
    }

    public void uploadFilesHandler(ActionEvent e) {
        FileChooser fc = new FileChooser();

        setExtensionFilters(fc);

        List<File> files = getSelectedFiles(fc);
        if( files == null )
            return;

        uploadFilesLabel.setText("Wgrano " + files.size() + " plików.");

        files.forEach(this::displaySaveNameCard);
    }

    private void removeSaveTileNameCard(AnchorPane root) {
        saveTileNameCardContainer.getChildren().remove(root);
    }

    public FileTab(AppConfig cfg) {
        super(cfg);
    }
}
