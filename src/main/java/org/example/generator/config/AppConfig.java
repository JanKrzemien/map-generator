package org.example.generator.config;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.generator.tiles.Tile;
import java.util.ArrayList;

public class AppConfig {
    private SimpleListProperty<Tile> tiles;

    private SimpleIntegerProperty tile_size;
    private SimpleStringProperty tile_shape;

    public AppConfig() {
        tile_size = new SimpleIntegerProperty(-1);
        tile_shape = new SimpleStringProperty("");
        tiles = new SimpleListProperty<>();
    }

    public void setTile_size(int size) {tile_size.set(size);}
    public int getTile_size() {return tile_size.getValue();}
    public SimpleIntegerProperty getTile_sizeObj() {return tile_size;}
    public void setTile_shape(String shape) {tile_shape.set(shape);}
    public String getTile_shape() { return tile_shape.getValue();}
    public SimpleStringProperty getTile_shapeObj() { return tile_shape; }

    public ArrayList<Tile> getTiles() { return new ArrayList<>(this.tiles); }
    public SimpleListProperty<Tile> getTilesObservableObj() {return tiles;}
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles.clear(); // not efficient as fuck, but currently I don't know how to do It better with SimpleListProperty
        ObservableList<Tile> observableList = FXCollections.observableArrayList(tiles);
        this.tiles.setValue(observableList);
    }

    @Override
    public String toString() {
        return "tile_size: " + tile_size + ",\ntile_shape: " + tile_shape;
    }
}
