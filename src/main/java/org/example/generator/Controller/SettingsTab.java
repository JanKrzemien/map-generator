package org.example.generator.Controller;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsTab {
    private static Logger logger = LogManager.getLogger(SettingsTab.class);

    @FXML
    public void initialize() {
        logger.debug("Settings controller initialize method.");
    }
}
