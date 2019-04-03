package fr.cactuscata.game.main;

import org.lwjgl.opengl.GL11;

import fr.cactuscata.game.main.level.Level;

public class Game {

	private final Level level = new Level(150, 150);
	
	private static float xScroll, yScroll;

	public Game() {

	}

	public void init() {
		this.level.init();
	}

	private void addViewCoordonate(float xa, float ya) {
		Game.xScroll += xa;
		Game.yScroll += ya;
	}
	
	public static float getXScroll() {
		return Game.xScroll;
	}
	
	public static float getYScroll() {
		return Game.yScroll;
	}
	
	public void update() {
		//this.addViewCoordonate(-1, -1);
		this.level.init();
	}

	public void render() {
		//GL11.glTranslated(Game.xScroll, Game.yScroll, 0);
		this.level.render();
//		Renderer.renderQuad(62, 0, 16, 16, new float[] { 1.0f, 1.0f, 0.0f, 1.0f });
//		for (int x = 0; x < Display.getWidth() / 16; x++) {
//			for (int y = 0; y < Display.getHeight() / 16; y++) {
//				Renderer.renderQuad(x * 17, y * 17, 16, 16, new float[] { 1.0f, 0.0f, 1.0f, 1.0f });
//			}
//		}

	}

}
