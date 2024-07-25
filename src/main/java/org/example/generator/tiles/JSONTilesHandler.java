package org.example.generator.tiles;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONTilesHandler implements JsonDeserializer<Tile[]>, JsonSerializer<Tile[]> {
    private static final Logger logger = LogManager.getLogger(JSONTilesHandler.class);

    public static final String TILES_FILE = "./tiles.json";

    private final Gson gson;

    public JSONTilesHandler() {
        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Tile[].class, this)
                .registerTypeAdapter(Tile.class, new Tile(""))
                .create();
    }

    /***
     * loads tiles from file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public ArrayList<Tile> load_from_file(String path) {
        try (FileReader reader = new FileReader(path)) {
            Tile[] tileArr = gson.fromJson(reader, Tile[].class);

            if (tileArr != null) {
                ArrayList<Tile> tiles = new ArrayList<>(Arrays.asList(tileArr));
                logger.info("loaded tiles from {} file.\n{}", path, tiles);
                return tiles;
            }
            return new ArrayList<>();
        } catch (IOException e) {
            logger.error("IOException while reading tiles.\n", e);
        }

        return null;
    }

    /***
     * writes tiles to file specified as a param
     * @param path - String which is a path to a configuration file
     */
    public void write_to_file(String path, ArrayList<Tile> tiles) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(gson.toJson(tiles));

            logger.info("wrote tiles to {} file.", path);
        } catch (IOException e) {
            logger.error("IOException while writing app config.\n", e);
        }
    }

    @Override
    public JsonElement serialize(Tile[] tiles, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray tileArr = new JsonArray();
        for (Tile t : tiles) {
            tileArr.add(gson.toJson(t));
        }
        return tileArr;
    }

    @Override
    public Tile[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray tilesArr = (JsonArray) jsonElement;
        Tile[] tiles = new Tile[tilesArr.size()];
        try {
            for (int i = 0; i < tilesArr.size(); i++) {
                tiles[i] = gson.fromJson(tilesArr.get(i).toString(), Tile.class);
            }
        } catch (JsonSyntaxException e) {
            logger.error("error while parsing json\n", e);
        } catch (Exception e) {
            logger.error("unknown exception\n", e);
        }

        return tiles;
    }
}
