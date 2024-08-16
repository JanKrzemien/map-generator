package org.example.generator.tiles;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.util.JSONHandler;
import java.util.ArrayList;
import java.util.HashMap;

public class TileManager {
    private static final Logger logger = LogManager.getLogger(TileManager.class);

    private final JSONHandler<ArrayOfTiles> tilesHandler;

    private final SimpleMapProperty<String, Tile> tileDict; // immutable

    public TileManager() {
        tilesHandler = new JSONHandler<>(ArrayOfTiles.class);
        tileDict = new SimpleMapProperty<>();
    }

    public ArrayList<Tile> getTiles() { return new ArrayList<>(this.tileDict.getValue().values()); }
    public SimpleMapProperty<String, Tile> getObservableTileDict() {return tileDict;}
    public void addTiles(ArrayList<Tile> tiles) {
        HashMap<String, Tile> tempDict = new HashMap<>();

        for (Tile t : tiles) {
            if (tileDict.containsKey(t.getName()))
                logger.warn("tile with image path {} doesn't have unique name so it's not added.", t.getPath());
            else
                tempDict.put(t.getName(), t);
        }

        ObservableMap<String, Tile> newDict = FXCollections.observableMap(tempDict);
        this.tileDict.setValue(newDict);

        tilesHandler.write_to_file(ArrayOfTiles.TILES_FILE, new ArrayOfTiles(getTiles()));
    }
    public void removeTiles(ArrayList<Tile> tiles) {
        HashMap<String, Tile> tempDict = new HashMap<>(getObservableTileDict().getValue());

        for (Tile t : tiles)
            if(!tempDict.remove(t.getName(), t))
                logger.warn("tile with image path {} couldn't be removed.", t.getPath());

        ObservableMap<String, Tile> newDict = FXCollections.observableMap(tempDict);
        this.tileDict.setValue(newDict);

        tilesHandler.write_to_file(ArrayOfTiles.TILES_FILE, new ArrayOfTiles(getTiles()));
    }

    public boolean checkIfTileNameIsUnique(String name) {
        return !tileDict.containsKey(name);
    }
}
