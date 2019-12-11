/**
 * 
 */
package Controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;


/*
 * TODO
 * Moyen que la classe soit inutile
 * Set directement dans les COntroller
 */
/**
 * Classe permettant de faire le lien entre l'interface graphique
 * et les controller
 * 
 * Exemple : Quand on clique sur Stop, on doit modifier 
 * le bouton play ou pause. Pour retrouver le bouton on l'ajoute �
 * cette classe
 * 
 * @author goodw
 */
public class Adapteur_ControllerVue {
	
	// Declaration des variables
	
	/**
	 * Ensemble de Composant graphiques
	 * regroupant tous les �l�ment que l'on doit reli� aux controlleur
	 */
	private Set<Component> component;

	/**
	 * Constructeur principal
	 * qui cr�er une hahset pour instanci� l'ensemble de composant
	 */
	public Adapteur_ControllerVue() {
		
		component = new HashSet<Component>();
		
	}
	/**
	 * permet de connaitre l'ensemble de composant
	 * et ainsi de le parcourir afin de trouv� un �l�ment particulier
	 */
	public Set<Component> getComponent() {
		
		return component;
		
	}
	
}
