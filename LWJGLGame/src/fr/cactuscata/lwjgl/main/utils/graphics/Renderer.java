package fr.cactuscata.lwjgl.main.utils.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	private static void quadData(float x, float y, float width, float height, float[] color) {
		//Met les couleurs pour la globalité des points suivant entre le glBegin() et le glEnd()
		glColor4f(color[0], color[1], color[2], color[3]);
		//Définis les quatre points
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		
	}
	
	public static void renderQuad(float x, float y, float width, float height, float[] color) {
		
		glBegin(GL_QUADS);
			quadData(x, y, width, height, color);
		glEnd();
		
	}
	
	private static void quadDataTexture(float x, float y, float width, float height, float[] color, int xo, int yo) {
		//Met les couleurs pour la globalité des points suivant entre le glBegin() et le glEnd()
		glColor4f(color[0], color[1], color[2], color[3]);
		///Texture spritShitTexture = Texture.getSpritShitTexture();
		//Définis les quatre points
		glTexCoord2f((0 + xo) / 32.0f, (0 + yo) / 32.0f); glVertex2f(x, y);
		glTexCoord2f((0 + xo) / 32.0f, (1 + yo) / 32.0f); glVertex2f(x + width, y);
		glTexCoord2f((1 + xo) / 32.0f, (1 + yo) / 32.0f); glVertex2f(x + width, y + height);
		glTexCoord2f((1 + xo) / 32.0f, (0 + yo) / 32.0f); glVertex2f(x, y + height);
		
	}
	
	public static void renderQuadTexture(float x, float y, float width, float height, float[] color, int xo, int yo) {
		
		glBegin(GL_QUADS);
			quadDataTexture(x, y, width, height, color, xo, yo);
		glEnd();
		
	}
	
}
