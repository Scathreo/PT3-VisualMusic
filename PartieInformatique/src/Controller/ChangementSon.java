package Controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Classe qui permettra de g�rer le son lorsque 
 * l'utilisateur va le changer
 * @author perri
 *
 */
public class ChangementSon implements ChangeListener{
	// d�finition des variables
	// la valeur du volume au moment pas chang�
	private  int slider;
	// le slider 
	private JSlider slider_son;
	
	// on r�cup�re la valeur du slider
	public ChangementSon(int i) {
		// TODO Auto-generated constructor stub
		this.slider = i;
	}
	@Override
	/*
	 * A impl�menter pour que le "click" fait pour changer
	 * le volume est ajout�e au slider
	 * Sachant que la modification du nombre 
	 * ne modifie pas pour le moment le volume
	 */
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		 
	        String str = Integer.toString(slider);
	        
	}
}
