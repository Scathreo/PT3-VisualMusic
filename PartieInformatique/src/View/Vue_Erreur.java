package View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import Model.Model;

//TODO javadoc
public class Vue_Erreur extends JOptionPane implements Observer {

	public void update(Observable m, Object arg1) {
		Model model = (Model) m;
		if (model.isErreur()) JOptionPane.showMessageDialog(null, 
				"D�sol� mais notre hamster s'est perdu, ... \n\nAucun fichier n'a �t� ouvert !!", 
				"Aucun fichier ouvert !!", ERROR_MESSAGE);
	}

}
