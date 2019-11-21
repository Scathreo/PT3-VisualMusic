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
	private static int EPAISSEUR_RECTANGLE = 90;	
	
	/**
	 * Contient tout les ratios qui seront affich� (barres)
	 * Taille d�finit dans m�thode update
	 */
	private double[] ratioFrequence = new double[
                             (TAILLE_FENETRE_X - 200) / EPAISSEUR_RECTANGLE
	                                             ];

	/**
	 * D�finition de la m�thode paint
	 * Affiche des formes g�om�triques (2D, 3D)
	 * en fonction de la musique
	 */
	public void paint(Graphics g) { 

		g.clearRect(0, 0, TAILLE_FENETRE_X, TAILLE_FENETRE_Y);
		
		// affiche une ligne au centre de la fen�tre
		g.drawLine(100, 
				   MILIEU_FENETRE_Y, 
				   TAILLE_FENETRE_X - 100, 
				   MILIEU_FENETRE_Y);
		
		// TODO Ne fonctionne pas
		//D�calement vers gauche
		int j = 0;
		for (int i = MILIEU_FENETRE_X-EPAISSEUR_RECTANGLE*3; 
				 i < MILIEU_FENETRE_X+EPAISSEUR_RECTANGLE*3; 
				 i += EPAISSEUR_RECTANGLE) {
			if (ratioFrequence[j] != 0)
				g.drawRect(i,
					   (int) (MILIEU_FENETRE_Y-ratioFrequence[j]*MILIEU_FENETRE_Y),
					   EPAISSEUR_RECTANGLE,
					   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y));
			j++;
		}
		
		
		//TODO a compl�ter
	}

	public void update(Observable m, Object obj) {
		/* TODO Ne fonctionne pas */
		Model model = (Model) m;
		
		for (int index = 0; index < ratioFrequence.length; index ++) {
			try {
				//if (ratioFrequence[index] != 0)
					ratioFrequence[index] = ratioFrequence[index + 1];
			}
			catch (IndexOutOfBoundsException e) {
				ratioFrequence[index] = model.getRatioFrequence();
			}
		}
		repaint();
		/*for (int i = 0; i < bitDeLecture.length; i ++) {
			int bit = model.getMusique().getOneBytes();
			bitDeLecture[i] = bit < 0 ? -bit: bit; //nb de rectangle max sur l'IG
			repaint();
		}*/
	}
	
}
