package fr.cactuscata.game.main.level.tile;

import org.lwjgl.opengl.Display;

import fr.cactuscata.game.main.Game;
import fr.cactuscata.lwjgl.main.utils.graphics.Renderer;
import fr.cactuscata.lwjgl.main.utils.graphics.Texture;

public class Tile {

	private static final int size = 16;
	
	private final int x, y;
	private final TileType tileType;
	
	
	public Tile(TileType tileType, int x, int y) {
		this.x = x;
		this.y = y;
		this.tileType = tileType;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public TileType getTileType() {
		return this.tileType;
	}
	
	public void render() {
// Not functionnal
//		float x0 = this.getX() - 1 + Game.getXScroll() / size;
//		float y0 = this.getY() - 1 + Game.getYScroll() / size;
//	
//		if(x0 < 1 || y0 < 1 || x0 > Display.getWidth() / 3 / size || y0 > Display.getHeight() / 3 / size) {return;}
		//Texture.getSpritShitTexture().bind();
		Renderer.renderQuad(x, y, size, size, this.tileType.getRGBA());
		//Texture.getSpritShitTexture().unbind();
	}
	
}
