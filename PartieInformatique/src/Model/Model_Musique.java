package Model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/** 
 * 
 * @author goodw
 *
 * Classe de type Model, connu uniquement par la Classe "Model"
 * 
 * Sert � faire du MultiThreading et ainsi permettre la lecture 
 * 	audio tous en �tant en possibilit� d'affect� celle ci ou l'IG
 * 	en g�n�ral
 * 	C'est � dire que sans MultiThreading, il �tait impossible de
 * 	faire pause quand une musique se lanc�
 * 
 * Cette classe conserne uniquement la lecture de la musique � partir
 * 	d'un fichier audio compos� uniquement de donn�es brutes
 */
public class Model_Musique implements Runnable {

	//TODO javadoc
	private SourceDataLine line;
	private AudioInputStream audioInputStream;	
	private AudioFormat audioFormat; 

	/**
	 * D�finis si la lecture doit �tre en pause ou non
	 * true  : la lecture s'arr�te
	 * false : la lecture continue/commence
	 */
	private boolean pause = true;

	/**
	 * D�finis si un fichier � �t� charg� et convertis en line
	 */
	private boolean load = false;

	/**
	 * Initialise la lecture de la musique
	 * 	A �x�cuter avant la m�thode Thread.start(), sinon erreur
	 * 
	 * @param file : le fichier audio a ouvrir
	 * @return un boolean, afin de voir si la pr�paration
	 * 	c'est bien pass�
	 */
	public boolean initialisation(File file){

		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
		} 
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			return false;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		audioFormat = audioInputStream.getFormat();

		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			load = true;
		} 
		catch (LineUnavailableException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	} 

	/**
	 * M�thode de l'interface parente Runnable
	 * 	permettant l'execution de la m�thode Thread.start()
	 * 
	 * Lit la musique en cr�ant un buffer de byte
	 * 
	 * Enfin quand la lecture et finis, ferme le fichier
	 */
	public void run() {	
		pause = false;

		try {
			line.open(audioFormat);
		} 
		catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		}

		line.start();

		try {
			byte bytes[] = new byte[1024];

			int bytesRead = 0;			
			while (!pause) {
				bytesRead = audioInputStream.read(bytes, 0, bytes.length);
				if (bytesRead != -1) {
					line.write(bytes, 0, bytesRead);
				}
			}
		} 
		catch (IOException io) {
			io.printStackTrace();
			return;
		}

		line.close();
	}
	
	/**
	 * Permet de mettre en pause ou en lecture
	 * 
	 * @param b
	 * true  : sera mis en pause
	 * false : sera mis en lecture
	 */
	public void setPause(boolean b) {
		this.pause = b;
	}	

	/**
	 * Informe de l'�tat de la pause
	 * 
	 * @return un boolean informant de l'�tat de la pause
	 * true  : fichier en pause
	 * false : fichier en lecture
	 */
	public boolean isPause() {
		return pause;
	}
	
	/**
	 * @return the load
	 */
	public boolean isLoad() {
		return load;
	}
	
	/* M�thode qui r�initialise la musique du d�but  */
	public void reset() {
		if(!pause) {
			pause = true;
			audioFormat = null;
			audioInputStream = null;
			line.close();
			line = null;
			load = false;
		}
	}
	
	/*
	 public SourceDataLine getLine() {
		return line;
	}
	*/
	
	/* Transformation de Fourier */
	public static double[][] TransformationFourier( double[][] tableau) {
		/* Longueur de notre signal doit �tre un multiple de 2*/
		int longueurSignal = tableau.length;
		
		/* fin de l'appel r�cursif */
		double[][] tampon = new double [longueurSignal][1];
				if (longueurSignal==1) return tampon;
		
		/* Pr�paration des donn�es pour faire Fourier */
		int longueurSignalDiviser2 = longueurSignal/2;
		double[][] transformationFourier = new double[longueurSignal][2];
		
		/* D�claration des tableaux double avec leur taille */
		double[][] pair = new double[longueurSignalDiviser2][2];
		double[][] impair = new double[longueurSignalDiviser2][2];	
		
		/* Coupe le signal en termes d'indices pairs et impairs */
		for(int i=0; i < longueurSignalDiviser2; i++) {
			pair[i] = tableau[2*i];
			impair[i] = tableau[2*i+1];
			
		}
		
		/* Calcul recursif de la fonction */
		pair = 	TransformationFourier(pair);
		impair = 	TransformationFourier(impair);
			
		/* Reconstruction des valeurs par Fourier */
		for(int i = 0; i < longueurSignal; i++) {
			transformationFourier[i][0] = pair[i%longueurSignalDiviser2][0] 
					+ impair[i%longueurSignalDiviser2][0]*Math.cos(2*Math.PI*i/longueurSignal) 
					 + impair[i%longueurSignalDiviser2][1]*Math.sin(2*Math.PI*i/longueurSignal);
			
			transformationFourier[i][1] = pair[i%longueurSignalDiviser2][1] 
					+ impair[i%longueurSignalDiviser2][1]*Math.cos(2*Math.PI*i/longueurSignal) 
					 - impair[i%longueurSignalDiviser2][0]*Math.sin(2*Math.PI*i/longueurSignal);
		}
		return transformationFourier;
				
		}
	
}
