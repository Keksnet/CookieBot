package de.neo.cookiebot.vars;

import java.util.HashMap;

/**
 * Verwaltet Configwerte für Server.
 * 
 * @author Neo8
 * @version 1.0
 */
public class VarManager {
	
	private HashMap<VarType, String> val;
	
	/**
	 * Neue Instanz des VarManagers.
	 */
	public VarManager() {
		this.val = new HashMap<>();
	}
	
	/**
	 * Fügt einen Wert hinzu.
	 * 
	 * @param type Name des Werts.
	 * @param val Wert.
	 */
	public void add(VarType type, String val) {
		this.val.put(type, val);
	}
	
	/**
	 * Ruft einen Wert ab.
	 * 
	 * @param type Name des Werts.
	 * @return Wert.
	 */
	public String get(VarType type) {
		return this.val.get(type);
	}
	
	/**
	 * Gibt an, ob ein Wert verfügbar ist.
	 * 
	 * @param type Name des Werts.
	 * @return Boolean, ob ein Wert verfügbar ist.
	 */
	public Boolean contains(VarType type) {
		return this.val.containsKey(type);
	}
	
	/**
	 * Gibt alle Werte zurück.
	 * 
	 * @return HashMap mit allen Werten.
	 */
	public HashMap<VarType, String> getVars(){
		return this.val;
	}
	
	/**
	 * Löscht alle Werte.
	 */
	public void clear() {
		this.val.clear();
	}
}
