package Controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Model.Model;
import View.Vue_2D;
import View.Vue_3D;

/**
 * 
 * Classe impl�mentant ActionListener
 * 
 * @author goodw
 */
public class Controller_Menu extends Controller implements ActionListener, MenuListener  {

	/**
	 * Constructeur utilisant le Constructeur Parent
	 * @param model   : Instanciant le Model
	 * @param handler : Instanciant l'adapteur
	 */
	public Controller_Menu(Model model,
			Set<Component> handler) {

		super(model, handler);

	}

	/**
	 * M�thode de l'interface parente ActionListener
	 * 
	 * Si on clique sur un JMenuItem "Ouvrir un fichier ...", cr�ation d'un 
	 * 	FileChooser (permettant de choisir un fichier sur l'espace disque)
	 * 	qui renvoi et ouvre le fichier choisit
	 */
	public void actionPerformed(ActionEvent arg0) {

		JMenuItem menuItem = (JMenuItem) arg0.getSource();		
		
		if (menuItem.getText().equals("Ouvrir un fichier...")) {

			/*// TODO A d�commenter ...
			JFileChooser fc = new JFileChooser();
			int valeur_de_retour = fc.showOpenDialog(null);

			if (valeur_de_retour == JFileChooser.APPROVE_OPTION)
				model.setFichier(fc.getSelectedFile());
			 */		 
		}

		if (menuItem.getText().equals("2D") && menuItem.isSelected()) {

			JFrame fenetre	= null;
			Vue_2D twoD 	= null;
			Vue_3D threeD 	= null;

			model.setIsThreeDimension(false);

			for (Component b : handler) {

				if (b instanceof JFrame)
					fenetre = (JFrame) b;

				if (b instanceof Vue_2D)
					twoD = (Vue_2D) b;

				if (b instanceof Vue_3D)
					threeD = (Vue_3D) b;

			}

			try {

				fenetre.remove(threeD);
				fenetre.add(twoD, BorderLayout.CENTER);
				fenetre.revalidate();
				fenetre.repaint();

			}

			catch (NullPointerException e) {

				//TODO message d'erreur
				model.setErreur(e);
				return;

			}
		}

		if (menuItem.getText().equals("3D") && menuItem.isSelected()) {

			JFrame fenetre	= null;
			Vue_2D twoD 	= null;
			Vue_3D threeD 	= null;

			for (Component b : handler) {

				if (b instanceof JFrame)
					fenetre = (JFrame) b;

				if (b instanceof Vue_2D)
					twoD = (Vue_2D) b;

				if (b instanceof Vue_3D)
					threeD = (Vue_3D) b;

			}

			try {

				fenetre.remove(twoD);
				fenetre.add(threeD, BorderLayout.CENTER);
				fenetre.revalidate();
				fenetre.repaint();

			}

			catch (NullPointerException e) {
				
				model.setErreur(e);
				return;

			}

			model.setIsThreeDimension(true);

		}
		
		model.setFichier(new File("res/auprintemps-44100-32.wav"));	//TODO A supprimer
		
	}
	
	
	public void menuCanceled(MenuEvent arg0) {}
	public void menuDeselected(MenuEvent arg0) {}
	
	public void menuSelected(MenuEvent arg0) {
		
		JMenu menu = (JMenu) arg0.getSource();
		
		if (menu.getText().equals("Param�tres")) {
			System.out.println(model.isPrintSettings());
			if (!model.isPrintSettings())
				model.setPrintSettings(true);
			
			else model.setPrintSettings(false);
			
		}
	}
}

