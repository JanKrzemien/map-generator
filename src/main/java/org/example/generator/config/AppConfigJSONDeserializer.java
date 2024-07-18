package org.example.generator.config;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AppConfigJSONDeserializer implements JsonDeserializer<AppConfig> {
    @Override
    public AppConfig deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = JsonParser.parseString(String.valueOf(jsonElement)).getAsJsonObject();

        AppConfig c = new AppConfig();
        c.setTile_size(jsonObject.get("tile_size").getAsInt());
        c.setTile_shape(jsonObject.get("tile_shape").getAsString());
        return c;
    }
}
