package Controller;

import Model.Model;

/**
 * 
 * @author goodw
 *
 * Classe permettant de faciliter la structure MVC
 * D�finit un Model qui sera commun � tout les Controller
 * 	et un Constructeur initialisant ce model
 * 
 */
public abstract class Controller {

	/**
	 * Model permettant la structure MVC
	 * Sera partager par toute les classes filles
	 */
	protected Model model;
	
	/**TODO
	 * 
	 */
	protected Adapteur_ControllerVue handler;

	/**
	 * Constructeur instanciant le Mod�le permettant la 
	 * 	structure MVC
	 * @param model : Instanciant le Model
	 */
	public Controller(Model model,
			Adapteur_ControllerVue handler) {
		super();
		this.handler = handler;
		this.model = model;
	}
	
}
