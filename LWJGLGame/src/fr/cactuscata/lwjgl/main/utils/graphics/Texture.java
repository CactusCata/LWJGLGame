package fr.cactuscata.lwjgl.main.utils.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Texture {

	private static Texture spritShitTexture = loadTexture("/res/textures/blocks.png");

	private final int width, height, id;

	public Texture(int width, int height, int id) {
		this.width = width;
		this.height = height;
		this.id = id;
	}

	public static Texture loadTexture(String path) {
		
		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new File(new File(Texture.class.getProtectionDomain().getCodeSource().getLocation().getPath())
							.getParentFile().getAbsolutePath() + path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int w = image.getWidth();
		int h = image.getHeight();
		
		System.out.printf("w: %d, h: %d%n", w, h);

		// La taille des textures est toujours de 16 * 16
		int[] pixels = new int[w * h];
		image.getRGB(0, 0, w, h, pixels, 0, w);

		ByteBuffer buffer = BufferUtils.createByteBuffer(w * h * 4);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int i = pixels[x + y * w];
				buffer.put((byte) ((i >> 16) & 0xFF));
				buffer.put((byte) ((i >> 8) & 0xFF));
				buffer.put((byte) ((i) & 0xFF));
				buffer.put((byte) ((i >> 24) & 0xFF));
			}
		}

		buffer.flip();

		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		return new Texture(w, h, id);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, this.id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public static Texture getSpritShitTexture() {
		return spritShitTexture;
	}

}
