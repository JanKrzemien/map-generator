package org.example.generator.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;

public class JSONConfigReader {
    private static final Logger logger = LogManager.getLogger(JSONConfigReader.class);

    /***
     * loads configuration from file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public static AppConfig load_from_file(String path) {
        Gson gson = new GsonBuilder().registerTypeAdapter(AppConfig.class, new AppConfigJSONDeserializer()).create();

        try (FileReader reader = new FileReader(path)) {
            AppConfig config = gson.fromJson(reader, AppConfig.class);

            if (config != null)
                logger.info("loaded app configuration from {} file.\n Loaded data: \n{}", path, config.toString());

            return config;
        } catch (IOException e) {
            logger.error("IOException while reading app config.\n", e);
        }

        return null;
    }
}
