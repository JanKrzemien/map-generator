package org.example.generator.config;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.generator.tiles.TileManager;

public class AppConfig {
    private TileManager tileManager;

    private SimpleIntegerProperty tile_size;
    private SimpleStringProperty tile_shape;

    public AppConfig() {
        tile_size = new SimpleIntegerProperty(-1);
        tile_shape = new SimpleStringProperty("");
        tileManager = new TileManager();
    }

    public void setTile_size(int size) {tile_size.set(size);}
    public int getTile_size() {return tile_size.getValue();}
    public SimpleIntegerProperty getTile_sizeObj() {return tile_size;}
    public void setTile_shape(String shape) {tile_shape.set(shape);}
    public String getTile_shape() { return tile_shape.getValue();}
    public SimpleStringProperty getTile_shapeObj() { return tile_shape; }

    public TileManager getTileManager() {return tileManager;}

    @Override
    public String toString() {
        return "tile_size: " + tile_size + ",\ntile_shape: " + tile_shape;
    }
}
