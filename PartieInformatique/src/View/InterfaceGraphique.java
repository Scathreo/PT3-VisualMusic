package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/** 
 * @author Goodwui
 * Classe repr�sentant l'IG, compos� de 
 *     - un menu
 *     - le visualiseur de musique
 *     - les bouton de lectures
 */
public class InterfaceGraphique extends JFrame {

	///////////////////////////////////////
	////////////// Attributs //////////////
	///////////////////////////////////////

	/**
	 * Bouton permettant de mettre en marche la musique
	 * S'affiche si la vid�o est en pause
	 */	
	private JButton bouton_play;
	
	/**
	 * Bouton permettant de mettre en pause la musique
	 * S'affiche si la vid�o est en lecture
	 */	
	private JButton bouton_pause;
	
	//private JButton bouton_stop;
	//private JButton bouton_volume;
	//private JButton bouton_pleinEcran;
		
	/**
	 * Bandeau inf�rieur de l'IG
	 * Contiendra les diff�rents boutons de navigation de la musique
	 * (play, stop, pause, ...)
	 */
	private JPanel bouton;
	
	/**
	 * Menu qui permettra d'acc�der � toutes les fonctionnalit�es de l'application
	 * ouvrir une musique, ...
	 */
	private JMenuBar menu;
	
	/**  
	 * Cat�gorie du @menu
	 * Actions disponibles :
	 * 		- Ouvrir un fichier...
	 */
	private JMenu menu_fichier;
	
	/**
	 * Actions de @menu_fichier
	 * Permet d'ouvrir un fichier
	 */
	private JMenu menu_fichier_ouvrir;
	
	/**
	 * Panneau principale
	 * Affichera les formes en fonction de la musique
	 */
	private IGAffichage visualisateur;

	///////////////////////////////////////
	////////////// M�thodes ///////////////
	///////////////////////////////////////

	/**
	 * Cr�ateur de l'IG toute enti�re
	 */
	public InterfaceGraphique () {

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Visuals Music");
		
		this.creationMenu();
		this.creationBouton();
		this.creationVisualisateur();
		
		
		//Ajout de tous les �l�ments
		this.add(bouton, BorderLayout.SOUTH);
		this.add(menu, BorderLayout.NORTH);
		this.add(visualisateur, BorderLayout.CENTER);
				
		this.pack();
		
	}
	
	/**
	 * Cr�ation du l'IG sup�rieur
	 * Menu (fichier, ...)
	 */
	public void creationMenu() {
		
		//Cr�ation des �l�ments
		bouton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		menu = new JMenuBar();
		
		menu_fichier = new JMenu("Fichier");
		menu_fichier_ouvrir = new JMenu("Ouvrir un fichier...");
		
		//Modification des �l�ments
		menu.setBackground(new Color(206, 213, 209));
		
		//Ajout des �l�ments
		menu_fichier.add(menu_fichier_ouvrir);
		
		menu.add(menu_fichier);
		
	}
	
	/**
	 * Cr�ation du l'IG inf�rieur
	 * Bouton de navigation
	 */
	public void creationBouton() {
		
		//Cr�ation des �l�ments
		bouton = new JPanel();
		
		bouton_play = new JButton("Play");
		bouton_pause = new JButton("Pause");
		
		//Modification des �l�ments
		bouton.setBackground(new Color(87, 73, 73, 50));
		bouton.setPreferredSize(new Dimension(10, 100));
				//Centrer les bouton verticalement
		
		//Ajout des �l�ments
		bouton.add(bouton_play);
		bouton.add(bouton_pause);

	}
	
	/**
	 * Cr�ation du l'IG centrale
	 * Affichage des formes
	 */
	public void creationVisualisateur() {
		
		//Cr�ation des �l�ments
		visualisateur = new IGAffichage();	
		
		//Modification des �l�ments
		visualisateur.setPreferredSize(new Dimension(700,400));
		
		//Ajout des �l�ments
		
	}
	

	
}
