package de.neo.cookiebot.vars;

import java.util.HashMap;

public class VarManager {
	
	private HashMap<VarType, String> val;
	
	public VarManager() {
		this.val = new HashMap<>();
	}
	
	public void add(VarType type, String val) {
		this.val.put(type, val);
	}
	
	public String get(VarType type) {
		return this.val.get(type);
	}
	
	public Boolean contains(VarType type) {
		return this.val.containsKey(type);
	}
	
	public HashMap<VarType, String> getVars(){
		return this.val;
	}
}
