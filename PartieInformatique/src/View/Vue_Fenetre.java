package View;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JTextField;

import Controller.Controller_Bouton_Musique;
import Controller.Controller_Bouton_PlusMoins;
import Controller.Controller_Menu;
import Controller.Controller_Slider;
import Controller.Adapteur_ControllerVue;
import Model.Model;
import View.Vue_2D;
import View.Vue_3D;
import View.Vue_Ecran_Full;
import View.Vue_Erreur;
import View.Vue_TextField_PlusMoins;

/** 
 * Classe repr�sentant l'IG, compos� de 
 *     - un menu
 *     - le visualiseur de musique
 *     - les bouton de lectures
 *     
 * @author 
 * Goodwin
 * 	Cr�ation de la quasi totalit� de la classe
 * 	Correction de la m�thode frameCenter, creationSlider
 * Perrin
 * 	Cr�ation de la m�thode frameCenter, creationSlider
 */
public class Vue_Fenetre extends JFrame implements Observer {

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
	 * si vid�o en pause : "Play"
	 * sinon 			 : "Pause
	 */	
	private JButton bouton_playPause;

	/**
	 * Bouton permettant de stoper la musique
	 * Elle reprendra � zeron si on reclique sur play
	 */	
	private JButton bouton_stop;

	//TODO
	//private JButton bouton_volume;

	/**
	 * Bouton permettant de mettre la fen�tre en plein �cran
	 */	
	private JButton bouton_pleinEcran;

	/**
	 * Adapteur entre les controller et l'interface graphique
	 * Sert � retirer des �l�ments dans l'interface quand on 
	 * clique sur un bouton, ect ...
	 */
	Set<Component> handler = new HashSet<Component>();

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
	 * Cat�gorie du @menu
	 * Actions disponibles :
	 * 		- Amplitude
	 * 		- Taille rectangles
	 * 		- Espacement rectangles
	 */
	private JMenu menu_parametre;

	//TODO A delete
	private JTextField 	menu_parametre_amplitude_field;
	private JButton 	menu_parametre_amplitude_plus;
	private JButton 	menu_parametre_amplitude_moins;
	private JLabel 		menu_parametre_amplitude_label;
	private JTextField 	menu_parametre_width_field;
	private JButton 	menu_parametre_width_plus;
	private JButton 	menu_parametre_width_moins;
	private JLabel 		menu_parametre_width_label;
	private JTextField 	menu_parametre_margin_field;
	private JButton 	menu_parametre_margin_plus;
	private JButton 	menu_parametre_margin_moins;
	private JLabel 		menu_parametre_margin_label;
	private Vue_TextField_PlusMoins menu_parametre_amplitude_plusMoins;
	private Vue_TextField_PlusMoins menu_parametre_width_plusMoins;
	private Vue_TextField_PlusMoins menu_parametre_margin_plusMoins;
	private JPanel menu_parametre_amplitude;
	private JPanel menu_parametre_width;
	private JPanel menu_parametre_margin;

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

	/**
	 * Groupe de radio bouton du menu "Affichage"
	 * Groupe les bouton radios afin de selectioner 
	 * le type de vue (2D, 3D)
	 */
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
	private Vue_Ecran_Full vue_full_ecran;
	///////////////////////////////////////
	////////////// M�thodes ///////////////
	///////////////////////////////////////

	/**
	 * Cr�ateur de l'IG toute enti�re
	 */
	public Vue_Fenetre(Model model) {

		/* TODO A voir
		 * 		changer le constructeur en main
		 * 		Mettre les attributs directement dans le main
		 */

		//TODO a voir pour pas le m�tre dans la fenetre param�tres
		String[] listOfParameters = {	
				"Amplitude",
				"Epaisseur",
				"Espacement"
		};

		//Cr�ation Model
		model = new Model(listOfParameters);

		//Cr�ation des �l�ments 
		this.creationMenu(model, handler);
		this.creationBouton(model, handler);
		this.creationSliderMusique(model, handler);
		this.creationVisualisateur();
		erreur = new Vue_Erreur();
		vue_full_ecran = new Vue_Ecran_Full();

		//Ajout des observer
		model.addObserver(visualisateur2D);
		model.addObserver(visualisateur3D);
		model.addObserver(erreur);
		model.addObserver(vue_full_ecran);
		model.addObserver(this);
		
		
		handler.add(this);

		//Ajout de tous les �l�ments
		this.add(panel_bouton, BorderLayout.SOUTH);
		this.add(menu, BorderLayout.NORTH);
		this.add(visualisateur2D, BorderLayout.CENTER);

		//Param�trage de la fen�tre
		this.frameCenter();
		this.setTitle("Visuals Music");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();


	}

