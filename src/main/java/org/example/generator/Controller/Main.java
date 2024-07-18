package org.example.generator.Controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javafx.fxml.FXML;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    @FXML
    public void initialize() {
        logger.info("Main controller initialize method.");
    }
}
