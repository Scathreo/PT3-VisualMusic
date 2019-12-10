package Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

//TODO Javadoc, contenu
/** 
 * 
 * @author goodw
 *
 * Classe de type Model, permettant la structure MVC
 * 	Instanci� uniquement dans le code principale
 * 	et m�thode quasi-uniquement utilis� dans les Controller
 * 	lors d'Action
 * 
 * Cette classe conserne toute la gestion �venementiel du
 * 	programme
 * 	Elle informe �galement les Vue
 */
public class Model extends Observable implements Observer {

	/**
	 * Fichier qui sera �cout�
	 */
	private File fichier;
	
	/**
	 * Classe de type Model, connu et instanci� uniquement ici
	 * Permet le MultiThreading et ainsi de garder la main sur le programme
	 */
	private Model_Musique musique = new Model_Musique(this);
	
	/**
	 * Permet le MultiThreading et ainsi de garder la main sur le programme
	 * Cr�� a partir de musique, attribut d�cris ci-dessus
	 */
	private Thread musiqueThread;
	
	/**
	 * permet de connaitre si la musique 
	 * est en cours de lecture
	 */
	private boolean enCours;
	
	/**
	 * D�finis si le programme a rencontr� une erreur
	 * 0 : aucune erreur
	 * 1 : erreur de type ""
	 */
	private Exception erreur = null;
	//TODO attribut
	private int bit;
	private Map<String, Integer> parametres = new HashMap<String, Integer>();
	
	//TODO
	public Model(String[] parameters) {
		
		super();
		
		for (String parametre : parameters) {
			
			if (parametre.equals("Amplitude"))
				this.parametres.put(parametre, 100);
			
			else if (parametre.equals("Epaisseur"))
				this.parametres.put(parametre, 60);
			
			else
				this.parametres.put(parametre, 0);
			
		}
	}

	//TODO Check pause / mettre pause a true d�s le d�but
	public void lectureFichier() {
		if (musiqueThread == null || musique.isPause()) {
			if (!musique.isLoad())
				musique.initialisation(fichier);
	
			//cool du multithreading :)
			musiqueThread = new Thread(musique);
			musiqueThread.start();
			//modifier();
			/*Je crois on peux delte TODO
			if (Thread.State.TERMINATED == musiqueThread.getState()) {
			 
				musiqueThread = null;
				musique.setPause(true);
			}
			*/
		}
	}
	/**
	 * permet de modifier le nom du fichier
	 */
	public void setFichier(File file) {
		fichier = file;
	}
	
	/**
	 * 
	 * @return le model de la musique
	 */
	public Model_Musique getMusique() {
		return musique;
	}
	/**
	 * permet d'arr�ter la musique
	 * et de remettre le m�me fichier
	 * au d�but de la lecture
	 */
	public void stop() {
		musique.reset();
	}

	/**
	 * @return une boolean qui permet de savoir
	 * si le fichier est en cours de lecture ou pas
	 * true : le fichier est entrain d'�tre lu
	 * false : il n'y a pas de fichier en cour de lecture
	 */
	public boolean isFileLoaded() {
		if (fichier == null) return false;
		return true;
	}

	/**
	 * notify si il y a une erreur,
	 * � la vue
	 */
	public void setErreur(Exception e) {
		erreur  = e;
		setChanged();
		notifyObservers();
		erreur = null;
	}

	/**
	 * @return le type d'une erreur si elle existe
	 */
	public Exception getErreur() {
		return erreur;
	}
	
	/**
	 * @return le ration de la fr�quense actuel
	 * par rapport � celle du fichier
	 */
	public double getRatioFrequence() {
		double freq = 0;
		freq = 	(musique.getFrequence() 
				* (parametres.get("Amplitude")) / 100)
				/ musique.getAudioFormat().getFrameRate();
		return freq;
	}

	/**
	 * M�thode permettant de mettre � jour
	 * et de notify l'observer
	 */
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return the parametres
	 */
	public Map<String, Integer> getParametres() {
		return parametres;
	}
	
	//TODO
	public void parametersChanged(String textLabel, int texteToInt) {
		parametres.put(textLabel, texteToInt);
		setChanged();
		notifyObservers();
	}
}
