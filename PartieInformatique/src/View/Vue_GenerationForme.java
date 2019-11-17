package View;

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
 * Affiche des formes g�om�trique (2D, 3D) en fonction de la musique �cout�
 */
public class Vue_GenerationForme extends JPanel implements Observer {

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
	 * D�finition de la m�thode paint
	 * Affiche des formes g�om�triques (2D, 3D)
	 * en fonction de la musique
	 */
	public void paint(Graphics g) { 
		
		// affiche une ligne au centre de la fen�tre
		g.drawLine(100, 
				   MILIEU_FENETRE_Y, 
				   TAILLE_FENETRE_X - 100, 
				   MILIEU_FENETRE_Y);
		
		//sert � rien
		g.drawRect(MILIEU_FENETRE_X, MILIEU_FENETRE_Y-200, 30, 200);
		
		//TODO a compl�ter
	}

	public void update(Observable m, Object obj) {
		// TODO Auto-generated method stub
		
		Model model = (Model) m;
		
	}
	
}
