package org.example.generator.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileImageView extends ImageView {
    private Tile tileObj;
    public TileImageView(Image img, Tile t) {
        super(img);
        tileObj = t;
    }

    public Tile getTileObj() { return tileObj; }
    public void setTileObj(Tile tileObj) { this.tileObj = tileObj; }
}
