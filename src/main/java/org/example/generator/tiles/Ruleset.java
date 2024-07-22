package org.example.generator.tiles;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

public class Ruleset  implements JsonDeserializer<Ruleset>, JsonSerializer<Ruleset> {
    private static final Logger logger = LogManager.getLogger(Ruleset.class);


    @Override
    public JsonElement serialize(Ruleset ruleset, Type type, JsonSerializationContext jsonSerializationContext) {
        return JsonParser.parseString("{}");
    }
    @Override
    public Ruleset deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Ruleset();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
