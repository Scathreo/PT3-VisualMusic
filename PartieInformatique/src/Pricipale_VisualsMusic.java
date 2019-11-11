

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

import Controller.Controller_Bouton_LecturePause;
import Controller.Controller_MenuFichier;
import Model.Model;
import View.Vue_Erreur;
import View.Vue_GenerationForme;

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

	//TODO javadoc
	Model model;
	
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
	 * Affichera les formes en fonction de la musique
	 */
	private Vue_GenerationForme visualisateur;
	
	//TODO javadoc
	private Vue_Erreur erreur;

	///////////////////////////////////////
	////////////// M�thodes ///////////////
	///////////////////////////////////////

	/**
	 * Cr�ateur de l'IG toute enti�re
	 */
	public Pricipale_VisualsMusic () {
		/* TODO changer le constructeur en main
		 * 		Mettre les attributs directement dans le main
		 */
		//Cr�ation Model
		model = new Model();
		
		//Param�trage de la fen�tre
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Visuals Music");
		
		//Cr�ation des �l�ments 
		this.creationMenu();
		this.creationBouton();
		this.creationVisualisateur();
		erreur = new Vue_Erreur();
		
		//Ajout des observer
		model.addObserver(visualisateur);
		model.addObserver(erreur);
		
		//Ajout de tous les �l�ments
		this.add(panel_bouton, BorderLayout.SOUTH);
		this.add(menu, BorderLayout.NORTH);
		this.add(visualisateur, BorderLayout.CENTER);
				
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
		
		bouton_play = new JButton("Play");
		bouton_pause = new JButton("Pause");
		
		//Modification des �l�ments
		panel_bouton.setBackground(new Color(87, 73, 73, 50));
		
		//Ajout des Controller
		bouton_play.addActionListener(new Controller_Bouton_LecturePause(model));
		bouton_pause.addActionListener(new Controller_Bouton_LecturePause(model));
		
		//Ajout des �l�ments
		panel_bouton.add(bouton_play);
		panel_bouton.add(bouton_pause);

	}
	
	/**
	 * Cr�ation du l'IG centrale
	 * Affichage des formes
	 */
	private void creationVisualisateur() {
		
		//Cr�ation des �l�ments
		visualisateur = new Vue_GenerationForme();	
		
		//Modification des �l�ments
		visualisateur.setPreferredSize(
				new Dimension(800,450)); //rapport de 16:9
		
		//Ajout des �l�ments
		
	}
	
	public static void main (String[] args) {
		Pricipale_VisualsMusic easy = new Pricipale_VisualsMusic();
	}
	
}
