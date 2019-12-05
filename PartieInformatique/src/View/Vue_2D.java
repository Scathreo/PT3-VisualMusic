package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Model;

/**
 * 
 * @author Goodwin
 * Classe repr�sentant le visualisateur de l'IG
 * Affiche des formes g�om�trique 2D en fonction de la musique �cout�
 */
public class Vue_2D extends JPanel implements Observer {

	/**
	 * Taille Maximale de la fen�tre de l'application
	 * sur l'axe des abscisses (horizontal)
	 */
	private static int TAILLE_FENETRE_X = 800;
	
	/**
	 * Taille Maximale de la fen�tre de l'application
	 * sur l'axe des ordonn�es (vertical)
	 */
	private static int TAILLE_FENETRE_Y = 450;

	/**
	 * Coordonn�e sur l'axe des abscisses (horizontal)
	 * du milieu de la @TAILLE_FENETRE_X
	 */
	private static int MILIEU_FENETRE_X = TAILLE_FENETRE_X / 2;

	/**
	 * Coordonn�e sur l'axe des ordonn�es (vertical)
	 * du milieu de la @TAILLE_FENETRE_Y
	 */
	private static int MILIEU_FENETRE_Y = TAILLE_FENETRE_Y / 2;

	/**
	 * Epaisseur de chaqun des triangles
	 */
	private static int EPAISSEUR_RECTANGLE = 60;	

	/**
	 * Distance en pixel entre les forme 
	 * et les bords droits et gauche
	 */
	private static int MARGIN_FORME_FENETRE = 100;	

	/**
	 * Epaisseur de la ligne centrale
	 */
	private static int EPAISSEUR_LIGNE = 2;	

	/**
	 * Nombre de rectangle � afficher
	 */
	private static int NOMBRE_RECTANGLE = 
            (
            		(TAILLE_FENETRE_X - 2*MARGIN_FORME_FENETRE)
            		- 2 * EPAISSEUR_RECTANGLE
            )
            / EPAISSEUR_RECTANGLE;	
	
	/**
	 * Contient tout les ratios qui seront affich� (barres)
	 * Taille d�finit dans m�thode update
	 */
	private double[] ratioFrequence = new double[
                             (TAILLE_FENETRE_X - 200) / EPAISSEUR_RECTANGLE
	                                             ];

	/**
	 * Un tableau contenant un nombre de couleur egal
	 * au nombre de rectangle
	 */
	private Color[] couleurs = new Color[NOMBRE_RECTANGLE];
	
	/**
	 * Constructeur utilisant celui du p�re
	 * JPanel
	 * Donne des couleurs al�atoires
	 */
	public Vue_2D() {
		super();
		System.out.println(couleurs.length);
		for (int index_dans_tableau = 0;
			 index_dans_tableau < NOMBRE_RECTANGLE;
			 index_dans_tableau ++) {
			couleurs[index_dans_tableau] = new Color(
												(float) Math.random(),
												(float) Math.random(),
												(float) Math.random());
		}
	}
	
	/**
	 * D�finition de la m�thode paint
	 * Affiche des formes g�om�triques (2D, 3D)
	 * en fonction de la musique
	 */
	public void paint(Graphics g) { 

		//Nettoie la fen�tre / l'affichage pr�cedent
		g.clearRect(0, 0, TAILLE_FENETRE_X, TAILLE_FENETRE_Y);

		// affiche une ligne au centre de la fen�tre
		/*g.drawLine(100, 
				   MILIEU_FENETRE_Y, 
				   TAILLE_FENETRE_X - 100, 
				   MILIEU_FENETRE_Y);*/
		
		//Affiche une ligne epaisse au centre de la fen�tre
		g.fillRect(MARGIN_FORME_FENETRE, 
				   MILIEU_FENETRE_Y - EPAISSEUR_LIGNE/2, 
				   TAILLE_FENETRE_X - 2*MARGIN_FORME_FENETRE, 
				   EPAISSEUR_LIGNE);
		
		//g.setColor(Color.cyan);
		
		int j = 0;
		for (int i = MARGIN_FORME_FENETRE + EPAISSEUR_RECTANGLE; //MILIEU_FENETRE_X-EPAISSEUR_RECTANGLE*3; 
				 j < NOMBRE_RECTANGLE; //MILIEU_FENETRE_X+EPAISSEUR_RECTANGLE*3; 
				 i += EPAISSEUR_RECTANGLE) {
			
			// On trace le rectangle
			// la couleur correspond au dedans du rectangle
			g.setColor(couleurs[j]);
			//G�n�ration d'une couleur diff�rente � chaque fois
			couleurs[j] = new Color(
					(float) Math.random(),
					(float) Math.random(),
					(float) Math.random());
			
			if (ratioFrequence[j] != 0) {
				//TODO Avoir lequel vous pr�f�rer
				//choix 1
				/*
				 g.fillRect(i,
						   (int) (MILIEU_FENETRE_Y-ratioFrequence[j]*MILIEU_FENETRE_Y),
						   EPAISSEUR_RECTANGLE,
						   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y));
						   */
				
				//choix 2
				/*
				 g.fillRect(i,
						   (int) (MILIEU_FENETRE_Y-ratioFrequence[j]*MILIEU_FENETRE_Y),
						   EPAISSEUR_RECTANGLE,
						   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y));
				 g.fillRect(i,
						   MILIEU_FENETRE_Y,
						   EPAISSEUR_RECTANGLE,
						   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y));
						   */
				
				//choix 3
				g.fillRect(i,
						   (int) (MILIEU_FENETRE_Y-ratioFrequence[j]*MILIEU_FENETRE_Y),
						   EPAISSEUR_RECTANGLE,
						   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y*2));
			}
			
			// on trace le contour
			/* TODO INUTILE
			g.setColor(Color.black);
			g.drawRect(i,
					   (int) (MILIEU_FENETRE_Y-ratioFrequence[j]*MILIEU_FENETRE_Y),
					   EPAISSEUR_RECTANGLE,
					   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y));
		   */
			j++;
		}
		
		
		//TODO a compl�ter
	}
	/*
	 * on met � jour le model s'il n'y a aucune erreur
	 */
	public void update(Observable m, Object obj) {
		Model model = (Model) m;
		
		if (model.getErreur() == null) {
			for (int index = 0; index < ratioFrequence.length; index ++) {
				try {
					ratioFrequence[index] = ratioFrequence[index + 1];
				}
				catch (IndexOutOfBoundsException e) {
					ratioFrequence[index] = model.getRatioFrequence();
				}
			}
			repaint();
		}
	}
	
}
