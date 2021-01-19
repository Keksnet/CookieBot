package de.neo.cookiebot.vars;

import java.util.HashMap;

/**
 * Verwaltet Configwerte f&uuml;r Server.
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
	 * F&uuml;gt einen Wert hinzu.
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
	 * Gibt an, ob ein Wert verf&uuml;gbar ist.
	 * 
	 * @param type Name des Werts.
	 * @return Boolean, ob ein Wert verf&uuml;gbar ist.
	 */
	public Boolean contains(VarType type) {
		return this.val.containsKey(type);
	}
	
	/**
	 * Gibt alle Werte zur&uuml;ck.
	 * 
	 * @return HashMap mit allen Werten.
	 */
	public HashMap<VarType, String> getVars(){
		return this.val;
	}
	
	/**
	 * L&ouml;scht alle Werte.
	 */
	public void clear() {
		this.val.clear();
	}
}
