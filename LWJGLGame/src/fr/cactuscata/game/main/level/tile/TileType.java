package fr.cactuscata.game.main.level.tile;

import java.awt.Color;
import java.util.Random;

public enum TileType {

	STONE(1, 0.5f, 0.5f, 0.5f, 1.0f),
	GRASS(2, 0.0f, 0.5f, 0.0f, 1.0f),
	WATER(3, 0.0f, 0.3f, 0.5f, 1.0f);

	private final float r, g, b, a;
	private final int id;

	private TileType(int id, float r, float g, float b, float a) {
		this.id = id;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	private TileType(int id, Color color) {
		this(id, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public float[] getRGBA() {
		return new float[] { this.r, this.g, this.b, this.a };
	}

	private static final Random random = new Random();

	public static TileType getRandomTile() {
		return TileType.values()[random.nextInt(TileType.values().length)];
	}

	public int getId() {
		return id;
	}

}
