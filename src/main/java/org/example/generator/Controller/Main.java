package org.example.generator.Controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javafx.fxml.FXML;
import org.example.generator.config.AppConfig;

public class Main extends Controller {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public Main(AppConfig cfg) {
        super(cfg);
    }

    @FXML
    public void initialize() {
        logger.info("Main controller initialize method.");
    }


}
