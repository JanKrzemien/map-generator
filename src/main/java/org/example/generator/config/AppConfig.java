package org.example.generator.config;

import com.google.gson.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.generator.tiles.TileManager;

import java.lang.reflect.Type;

public class AppConfig implements JsonDeserializer<AppConfig>, JsonSerializer<AppConfig> {
    final public static String DEFAULT_SETTINGS_PATH = "./settings.default.json";
    final public static String USER_SETTINGS_PATH = "./settings.json";

    private final TileManager tileManager;

    private final SimpleIntegerProperty tile_size;
    private final SimpleStringProperty tile_shape;

    public AppConfig() {
        tile_size = new SimpleIntegerProperty(-1);
        tile_shape = new SimpleStringProperty("");
        tileManager = new TileManager();
    }

    public void setTile_size(int size) {tile_size.set(size);}
    public int getTile_size() {return tile_size.getValue();}
    public SimpleIntegerProperty getTile_sizeObj() {return tile_size;}
    public void setTile_shape(String shape) {tile_shape.set(shape);}
    public String getTile_shape() { return tile_shape.getValue();}
    public SimpleStringProperty getTile_shapeObj() { return tile_shape; }

    public TileManager getTileManager() {return tileManager;}

    @Override
    public String toString() {
        return "tile_size: " + tile_size + ",\ntile_shape: " + tile_shape;
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
