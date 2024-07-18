package org.example.generator.config;

public class AppConfig {
    private int tile_size;
    private String tile_shape;

    public void setTile_size(int size) {tile_size = size;}
    public int getTile_size() {return tile_size;}
    public void setTile_shape(String shape) {tile_shape = shape;}
    public String getTile_shape() { return tile_shape;}

    @Override
    public String toString() {
        return "tile_size: " + tile_size + ",\ntile_shape: " + tile_shape;
    }
}
