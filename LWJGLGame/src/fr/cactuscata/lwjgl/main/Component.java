package fr.cactuscata.lwjgl.main;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import fr.cactuscata.game.main.Game;

public class Component {
	
	/**
	 * Va permettre la boucle dans la méthode {@link Component#loop()}
	 */
	private boolean running = false;
	
	//Vu que ça va être un jeu en 2D qui va être pixelisé, on a pas besoin d'autant de pixel on va diviser la taille de l'écran par le scale
	private final int scale = 3;
	
	//Utilisé pour le déplacement du cube dans la logique du jeu
	//private int time = 0;
	
	//Permet a l'algorithme de fps de fonctionner, ces variables permettent de laisser ou non le droit d'update ou de render
	private boolean canUpdate = false, canRender = false;
	
	private final Game game = new Game();
		

	public Component() {
		try {
			//Définition de la taille de l'écran (a été divisé par trois ensuite (voir documentation <width> et <height>) pour ensuite être re-multiplié par trois ensuite)
			final int width = 720 / this.scale, height = 480 / this.scale;
			//Définis les dimensions de la fenêtre
			Display.setDisplayMode(new DisplayMode(width * scale, height * scale));
			//L'utilisateur peut modifier la taille de l'écran
			Display.setResizable(true);
			//Met, ou non, la fenêtre en plein écran dès le lancement
			Display.setFullscreen(false);
			//Met un titre à la fenêtre
			Display.setTitle("LWJGLGame by CactusCata");
			//Créer la fenêtre
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
		//Met à jour les entitées (?)
		glLoadIdentity();
		//Permet de décider si on veur de la 2D ou de la 3D
		//On donne la largeur à l'écran
		GLU.gluOrtho2D(0, width, height, 0);
		//Retour àla vu d'origine
		glMatrixMode(GL_MODELVIEW);
		//Met à jour les entitées (?)
		glLoadIdentity();
		
		
		glEnable(GL_TEXTURE_2D);
		
	}
	
	public void start() {
		//Permet d'initialiser la variable running afin qu'elle puisse suivre la boucle while dans la méthode loop.
		this.running = true;
		this.game.init();
		this.loop();
	}
	
	private void stop() {
		this.running = false;
	}
	
	private void exit() {
		//Détruit la fenêtre
		Display.destroy();
		//Ferme le programme java
		System.exit(0);
	}
	
	private void loop() {
		
		//variable "cache" qui permettra d'activer la visibilité des ticks et fps sur une seconde
		long timer = System.currentTimeMillis();
		//Compteur de ticks sur une seconde
		int tickCount = 0;
		//Compteur de fps sur une seconde
		int frameCount = 0;
		
		long beforeNanoTime = System.nanoTime();
		double elapsed = 0;
		double nanoSeconds = Math.pow(10, 9) / 60;
		
		while (this.running) {
			//Si l'utilisateur appuie sur la croix de la fenêtre, on stop (-> running == false) pour donc ensuite executer la méthode exit();
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
			
			//Condition utilisée pour permetre a l'algorithme de fps de fonctionner
			//on fait tous les calculs liés à la logique du jeu
			if(this.canUpdate) this.update();
			//Condition utilisée pour permetre a l'algorithme de fps de fonctionner
			//On rend le résultat des calculs liés à la logique du jeu
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
	 * Pour le render d'un carré
	 * 
	 * 		(1)			(2)
	 * 
	 * 
	 * 		(4)			(3)
	 * 
	 */
	
	/**
	 * Fait tous les calculs liés à la logique meme du jeu
	 */
	private void update() {
		this.game.update();
		//this.time++;
		
	}
	
	/**
	 * Rend le résultat des calculs liés à la logique du jeu à l'écran
	 */
	private void render() {
		
//		//Pour que la redimension soit clean
//		this.view2D(Display.getWidth() / this.scale, Display.getHeight() / this.scale);
//		//Permet de suprimer les résidus de l'ancien dessin (Met du noir partout)
//		glClear(GL_COLOR_BUFFER_BIT);
//		//Supprime aussi les résidus mais remplace le noir par la couleur
//		glClearColor(0.8f, 0.9f, 1.0f, 1.0f);
//		
//		float x = 16.0f + this.time / 4, y = 16.0f + this.time / 4, size = 16.0f;
//		glBegin(GL_QUADS);
//			glColor3f(0.5f, 0.5f, 0.5f);
//			//Point (1)
//			//Pour mettre de la couleur sur un point il suffit d'utiliser cette méthode juste avant de déclarer le point dans OpenGL
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
//			//Pour mettre de la couleur sur un point il suffit d'utiliser cette méthode juste avant de déclarer le point dans OpenGL
//			glColor3f(0.4f, 0.9f, 1.0f);
//			glVertex2f(x, y + size);
//			
//		glEnd();
//		
//		Renderer.renderQuad(x+30, y+30, size + 5, size + 5, new float[] {0.5f, 0.6f, 0.0f, 1.0f});
		
		this.game.render();
	}
	
}
