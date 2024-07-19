package org.example.generator.Controller;

import javafx.scene.Parent;
import org.example.generator.config.AppConfig;

public class Controller {
    private AppConfig config;

    public AppConfig getConfig() {
        return config;
    }
    public void setConfig(AppConfig cfg) {
        config = cfg;
    }

    public Controller(AppConfig cfg) {
        setConfig(cfg);
    }
}
