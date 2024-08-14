package org.example.generator.tiles;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.generator.util.JSONHandler;

import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    private static final Logger logger = LogManager.getLogger(TileManager.class);

    private final JSONHandler<ArrayOfTiles> tilesHandler;

    private final SimpleListProperty<Tile> tilesObservable; // TODO check if you can turn it into dict

    public TileManager() {
        tilesObservable = new SimpleListProperty<>();
        tilesHandler = new JSONHandler<>(ArrayOfTiles.class);
    }

    public ArrayList<Tile> getTiles() { return new ArrayList<>(this.tilesObservable); }
    public SimpleListProperty<Tile> getObservableTilesList() {return tilesObservable;}
    public void addTiles(ArrayList<Tile> tiles) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tilesObservable);

        for (Tile t : tiles) {
            if (checkIfTileNameIsUnique(t.getName()))
                oldTiles.add(t);
            else
                logger.warn("tile with image path {} doesn't have unique name so it's not added.", t.getPath());
        }
        this.tilesObservable.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tilesObservable.setValue(observableList);

        tilesHandler.write_to_file(ArrayOfTiles.TILES_FILE, new ArrayOfTiles(getTiles()));
    }
    public void removeTiles(ArrayList<Tile> tiles) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tilesObservable);
        for (Tile t : tiles)
            oldTiles.remove(t);
        this.tilesObservable.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tilesObservable.setValue(observableList);

        tilesHandler.write_to_file(ArrayOfTiles.TILES_FILE, new ArrayOfTiles(getTiles()));
    }

    public boolean checkIfTileNameIsUnique(String name) {
        for (Tile t : tilesObservable)
            if (Objects.equals(t.getName(), name))
                return false;

        return true;
    }
}
