package Model;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

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
public class Model extends Observable {

	/**
	 * Fichier qui sera �cout�
	 */
	private File fichier;
	
	/**
	 * Classe de type Model, connu et instanci� uniquement ici
	 * Permet le MultiThreading et ainsi de garder la main sur le programme
	 */
	private Model_Musique musique = new Model_Musique();
	
	/**
	 * Permet le MultiThreading et ainsi de garder la main sur le programme
	 * Cr�� a partir de musique, attribut d�cris ci-dessus
	 */
	private Thread musiqueThread;
	
	//TODO javadoc
	private boolean enCours;
	
	/**
	 * D�finis si le programme a rencontr� une erreur
	 * 0 : aucune erreur
	 * 1 : erreur de type ""
	 */
	private IOException erreur = null;
	private int bit;

	//TODO bricolage : fonctionne pas
	public void modifier () {
		
		setChanged();
		notifyObservers();
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
			if (Thread.State.TERMINATED == musiqueThread.getState()) {
				musiqueThread = null;
				musique.setPause(true);
			}
		}
	}

	public void setFichier(File file) {
		fichier = file;
	}

	public Model_Musique getMusique() {
		return musique;
	}
	
	public void stop() {
		musique.reset();
	}

	public boolean isFileLoaded() {
		if (fichier == null) return false;
		return true;
	}

	public void setErreur(IOException e) {
		erreur  = e;
		setChanged();
		notifyObservers();
		erreur = null;
	}

	/**
	 * @return le type d'une erreur si elle existe
	 */
	public IOException getErreur() {
		return erreur;
	}
	
	/**
	 * @return le ration de la fr�quense actuel
	 * par rapport � celle du fichier
	 */
	public double getRatioFrequence() {
		double freq = 0;
		freq = musique.getFrequence()/musique.getAudioFormat().getFrameRate();
		return freq;
	}
}
