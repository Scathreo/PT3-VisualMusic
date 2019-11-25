package View;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Model;

import java.awt.Dimension;

import java.awt.Point;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Goodwin
 * Classe repr�sentant le visualisateur de l'IG
 * Affiche des formes g�om�trique 3D en fonction de la musique �cout�
 */
public class Vue_3D extends JPanel implements Observer {

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
	private static int EPAISSEUR_RECTANGLE = 30;	

	/**
	 * D�finition de la m�thode paint
	 * Affiche des formes g�om�triques (2D, 3D)
	 * en fonction de la musique
	 */
	private double[] ratioFrequence = new double[
	                                             (TAILLE_FENETRE_X - 200) / EPAISSEUR_RECTANGLE
	                                             ];
	/*
	 * On cr�er deux points afin de tester l'affichage d'un cube
	 */
	private Point[] cubeOnePoints = {new Point(100,200) };
	private Point[] cubeTwoPoints = {new Point(200,300)};

	/**
	 * M�thode qui permet l'affichage des cubes en 3D
	 * en fonction du temps, de la f�quence 
	 */
	//TODO le tableau ratioFrequence toujours �gal � 0
    public void paint(Graphics g) {
    	g.clearRect(0, 0, TAILLE_FENETRE_X, TAILLE_FENETRE_Y);
    	/*g.drawLine(100, 
				MILIEU_FENETRE_Y, 
				TAILLE_FENETRE_X - 100, 
				MILIEU_FENETRE_Y);
				*/
    	
        g.drawRect(100, 100, 200 - 100, 100);
        // draw connecting lines
        int j=0;
        for (int i = MILIEU_FENETRE_X-EPAISSEUR_RECTANGLE*3; 
				 i < MILIEU_FENETRE_X+EPAISSEUR_RECTANGLE*3; 
				 i += EPAISSEUR_RECTANGLE) {
        	if(ratioFrequence[j] != 0)
        		g.drawRect(i,
					   (int) (MILIEU_FENETRE_Y-ratioFrequence[j]*MILIEU_FENETRE_Y),
					   EPAISSEUR_RECTANGLE,
					   (int) (ratioFrequence[j]*MILIEU_FENETRE_Y) );
        	j++;
        	System.out.println(ratioFrequence[j]);
        }
    }
    /*
     * M�thode qui permet de tracer un cube 
     */
    /*public void drawCube(Graphics g) {
        g.drawRect(MILIEU_FENETRE_X, MILIEU_FENETRE_Y, MILIEU_FENETRE_Y, MILIEU_FENETRE_Y);
        // draw connecting lines
        for (int i = 0; i < 4; i++) {
            g.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y, 
                    cubeTwoPoints[i].x, cubeTwoPoints[i].y);
        }
    }*/
    
    /*
     * M�thode permettant 
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
