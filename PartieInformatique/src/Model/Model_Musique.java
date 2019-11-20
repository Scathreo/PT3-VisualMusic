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

import flanagan.complex.Complex;
import flanagan.math.FourierTransform;

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

	//TODO voir pour pr�-load la musique
	
	/**
	 * Ligne permettant l'�coute audio
	 * Sort en son, ce qu'il y a �crit dedans
	 */
	private SourceDataLine line;
	
	/**
	 * Sert � transformer le fichier ouvert en 
	 * fichier ouvert � la lecture multim�dia
	 * 
	 * Courant d'entr�e audio
	 */
	private AudioInputStream audioInputStream;
	
	/**
	 * R�f�re tous les champs sp�cifique qu format audio
	 */
	private AudioFormat audioFormat; 
	
	/**
	 * Stock les fr�quences des octects lus lors 
	 * de l'�coute de la musique
	 */
	private double frequence;
	
	/**
	 * Tranform� rapide de Fourrier
	 * Sert � passer du signal temporel de la musique
	 * au spectre fr�quentiel
	 */
	private FourierTransform FFT = new FourierTransform();

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
			//TODO rendre le truc bien pour tout format de musique
			//frequence diff
			//encodage diff
			byte bytes[] = new byte[32768];	//32 * 1024
			
			int bytesRead = 0;			
			while (!pause) {
				bytesRead = audioInputStream.read(bytes, 0, bytes.length);
				if (bytesRead != -1) {	//si il y a des octets lus
					Complex[] comp = new Complex[bytesRead];	//taille = nb de bytes lus
					for (int variable_temporaire = 0; 
							variable_temporaire < bytesRead;
							variable_temporaire ++) {
						comp[variable_temporaire] = new Complex(bytes[variable_temporaire]);
					}
					FFT.setData(comp);
					FFT.transform();
					frequence = Math.abs(FFT.getTransformedDataAsComplex()[0].getReal()); //valeur absolue
					line.write(bytes, 0, bytesRead);
				}
				else break;
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
	 * @return the audioInputStream
	 */
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	/**
	 * @return the frequence
	 */
	public double getFrequence() {
		return frequence;
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
	
	/**
	 * Remet tous les argument � null
	 * Afin de remettre � z�ro lorsque l'utilisateur
	 * presse "stop" par exemple
	 */
	public void reset() {
		pause = true;
		audioFormat = null;
		audioInputStream = null;
		line.close();
		line = null;
		load = false;
	}
	
	/*
	 public SourceDataLine getLine() {
		return line;
	}
	*/
}
