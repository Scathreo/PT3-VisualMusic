package App;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import Controller.Controller_Bouton_LecturePause;
import Controller.Controller_Menu;
import Controller.Handler_ControllerHandler;
import Model.Model;
import Model.Model_Cube;
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

	//TODO
	private JButton bouton_stop;

	//TODO
	//private JButton bouton_volume;

	//TODO
	private JButton bouton_pleinEcran;
	
	//TODO
	private Handler_ControllerHandler handler = new Handler_ControllerHandler();

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
	 * Cat�gorie du @menu
	 * Actions disponibles :
	 * 		- Vue 2D
	 * 		- Vue 3D
	 */
	private JMenu menu_affichage;

	/**
	 * Actions de @menu_affichage
	 * Permet d'afficher en 2D la musique
	 */
	private JRadioButtonMenuItem menu_affichage_2D;

	/**
	 * Actions de @menu_affichage
	 * Permet d'afficher en 3D la musique
	 */
	private JRadioButtonMenuItem menu_affichage_3D;
	
	private ButtonGroup menu_affichage_dimension;
	
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
	//TODO plus tard 
	private boolean pleine_ecran = false;
	/*
	 * Cube qui cr�er un cube
	 * a test� car pas afficher
	 */
	//TODO
	private Model_Cube cube;
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

		//Cr�ation des �l�ments 
		this.creationMenu();
		this.creationBouton();
		this.creationVisualisateur();
		erreur = new Vue_Erreur();

		//Ajout des observer
		model.addObserver(visualisateur2D);
		model.addObserver(visualisateur3D);
		model.addObserver(erreur);

		//Ajout de la fen�tre dans le handler
		handler.getComponent().add(this);

		//Ajout de tous les �l�ments
		this.add(panel_bouton, BorderLayout.SOUTH);
		this.add(menu, BorderLayout.NORTH);
		this.add(visualisateur2D, BorderLayout.CENTER);
		//this.add(visualisateur3D, BorderLayout.CENTER);
		//this.add(pleine_ecran);
		
		//Param�trage de la fen�tre
		this.fullScreen();
		this.setTitle("Visuals Music");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
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
		
		menu_affichage = new JMenu("Affichage");
		menu_affichage_2D = new JRadioButtonMenuItem("2D");
		menu_affichage_3D = new JRadioButtonMenuItem("3D");
		
		menu_affichage_dimension = new ButtonGroup();

		//Modification des �l�ments
		menu.setBackground(new Color(206, 213, 209));
		
		menu_affichage_2D.setSelected(true);

		//Ajout des Controller
		menu_fichier_ouvrir.addActionListener(new Controller_Menu(model, handler));
		menu_affichage_2D.addActionListener(new Controller_Menu(model, handler));
		menu_affichage_3D.addActionListener(new Controller_Menu(model, handler));

		//Ajout des �l�ments
		menu_fichier.add(menu_fichier_ouvrir);

		menu_affichage_dimension.add(menu_affichage_2D);
		menu_affichage_dimension.add(menu_affichage_3D);

		menu_affichage.add(menu_affichage_2D);
		menu_affichage.add(menu_affichage_3D);

		menu.add(menu_fichier);
		menu.add(menu_affichage);

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

		//Ajout des Bouton dans le handler
		handler.getComponent().add(bouton_playPause);
		handler.getComponent().add(bouton_stop);
		handler.getComponent().add(bouton_pleinEcran);
		
		//Ajout des Controller
		bouton_playPause.addActionListener(new Controller_Bouton_LecturePause(model, handler));
		bouton_stop.addActionListener(new Controller_Bouton_LecturePause(model, handler));
		bouton_pleinEcran.addActionListener(new Controller_Bouton_LecturePause(model, handler));

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

		//Ajout des �l�ments dans le handler
		handler.getComponent().add(visualisateur2D);
		handler.getComponent().add(visualisateur3D);

		//Ajout des �l�ments

	}

	/** 
	 * Cr�ation de la f�netre � dimension normale
	 *  et FullEcran selon une boolean 
	 */
	//TODO fullscreen -> abscence de vue
	public void fullScreen() {
		Dimension tailleEcran = Toolkit.getDefaultToolkit().
				getScreenSize();	
		int height = tailleEcran.height;
		int width = tailleEcran.width;
		
		if(!pleine_ecran) {
			pleine_ecran = false;

			// On r�cuper la taille de l'�cran
			this.setSize(width/2, height/2);
			this.setLocationRelativeTo(null);
			this.pack();
		}
		else {
			pleine_ecran = true;
			this.setExtendedState(this.MAXIMIZED_BOTH);
		}
	}
	/*
	 * Pour afficher notre application
	 */
	public static void main (String[] args) {
		Pricipale_VisualsMusic vue = new Pricipale_VisualsMusic();

	}

}
