package fr.cactuscata.game.main.level;

import fr.cactuscata.game.main.level.tile.Tile;
import fr.cactuscata.game.main.level.tile.TileType;

public class Level {

	private final int width, height;
	private Tile[][] tiles;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[this.width][this.height];
		this.generate();
	}

	public void generate() {
		for(int x = 0; x < this.tiles.length; x++) {
			for(int y = 0; y < this.tiles[x].length; y++) {
				this.tiles[x][y] = new Tile(TileType.getRandomTile(), x, y);
			}
		}
	}

	public void init() {
		
	}

	public void update() {

	}

	public void render() {
		for(Tile[] yTiles : this.tiles) {
			for(Tile tiles : yTiles) {
				tiles.render();
			}
		}
//		Renderer.renderQuad(62, 0, 16, 16, new float[] { 1.0f, 1.0f, 0.0f, 1.0f });
//		for (int x = 0; x < Display.getWidth() / 16; x++) {
//			for (int y = 0; y < Display.getHeight() / 16; y++) {
//				Renderer.renderQuad(x * 17, y * 17, 16, 16, new float[] { 1.0f, 0.0f, 1.0f, 1.0f });
//			}
//		}

	}

}