	/**
	 * Cr�ation du l'IG sup�rieur
	 * Menu (fichier, ...)
	 * @param handler2 
	 */
	private void creationMenu(Model model, Set<Component> handler2) {

		//Cr�ation des �l�ments		
		menu = new JMenuBar();

		menu_fichier = new JMenu("Fichier");
		menu_fichier_ouvrir = new JMenuItem("Ouvrir un fichier...");

		menu_affichage = new JMenu("Affichage");
		menu_affichage_2D = new JRadioButtonMenuItem("2D");
		menu_affichage_3D = new JRadioButtonMenuItem("3D");

		menu_affichage_dimension = new ButtonGroup();


		menu_parametre = new JMenu("Param�tres");
		menu_parametre_amplitude_moins	= new JButton("-");
		menu_parametre_amplitude_plus	= new JButton("+");
		menu_parametre_amplitude_field  = new JTextField(4);
		menu_parametre_amplitude_label  = new JLabel("Amplitude");

		menu_parametre_margin_moins		= new JButton("-");
		menu_parametre_margin_plus		= new JButton("+");
		menu_parametre_margin_field	    = new JTextField(4);
		menu_parametre_margin_label 	= new JLabel("Espacement");

		menu_parametre_width_moins		= new JButton("-");
		menu_parametre_width_plus		= new JButton("+");
		menu_parametre_width_field 		= new JTextField(4);
		menu_parametre_width_label 		= new JLabel("Epaisseur");

		menu_parametre_amplitude_plusMoins = new Vue_TextField_PlusMoins(
				menu_parametre_amplitude_plus,
				menu_parametre_amplitude_field,
				menu_parametre_amplitude_moins);

		menu_parametre_margin_plusMoins = new Vue_TextField_PlusMoins(
				menu_parametre_margin_plus,
				menu_parametre_margin_field,
				menu_parametre_margin_moins);

		menu_parametre_width_plusMoins = new Vue_TextField_PlusMoins(
				menu_parametre_width_plus,
				menu_parametre_width_field,
				menu_parametre_width_moins);

		menu_parametre_amplitude 	= new JPanel(new GridLayout(1, 2, 5, 0));
		menu_parametre_width 		= new JPanel(new GridLayout(1, 2, 5, 0));
		menu_parametre_margin 		= new JPanel(new GridLayout(1, 2, 5, 0));

		//Modification des �l�ments
		menu.setBackground(new Color(206, 213, 209));

		menu_affichage_2D.setSelected(true);

		menu_parametre_amplitude_field.setEditable(false);
		menu_parametre_width_field.setEditable(false);
		menu_parametre_margin_field.setEditable(false);

		//Ajout des Controller
		menu_fichier_ouvrir.addActionListener(new Controller_Menu(model, handler2));

		menu_affichage_2D.addActionListener(new Controller_Menu(model, handler2));
		menu_affichage_3D.addActionListener(new Controller_Menu(model, handler2));
		


		handler.add(menu_parametre_amplitude);
		handler.add(menu_parametre_width);
		handler.add(menu_parametre_margin);

		//Ajout des �l�ments
		menu_fichier.add(menu_fichier_ouvrir);

		menu_affichage_dimension.add(menu_affichage_2D);
		menu_affichage_dimension.add(menu_affichage_3D);

		menu_affichage.add(menu_affichage_2D);
		menu_affichage.add(menu_affichage_3D);

		menu_parametre_amplitude.add(menu_parametre_amplitude_plusMoins);
		menu_parametre_amplitude.add(menu_parametre_amplitude_label);
		menu_parametre_margin.add(menu_parametre_margin_plusMoins);
		menu_parametre_margin.add(menu_parametre_margin_label);
		menu_parametre_width.add(menu_parametre_width_plusMoins);
		menu_parametre_width.add(menu_parametre_width_label);

		menu_parametre.add(menu_parametre_amplitude);
		menu_parametre.addSeparator();
		menu_parametre.add(menu_parametre_margin);
		menu_parametre.addSeparator();
		menu_parametre.add(menu_parametre_width);

		menu.add(menu_fichier);
		menu.add(menu_affichage);
		menu.add(menu_parametre);

	}

	/**
	 * Cr�ation du l'IG inf�rieur
	 * Bouton de navigation
	 */
	private void creationBouton(Model model, Set<Component> handler2) {

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
		bouton_playPause.addActionListener(new Controller_Bouton_Musique(model, handler2));
		bouton_stop.addActionListener(new Controller_Bouton_Musique(model, handler2));
		bouton_pleinEcran.addActionListener(new Controller_Bouton_Musique(model, handler2));

		

		handler.add(bouton_playPause);
		handler.add(bouton_stop);
		handler.add(bouton_pleinEcran);
		
		//Ajout des �l�ments
		panel_bouton.add(bouton_playPause);
		panel_bouton.add(bouton_stop);
		panel_bouton.add(bouton_pleinEcran);


	}
	/**
	 * Cr�ation d'une m�thode qui va cr�er un slider
	 * elle d�finit un volume par d�faut qui est � 30
	 */
	public void creationSliderMusique(Model model, Set<Component> handler2) {
		JSlider slider = new JSlider();

		slider.setPreferredSize(new Dimension(100,50));

		slider.setMaximum(100);
		slider.setMinimum(0);
		slider.setValue(30);

		slider.addChangeListener(new Controller_Slider(model, handler2));

		panel_bouton.add(slider);
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
		visualisateur3D.setSize( 		 //Oblig� de faire setSize pour un Canva
				new Dimension(800,450)); //rapport de 16:9

		//Ajout des �l�ments

		handler.add(visualisateur2D);
		handler.add(visualisateur3D);
	}

	//TODO Javadoc
	public void frameCenter() {

		Dimension tailleEcran = Toolkit.getDefaultToolkit().
				getScreenSize();

		int posY = 
				( (int) tailleEcran.getHeight()
						- (int) visualisateur2D.getPreferredSize().getHeight()
						- (int) menu.getPreferredSize().getHeight()
						- (int) panel_bouton.getPreferredSize().getHeight()) / 2;	
		int posX = 
				( (int) tailleEcran.getWidth() 
						- (int) visualisateur2D.getPreferredSize().getWidth()) / 2;

		this.setLocation(posX, posY);

	}


	public void update(Observable m, Object o) {

		Model model = (Model) m;

		if (model.getErreur() == null) {

			if (model.isFullScreen()) {

				this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				this.setUndecorated(true);
				//TODO mettre les taille des panels

			}

			else {

				this.pack();
				this.frameCenter();
				//TODO mettre les taille des panels

			}

			this.revalidate();
			this.repaint();

		}
	}
}
