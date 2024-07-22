package org.example.generator.config;

import org.example.generator.tiles.Tile;
import java.util.ArrayList;

public class AppConfig {
    private ArrayList<Tile> tiles;

    private int tile_size;
    private String tile_shape;

    public void setTile_size(int size) {tile_size = size;}
    public int getTile_size() {return tile_size;}
    public void setTile_shape(String shape) {tile_shape = shape;}
    public String getTile_shape() { return tile_shape;}

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        return "tile_size: " + tile_size + ",\ntile_shape: " + tile_shape;
    }
}
