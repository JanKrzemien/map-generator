package org.example.generator.tiles;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.util.JSONHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArrayOfTiles implements JsonDeserializer<ArrayOfTiles>, JsonSerializer<ArrayOfTiles> {
    private static final Logger logger = LogManager.getLogger(ArrayOfTiles.class);
    public static final String TILES_FILE = "./tiles.json";
    JSONHandler<Tile> tileJSONHandler;

    private ArrayList<Tile> tileArray;
    public ArrayList<Tile> getTiles() {return tileArray;}
    public void setTiles(ArrayList<Tile> t) {tileArray = t;}

    public ArrayOfTiles() {
        tileJSONHandler = new JSONHandler<>(Tile.class);
        tileArray = new ArrayList<>();
    }
    public ArrayOfTiles(ArrayList<Tile> tiles) {
        tileJSONHandler = new JSONHandler<>(Tile.class);
        tileArray = tiles;
    }

    @Override
    public JsonElement serialize(ArrayOfTiles tiles, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray tileArr = new JsonArray();
        Gson gson = tileJSONHandler.getGson();

        for (Tile t : tiles.getTiles()) {
            tileArr.add(JsonParser.parseString(gson.toJson(t)));
        }

        logger.debug(tileArr);
        return tileArr;
    }

    @Override
    public ArrayOfTiles deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray tilesArr = JsonParser.parseString(String.valueOf(jsonElement)).getAsJsonArray();

        logger.debug(tilesArr);

        Gson gson = tileJSONHandler.getGson();

        ArrayList<Tile> tiles = new ArrayList<>();
        try {
            for (int i = 0; i < tilesArr.size(); i++) {
                tiles.add(gson.fromJson(tilesArr.get(i), Tile.class));
            }
        } catch (JsonSyntaxException e) {
            logger.error("error while parsing json\n", e);
        } catch (Exception e) {
            logger.error("unknown exception\n", e);
        }

        setTiles(tiles);
        return this;
    }
}
