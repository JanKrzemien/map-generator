package org.example.generator.config;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONConfigHandler implements JsonDeserializer<AppConfig>, JsonSerializer<AppConfig> {
    private static final Logger logger = LogManager.getLogger(JSONConfigHandler.class);

    final public static String DEFAULT_SETTINGS_PATH = "./settings.default.json";
    final public static String USER_SETTINGS_PATH = "./settings.json";

    private final Gson gson;

    public JSONConfigHandler() {
        gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(AppConfig.class, this).create();
    }

    /***
     * loads configuration from file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public AppConfig load_from_file(String path) {
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

    /***
     * writes configuration to file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public void write_to_file(String path, AppConfig config) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(gson.toJson(config));

            logger.info("wrote app configuration to {} file.\n Written data: \n{}", path, config.toString());
        } catch (IOException e) {
            logger.error("IOException while writing app config.\n", e);
        }
    }

    @Override
    public AppConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = JsonParser.parseString(String.valueOf(jsonElement)).getAsJsonObject();

        AppConfig c = new AppConfig();
        c.setTile_size(jsonObject.get("tile_size").getAsInt());
        c.setTile_shape(jsonObject.get("tile_shape").getAsString());
        return c;
    }

    @Override
    public JsonElement serialize(AppConfig appConfig, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("tile_size", appConfig.getTile_size());
        obj.addProperty("tile_shape", appConfig.getTile_shape());
        return obj;
    }
}
