package org.example.generator.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.config.AppConfig;
import org.example.generator.config.JSONConfigHandler;

import java.util.Objects;

public class SettingsTab extends Controller {
    private static final Logger logger = LogManager.getLogger(SettingsTab.class);

    JSONConfigHandler configHandler;

    @FXML
    private Button saveSettingsBtn;
    @FXML
    private TextField tileSize;

    private ToggleGroup tg;
    @FXML
    private RadioButton squareRadioBtn;
    @FXML
    private RadioButton hexRadioBtn;

    public SettingsTab(AppConfig cfg) {
        super(cfg);

        configHandler = new JSONConfigHandler();
    }

    @FXML
    public void initialize() {
        logger.info("Settings controller initialize method.");

        tg = new ToggleGroup();
        squareRadioBtn.setToggleGroup(tg);
        hexRadioBtn.setToggleGroup(tg);

        saveSettingsBtn.setOnAction(this::saveSettings);

        // set values from config file
        tileSize.setText(Integer.toString( getConfig().getTile_size() ));
        if(Objects.equals(getConfig().getTile_shape(), "hex")) {
            hexRadioBtn.setSelected(true);
            radioBtnValue = "hex";
        } else if(Objects.equals(getConfig().getTile_shape(), "square")) {
            squareRadioBtn.setSelected(true);
            radioBtnValue = "square";
        }

    }

    public void saveSettings(ActionEvent e) {
        if(Objects.equals(radioBtnValue, "") || Objects.equals(tileSize.getText(), "")) {
            return; // TODO implement showing some error Alert or something
        }

        // update AppConfig object with current data
        try {
            getConfig().setTile_size(Integer.parseInt(tileSize.getText()));
        } catch (NumberFormatException ex) {
            return; // TODO implement showing some error Alert or something
        }

        getConfig().setTile_shape(radioBtnValue);

        configHandler.write_to_file(JSONConfigHandler.USER_SETTINGS_PATH, getConfig());
    }

    private String radioBtnValue;
    public void handleRadioBtn(String text) {
        radioBtnValue = text;
    }
    @FXML
    public void handleHexRadioBtn() {handleRadioBtn("hex");}
    @FXML
    public void handleSquareRadioBtn() {handleRadioBtn("square");}
}
