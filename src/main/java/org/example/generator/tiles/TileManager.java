package org.example.generator.tiles;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.Controller.FileTab;

import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    private static final Logger logger = LogManager.getLogger(TileManager.class);

    private final JSONTilesHandler tilesHandler;

    private final SimpleListProperty<Tile> tiles;

    public TileManager() {
        tiles = new SimpleListProperty<>();
        tilesHandler = new JSONTilesHandler();
    }

    public ArrayList<Tile> getTiles() { return new ArrayList<>(this.tiles); }
    public SimpleListProperty<Tile> getObservableTilesList() {return tiles;}
    public void addTile(Tile t) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tiles);

        if (checkIfTileNameIsUnique(t.getName()))
            oldTiles.add(t);
        else
            logger.warn("tile with image path {} doesn't have unique name so it's not added.", t.getPath());

        this.tiles.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tiles.setValue(observableList);

        tilesHandler.write_to_file(JSONTilesHandler.TILES_FILE, getTiles());
    }
    public void addTiles(ArrayList<Tile> tiles) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tiles);

        for (Tile t : tiles) {
            if (checkIfTileNameIsUnique(t.getName()))
                oldTiles.add(t);
            else
                logger.warn("tile with image path {} doesn't have unique name so it's not added.", t.getPath());
        }
        this.tiles.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tiles.setValue(observableList);

        tilesHandler.write_to_file(JSONTilesHandler.TILES_FILE, getTiles());
    }
    public void removeTiles(ArrayList<Tile> tiles) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tiles);
        for (Tile t : tiles)
            oldTiles.remove(t);
        this.tiles.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tiles.setValue(observableList);
    }

    public boolean checkIfTileNameIsUnique(String name) {
        for (Tile t : tiles) {
            if (Objects.equals(t.getName(), name))
                return false;
        }
        return true;
    }
}
