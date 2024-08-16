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
import org.example.generator.util.JSONHandler;

import java.util.Objects;

public class SettingsTab extends Controller {
    private static final Logger logger = LogManager.getLogger(SettingsTab.class);

    JSONHandler<AppConfig> configHandler;

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

        configHandler = new JSONHandler<>(AppConfig.class);
    }

    private void setValuesFromConfigFile() {
        tg = new ToggleGroup();
        squareRadioBtn.setToggleGroup(tg);
        hexRadioBtn.setToggleGroup(tg);

        tileSize.setText(Integer.toString( getConfig().getTile_size() ));
        if(Objects.equals(getConfig().getTile_shape(), "hex")) {
            hexRadioBtn.setSelected(true);
            radioBtnValue = "hex";
        } else if(Objects.equals(getConfig().getTile_shape(), "square")) {
            squareRadioBtn.setSelected(true);
            radioBtnValue = "square";
        }
    }

    @FXML
    public void initialize() {
        logger.info("Settings controller initialize method.");

        saveSettingsBtn.setOnAction(this::saveSettings);

        setValuesFromConfigFile();
    }

    private boolean inputIsValid(String radioBtnValue, TextField tileSize) {
        if(Objects.equals(radioBtnValue, "") || Objects.equals(tileSize.getText(), ""))
            return false;

        try {
            Integer.parseInt(tileSize.getText());
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    private Integer converTextFieldValueToInt(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException ex) {
            logger.error("couldn't convert text field value to int");
        }
        return null;
    }

    private void updateConfig(String radioBtnValue, int tileSize) {
        getConfig().setTile_size(tileSize);
        getConfig().setTile_shape(radioBtnValue);
        configHandler.write_to_file(AppConfig.USER_SETTINGS_PATH, getConfig());
    }

    public void saveSettings(ActionEvent e) {
        if (!inputIsValid(radioBtnValue, tileSize))
            return; // TODO implement showing some error Alert or something


        Integer tileSizeInt = converTextFieldValueToInt(tileSize);
        if (tileSizeInt == null)
            return; // this should never happen as tile size textField input should be checked properly

        updateConfig(radioBtnValue, tileSizeInt);
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
