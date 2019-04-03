package fr.cactuscata.lwjgl.main;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import fr.cactuscata.game.main.Game;

public class Component {
	
	/**
	 * Va permettre la boucle dans la m�thode {@link Component#loop()}
	 */
	private boolean running = false;
	
	//Vu que �a va �tre un jeu en 2D qui va �tre pixelis�, on a pas besoin d'autant de pixel on va diviser la taille de l'�cran par le scale
	private final int scale = 3;
	
	//Utilis� pour le d�placement du cube dans la logique du jeu
	//private int time = 0;
	
	//Permet a l'algorithme de fps de fonctionner, ces variables permettent de laisser ou non le droit d'update ou de render
	private boolean canUpdate = false, canRender = false;
	
	private final Game game = new Game();
		

	public Component() {
		try {
			//D�finition de la taille de l'�cran (a �t� divis� par trois ensuite (voir documentation <width> et <height>) pour ensuite �tre re-multipli� par trois ensuite)
			final int width = 720 / this.scale, height = 480 / this.scale;
			//D�finis les dimensions de la fen�tre
			Display.setDisplayMode(new DisplayMode(width * scale, height * scale));
			//L'utilisateur peut modifier la taille de l'�cran
			Display.setResizable(true);
			//Met, ou non, la fen�tre en plein �cran d�s le lancement
			Display.setFullscreen(false);
			//Met un titre � la fen�tre
			Display.setTitle("LWJGLGame by CactusCata");
			//Cr�er la fen�tre
			Display.create();
			
			this.view2D(width, height);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	private void view2D(int width, int height) {
		
		
		glViewport(0, 0, width * this.scale, height * scale);
		//Pour initialiser OpenGL on commence par lui donner son mode de matrice
		glMatrixMode(GL_PROJECTION);
		//Met � jour les entit�es (?)
		glLoadIdentity();
		//Permet de d�cider si on veur de la 2D ou de la 3D
		//On donne la largeur � l'�cran
		GLU.gluOrtho2D(0, width, height, 0);
		//Retour �la vu d'origine
		glMatrixMode(GL_MODELVIEW);
		//Met � jour les entit�es (?)
		glLoadIdentity();
		
		
		glEnable(GL_TEXTURE_2D);
		
	}
	
	public void start() {
		//Permet d'initialiser la variable running afin qu'elle puisse suivre la boucle while dans la m�thode loop.
		this.running = true;
		this.game.init();
		this.loop();
	}
	
	private void stop() {
		this.running = false;
	}
	
	private void exit() {
		//D�truit la fen�tre
		Display.destroy();
		//Ferme le programme java
		System.exit(0);
	}
	
	private void loop() {
		
		//variable "cache" qui permettra d'activer la visibilit� des ticks et fps sur une seconde
		long timer = System.currentTimeMillis();
		//Compteur de ticks sur une seconde
		int tickCount = 0;
		//Compteur de fps sur une seconde
		int frameCount = 0;
		
		long beforeNanoTime = System.nanoTime();
		double elapsed = 0;
		double nanoSeconds = Math.pow(10, 9) / 60;
		
		while (this.running) {
			//Si l'utilisateur appuie sur la croix de la fen�tre, on stop (-> running == false) pour donc ensuite executer la m�thode exit();
			if(Display.isCloseRequested()) {this.stop();}
			Display.update();
			
			canUpdate = false;
			canUpdate = false;
			
			long now = System.nanoTime();
			elapsed = now - beforeNanoTime;
			
			if(elapsed > nanoSeconds) {
				//reset de beforeNanoTime pour pouvoir refaire cette boucle
				beforeNanoTime += nanoSeconds;
				this.canUpdate = true;
				tickCount++;
			} else {
				this.canRender = true;
				frameCount++;
			}
			
			//Condition utilis�e pour permetre a l'algorithme de fps de fonctionner
			//on fait tous les calculs li�s � la logique du jeu
			if(this.canUpdate) this.update();
			//Condition utilis�e pour permetre a l'algorithme de fps de fonctionner
			//On rend le r�sultat des calculs li�s � la logique du jeu
			if(this.canRender) this.render();
			
			//Va s'activer une fois toute les secondes
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.printf("Ticks par seconde : %d | Fps par seconde : %d%n", tickCount, frameCount);
				//Reset du compteur
				tickCount = 0;
				frameCount = 0;
			}
		}
		
		this.exit();
		
	}
	
	/*
	 * Pour le render d'un carr�
	 * 
	 * 		(1)			(2)
	 * 
	 * 
	 * 		(4)			(3)
	 * 
	 */
	
	/**
	 * Fait tous les calculs li�s � la logique meme du jeu
	 */
	private void update() {
		this.game.update();
		//this.time++;
		
	}
	
	/**
	 * Rend le r�sultat des calculs li�s � la logique du jeu � l'�cran
	 */
	private void render() {
		
//		//Pour que la redimension soit clean
//		this.view2D(Display.getWidth() / this.scale, Display.getHeight() / this.scale);
//		//Permet de suprimer les r�sidus de l'ancien dessin (Met du noir partout)
//		glClear(GL_COLOR_BUFFER_BIT);
//		//Supprime aussi les r�sidus mais remplace le noir par la couleur
//		glClearColor(0.8f, 0.9f, 1.0f, 1.0f);
//		
//		float x = 16.0f + this.time / 4, y = 16.0f + this.time / 4, size = 16.0f;
//		glBegin(GL_QUADS);
//			glColor3f(0.5f, 0.5f, 0.5f);
//			//Point (1)
//			//Pour mettre de la couleur sur un point il suffit d'utiliser cette m�thode juste avant de d�clarer le point dans OpenGL
//			glColor3f(0.2f, 0.1f, 0.0f);
//			glVertex2f(x, y);
//			
//			//Point (2)
//			glVertex2f(x + size, y);
//			
//			//Point (3)
//			glVertex2f(x + size, y + size);
//			
//			//Point (4)
//			//Pour mettre de la couleur sur un point il suffit d'utiliser cette m�thode juste avant de d�clarer le point dans OpenGL
//			glColor3f(0.4f, 0.9f, 1.0f);
//			glVertex2f(x, y + size);
//			
//		glEnd();
//		
//		Renderer.renderQuad(x+30, y+30, size + 5, size + 5, new float[] {0.5f, 0.6f, 0.0f, 1.0f});
		
		this.game.render();
	}
	
}
