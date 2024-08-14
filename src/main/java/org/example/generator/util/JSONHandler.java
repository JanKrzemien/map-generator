package org.example.generator.util;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.config.AppConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONHandler<T extends JsonDeserializer<T> & JsonSerializer<T>> {
    private final Logger logger;
    private final Gson gson;
    Class<T> serializedObjectsClass;

    public Gson getGson() {return gson;}

    public JSONHandler(Class<T> serializedObjectsClass) {
        this.serializedObjectsClass = serializedObjectsClass;
        logger = LogManager.getLogger(serializedObjectsClass);

        GsonBuilder gb = null;

        try {
            gb = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(serializedObjectsClass, serializedObjectsClass.getConstructor().newInstance());
        } catch (Exception e) {
            logger.error("wyjebało się podczas rejestrowania adaptera typów dla klasy {}.\n", serializedObjectsClass.getName(), e);
            System.exit(-1);
        }

        if (gb == null) {
            System.exit(-1);
        }

        gson = gb.create();
    }

    /***
     * loads configuration from file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public T load_from_file(String path) {
        try (FileReader reader = new FileReader(path)) {
            T deserializedObject = gson.fromJson(reader, serializedObjectsClass);

            if (deserializedObject != null) {
                logger.info("successfully deserialized object from {} file.\n", path);
                return deserializedObject;
            }

            logger.warn("deserialized object from {} file is null.\n", path);
        } catch (IOException e) {
            logger.error("IOException while reading serialized object.\n", e);
        } catch (JsonParseException e) {
            logger.error("JsonParseException while reading serialized object.\n", e);
        }

        return null;
    }

    /***
     * writes configuration to file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public void write_to_file(String path, T serializedObject) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(gson.toJson(serializedObject));

            logger.info("serialized object to {} file.\n", path);
        } catch (IOException e) {
            logger.error("IOException while writing app config.\n", e);
        }
    }
}
