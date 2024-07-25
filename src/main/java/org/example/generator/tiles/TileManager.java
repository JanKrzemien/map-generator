package org.example.generator.tiles;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class TileManager {
    private final SimpleListProperty<Tile> tiles;
    private int currentLastId;

    public TileManager() {
        tiles = new SimpleListProperty<>();
        currentLastId = 0;
    }

    public ArrayList<Tile> getTiles() { return new ArrayList<>(this.tiles); }
    public SimpleListProperty<Tile> getObservableTilesList() {return tiles;}
    public void addTiles(ArrayList<Tile> tiles) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tiles);
        for(Tile t : oldTiles)
            if (t.getId() > currentLastId)
                currentLastId = t.getId();
        for(Tile t : tiles)
            if (t.getId() > currentLastId)
                currentLastId = t.getId();

        for (Tile t : tiles) {
            if (t.getId() == -1) {
                t.setId( currentLastId );
                currentLastId++;
            }
            oldTiles.add(t);
        }
        this.tiles.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tiles.setValue(observableList);
    }
    public void removeTiles(ArrayList<Tile> tiles) {
        // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ArrayList<Tile> oldTiles = new ArrayList<>(this.tiles);
        for (Tile t : tiles)
            oldTiles.remove(t);
        //TODO should we change tile id's upon deletion to save some unique id's we can assign?
        this.tiles.clear();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(oldTiles);
        this.tiles.setValue(observableList);
    }
}
