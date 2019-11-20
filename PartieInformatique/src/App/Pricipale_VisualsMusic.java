package App;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Controller.Controller_Bouton_LecturePause;
import Controller.Controller_MenuFichier;
import Model.Model;
import View.Vue_2D;
import View.Vue_3D;
import View.Vue_Erreur;
/** 
 * @author Goodwui
 * Classe repr�sentant l'IG, compos� de 
 *     - un menu
 *     - le visualiseur de musique
 *     - les bouton de lectures
 */
public class Pricipale_VisualsMusic extends JFrame {

	///////////////////////////////////////
	////////////// Attributs //////////////
	///////////////////////////////////////

	/**
	 * Model qui sera inc�rer dans la cr�ation des Action
	 * afin de respecter le mod�le MVC
	 */
	private Model model;

	/**
	 * Bouton permettant de mettre en marche la musique
	 * S'affiche si la vid�o est en pause
	 */	
	private JButton bouton_playPause;
	private JButton bouton_stop;
	//private JButton bouton_volume;
	private JButton bouton_pleinEcran;

	/**
	 * Bandeau inf�rieur de l'IG
	 * Contiendra les diff�rents boutons de navigation de la musique
	 * (play, stop, pause, ...)
	 */
	private JPanel panel_bouton;

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
	private JMenuItem menu_fichier_ouvrir;

	/**
	 * Panneau principale
	 * Affichera les formes 2D en fonction de la musique
	 */
	private Vue_2D visualisateur2D;
	
	/**
	 * Panneau principale
	 * Affichera les formes 3D en fonction de la musique
	 */
	private Vue_3D visualisateur3D;
	
	/**
	 * Message d'erreur qui s'affiche quand:
	 *    - Aucun fichier n'a �t� s�lectionner mais que
	 *		l'utilisateur essai de le lire
	 */
	private Vue_Erreur erreur;
	private boolean pleine_ecran = false;

	///////////////////////////////////////
	////////////// M�thodes ///////////////
	///////////////////////////////////////

	/**
	 * Cr�ateur de l'IG toute enti�re
	 */
	public Pricipale_VisualsMusic () {
		/* TODO A voir
		 * 		changer le constructeur en main
		 * 		Mettre les attributs directement dans le main
		 */
		//Cr�ation Model
		model = new Model();

		//Param�trage de la fen�tre
		this.FullScreen();

		//Cr�ation des �l�ments 
		this.creationMenu();
		this.creationBouton();
		this.creationVisualisateur();
		erreur = new Vue_Erreur();

		//Ajout des observer
		model.addObserver(visualisateur2D);
		model.addObserver(visualisateur3D);
		model.addObserver(erreur);

		//Ajout de tous les �l�ments
		this.add(panel_bouton, BorderLayout.SOUTH);
		this.add(menu, BorderLayout.NORTH);
		this.add(visualisateur2D, BorderLayout.CENTER);
		//this.add(pleine_ecran);

		this.pack();

	}

	/**
	 * Cr�ation du l'IG sup�rieur
	 * Menu (fichier, ...)
	 */
	private void creationMenu() {

		//Cr�ation des �l�ments		
		menu = new JMenuBar();

		menu_fichier = new JMenu("Fichier");
		menu_fichier_ouvrir = new JMenuItem("Ouvrir un fichier...");

		//Modification des �l�ments
		menu.setBackground(new Color(206, 213, 209));

		//Ajout des Controller
		menu_fichier_ouvrir.addActionListener(new Controller_MenuFichier(model));

		//Ajout des �l�ments
		menu_fichier.add(menu_fichier_ouvrir);

		menu.add(menu_fichier);

	}

	/**
	 * Cr�ation du l'IG inf�rieur
	 * Bouton de navigation
	 */
	private void creationBouton() {

		//Cr�ation des �l�ments
		panel_bouton = new JPanel();
		
		bouton_playPause = new JButton("Play");
		bouton_stop = new JButton("Stop");
		bouton_pleinEcran = new JButton("Ecran");

		//Modification des �l�ments
		panel_bouton.setBackground(new Color(87, 73, 73, 50));
		bouton_playPause.setPreferredSize(new Dimension(100,50));
		bouton_stop.setPreferredSize(new Dimension(100,50));
		bouton_pleinEcran.setPreferredSize(new Dimension(100,50));

		//Ajout des Controller
		bouton_playPause.addActionListener(new Controller_Bouton_LecturePause(model));
		bouton_stop.addActionListener(new Controller_Bouton_LecturePause(model));
		bouton_pleinEcran.addActionListener(new Controller_Bouton_LecturePause(model));

		//Ajout des �l�ments
		panel_bouton.add(bouton_playPause);
		panel_bouton.add(bouton_stop);
		panel_bouton.add(bouton_pleinEcran);

	}

	/**
	 * Cr�ation du l'IG centrale
	 * Affichage des formes
	 */
	private void creationVisualisateur() {

		//Cr�ation des �l�ments
		visualisateur2D = new Vue_2D();	
		visualisateur3D = new Vue_3D();	
		
		//Modification des �l�ments
		visualisateur2D.setPreferredSize(
				new Dimension(800,450)); //rapport de 16:9
		visualisateur3D.setPreferredSize(
				new Dimension(800,450)); //rapport de 16:9

		//Ajout des �l�ments

	}

	/** 
	 * Cr�ation de la f�netre � dimension normale
	 *  et FullEcran selon une boolean 
	 */
	public void FullScreen() {
		Dimension tailleEcran = Toolkit.getDefaultToolkit().
				getScreenSize();	
		int height = tailleEcran.height;
		int width = tailleEcran.width;
		
		if(!pleine_ecran) {
			this.setTitle("Visuals Music");
			pleine_ecran = false;
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// On r�cuper la taille de l'�cran
			this.setSize(width/2, height/2);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
		else {
			this.revalidate();
			pleine_ecran = true;
			this.setTitle("Visuals Music");
			this.setExtendedState(this.MAXIMIZED_BOTH);
			this.setVisible(true);
			this.pack();

		}
	}

	public static void main (String[] args) {
		Pricipale_VisualsMusic vue = new Pricipale_VisualsMusic();

	}

}
