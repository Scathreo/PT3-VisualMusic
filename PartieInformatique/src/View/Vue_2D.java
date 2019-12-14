package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Model.Model;

/**
 * Classe repr�sentant le visualisateur de l'IG
 * Affiche des formes g�om�trique 2D en fonction de la musique �cout�
 * 
 * @author Goodwin
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
	private static int NOMBRE_RECTANGLE = (
			(TAILLE_FENETRE_X - 2 * MARGIN_FORME_FENETRE)
			- 2 * EPAISSEUR_RECTANGLE)
			/ (EPAISSEUR_RECTANGLE);

	/**
	 * Contient tout les ratios qui seront affich� (barres)
	 * Taille d�finit dans m�thode update
	 */
	private double[] ratioFrequence = new double[Vue_2D.NOMBRE_RECTANGLE];

	/**
	 * Un tableau contenant un nombre de couleur egal
	 * au nombre de rectangle
	 */
	private Color[] couleurs_trait;
	private Color[] couleurs_forme;
	private Color couleur_trait;
	private Color couleur_forme;

	/**
	 * L'espacement entre chaque rectangle, en pixel
	 */
	private static int ESPACEMENT = 0;

	/**
	 * D�finition de la m�thode paint
	 * Affiche des formes g�om�triques (2D, 3D)
	 * en fonction de la musique
	 */
	public void paint(Graphics g) { 

		//Nettoie la fen�tre / l'affichage pr�cedent
		g.clearRect(0, 0, TAILLE_FENETRE_X, TAILLE_FENETRE_Y);

		//Affiche une ligne epaisse au centre de la fen�tre
		g.fillRect(MARGIN_FORME_FENETRE, 
				MILIEU_FENETRE_Y - EPAISSEUR_LIGNE/2, 
				TAILLE_FENETRE_X - 2*MARGIN_FORME_FENETRE, 
				EPAISSEUR_LIGNE);

		//g.setColor(Color.cyan);

		if ((NOMBRE_RECTANGLE % 2) == 0) {

			paintPaire(g);

		}

		else {

			paintImpaire(g);

		}

	}

	/**
	 * Centre l'affichage des rectangle si le nombre de rectangle
	 * � affich� est un nombre paire
	 * @param g : de type Graphics, sert � trac� les rectangles
	 */
	private void paintPaire(Graphics g) {

		int j;
		j = 0;

		for (int i = TAILLE_FENETRE_X / 2 - (EPAISSEUR_RECTANGLE * NOMBRE_RECTANGLE / 2);
				j < NOMBRE_RECTANGLE;
				i += EPAISSEUR_RECTANGLE + ESPACEMENT) {

			paintRectCouleur(g, i, j);
			j ++;

		}

	}

	/**
	 * Centre l'affichage des rectangle si le nombre de rectangle
	 * � affich� est un nombre impaire
	 * @param g : de type Graphics, sert � trac� les rectangles
	 */
	private void paintImpaire(Graphics g) {

		int j;
		j = 0;

		for (int i = TAILLE_FENETRE_X / 2 - (EPAISSEUR_RECTANGLE * NOMBRE_RECTANGLE / 2);
				j < NOMBRE_RECTANGLE;
				i += EPAISSEUR_RECTANGLE + ESPACEMENT) {

			paintRectCouleur(g, i, j);
			j ++;

		}

	}

	/**
	 * Trace un rectangle avec les coordonn�s fournis
	 * et lui fournis un couleur sp�cifique
	 * @param g : de type Graphics, sert � trac� les rectangles
	 * @param x : l'absisce o� commenc� � tracer le rectangle
	 * @param numero_rectangle : Quel rectangle doit �tre affich�
	 */
	private void paintRectCouleur(Graphics g, int x, int numero_rectangle) {
		// On trace le rectangle
		// la couleur correspond au dedans du rectangle

		g.setColor(new Color(
				(float) Math.random(),
				(float) Math.random(),
				(float) Math.random()));

		if (ratioFrequence[numero_rectangle] != 0) {

			if (couleur_forme == null && couleur_trait == null) {

				g.setColor(couleurs_forme[numero_rectangle]);
				g.fillRect(x,
						(int) (MILIEU_FENETRE_Y-ratioFrequence[numero_rectangle]*MILIEU_FENETRE_Y),
						EPAISSEUR_RECTANGLE,
						(int) (ratioFrequence[numero_rectangle]*TAILLE_FENETRE_Y));

				g.setColor(couleurs_trait[numero_rectangle]);
				g.drawRect(x,
						(int) (MILIEU_FENETRE_Y-ratioFrequence[numero_rectangle]*MILIEU_FENETRE_Y),
						EPAISSEUR_RECTANGLE,
						(int) (ratioFrequence[numero_rectangle]*TAILLE_FENETRE_Y));

			}

			else if (couleurs_forme == null && couleurs_trait ==null) {



				g.setColor(couleur_forme);
				g.fillRect(x,
						(int) (MILIEU_FENETRE_Y-ratioFrequence[numero_rectangle]*MILIEU_FENETRE_Y),
						EPAISSEUR_RECTANGLE,
						(int) (ratioFrequence[numero_rectangle]*TAILLE_FENETRE_Y));

				g.setColor(couleur_trait);
				g.drawRect(x,
						(int) (MILIEU_FENETRE_Y-ratioFrequence[numero_rectangle]*MILIEU_FENETRE_Y),
						EPAISSEUR_RECTANGLE,
						(int) (ratioFrequence[numero_rectangle]*TAILLE_FENETRE_Y));

			}

		}

	}

	/**
	 * Met � jour la vue
	 * Importe la frequence du model et la stock dans le 
	 * tableau ratioFrequence tout en d�calant chaque
	 * �l�ment vers la gauche
	 */
	public void update(Observable m, Object obj) {

		Model model = (Model) m;

		if (model.getErreur() == null) {

			if (model.isThreeDimension())
				ratioFrequence = new double[Vue_2D.NOMBRE_RECTANGLE];

			else {
				EPAISSEUR_RECTANGLE = model.getParametres().get("Epaisseur");
				ESPACEMENT = model.getParametres().get("Espacement");

				NOMBRE_RECTANGLE = (
						(TAILLE_FENETRE_X - 2 * MARGIN_FORME_FENETRE)
						- 2 * EPAISSEUR_RECTANGLE)
						/ (EPAISSEUR_RECTANGLE);

				if (model.getParametres().get("Couleur_2d_random") == 1) {

					couleur_forme = null;
					couleur_trait = null;

					couleurs_forme = new Color[NOMBRE_RECTANGLE];

					for (int index = 0; index < couleurs_forme.length; index ++) {

						couleurs_forme[index] = new Color(
								(float) Math.random(),
								(float) Math.random(),
								(float) Math.random()); 
					}

					couleurs_trait = new Color[NOMBRE_RECTANGLE];

					for (int index = 0; index < couleurs_trait.length; index ++) {

						couleurs_trait[index] = new Color(
								(float) Math.random(),
								(float) Math.random(),
								(float) Math.random()); 

					}
				}

				else {

					couleurs_trait = null;
					couleurs_forme = null;

					couleur_forme = new Color( 
							model.getParametres().get("Couleur_2d_forme_red"),
							model.getParametres().get("Couleur_2d_forme_green"),
							model.getParametres().get("Couleur_2d_forme_blue"));

					couleur_trait = new Color( 
							model.getParametres().get("Couleur_2d_trait_red"),
							model.getParametres().get("Couleur_2d_trait_green"),
							model.getParametres().get("Couleur_2d_trait_blue"));

				}
			}
		}

		double[] sauvegarde_rationFrequence = ratioFrequence;
		ratioFrequence = new double[NOMBRE_RECTANGLE];

		if (sauvegarde_rationFrequence != null) {

			for (int index = 0; index < NOMBRE_RECTANGLE; index ++) {

				ratioFrequence[index] = sauvegarde_rationFrequence[index];

			}
		}

		if (model.isFileLoaded() 
				&& model.getMusique().isLoad()
				&& !model.getMusique().isPause()) {

			for (int index = 0; index < NOMBRE_RECTANGLE; index ++) {

				try {

					ratioFrequence[index] = ratioFrequence[index + 1];

				}

				catch (IndexOutOfBoundsException e) {

					ratioFrequence[index] = model.getRatioFrequence();

				}
			}
		}

		repaint();

	}

}
