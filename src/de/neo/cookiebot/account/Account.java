package de.neo.cookiebot.account;

import de.neo.cookiebot.account.type.MasterAccount;

public interface Account {
	
	public abstract AccountType getType();
	
	public abstract MasterAccount getMaster();
	
	public abstract void create();
	
	public abstract void delete() throws IllegalAccessException;
	
	public abstract Boolean isCreated();
}
