package Controller;

import java.awt.Component;
import java.util.Set;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.Model;
/**
 * Classe qui permettra de g�rer le son lorsque 
 * l'utilisateur va le changer
 * @author perri
 *
 */
public class Controller_Slider extends Controller implements ChangeListener {
	
	public Controller_Slider(Model model, 
			Set<Component> handler) {
		
		super(model, handler);
		
	}

	/*
	 * A impl�menter pour que le "click" fait pour changer
	 * le volume est ajout�e au slider
	 * Sachant que la modification du nombre 
	 * ne modifie pas pour le moment le volume
	 */
	public void stateChanged(ChangeEvent arg0) {
		
		
	        
	}
}
