package org.example.generator.tiles;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Tile implements JsonDeserializer<Tile>, JsonSerializer<Tile> {
    private static final Logger logger = LogManager.getLogger(Tile.class);

    private int id;
    private String path;
    private ArrayList<Ruleset> rulesets;

    public Tile(String path) {
        this.id = -1;
        this.path = path;
        this.rulesets = new ArrayList<>();
    }
    public Tile(int id, String path) {
        this.id = id;
        this.path = path;
        this.rulesets = new ArrayList<>();
    }
    public Tile(int id, String path, ArrayList<Ruleset> rulesets) {
        this.id = id;
        this.path = path;
        this.rulesets = rulesets;
    }

    public ArrayList<Ruleset> getRulesets() { return rulesets; }
    public int getId() { return id; }
    public String getPath() { return path; }

    public void setId(int id) { this.id = id; }
    public void setPath(String path) { this.path = path; }
    public void setRulesets(ArrayList<Ruleset> rulesets) { this.rulesets = rulesets; }

    @Override
    public JsonElement serialize(Tile tile, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", tile.getId());
        obj.addProperty("path", tile.getPath());

        JsonArray rulesetArr = new JsonArray();
        Gson gson = new GsonBuilder().registerTypeAdapter(Ruleset.class, new Ruleset()).setPrettyPrinting().create();
        for (Ruleset rs : getRulesets()) {
            String rsObj = gson.toJson(rs);
            rulesetArr.add(rsObj);
        }

        obj.add("rulesets", rulesetArr);
        return obj;
    }

    @Override
    public Tile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject tileJsonObj = JsonParser.parseString(String.valueOf(jsonElement)).getAsJsonObject();

        ArrayList<Ruleset> rs = new ArrayList<>();
        Gson gson = new GsonBuilder().registerTypeAdapter(Ruleset.class, new Ruleset()).create();
        for (JsonElement ruleset : tileJsonObj.getAsJsonArray("rulesets")) {
            rs.add(gson.fromJson(ruleset, Ruleset.class));
        }

        return new Tile(tileJsonObj.get("id").getAsInt(), tileJsonObj.get("path").getAsString(), rs);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "id=" + id +
                "path='" + path + '\'' +
                ", rulesets=" + rulesets +
                '}';
    }
}
