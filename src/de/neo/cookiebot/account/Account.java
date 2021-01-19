package de.neo.cookiebot.account;

import de.neo.cookiebot.account.type.MasterAccount;

/**
 * Interface für Accounts.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface Account {
	
	/**
	 * Gibt den AccountTyp zurück.
	 * 
	 * @return Typ des Accounts.
	 */
	public abstract AccountType getType();
	
	/**
	 * Gibt den MasterAccount zurück.
	 * 
	 * @return MasterAccount des Accounts.
	 */
	public abstract MasterAccount getMaster();
	
	/**
	 * Markiert den Account als erstellt.
	 */
	public abstract void create();
	
	/**
	 * Entfernt den Account.
	 * 
	 * @throws IllegalAccessException Wird geworfen, wenn essentielle Accounts gelöscht werden sollen. Zum Beispiel {@link de.neo.cookiebot.account.type.MasterAccount}
	 * @see de.neo.cookiebot.account.type.MasterAccount
	 */
	public abstract void delete() throws IllegalAccessException;
	
	/**
	 * Gibt an, ob der Account als erstellt markiert ist.
	 * 
	 * @return Boolean, ob der Account als erstellt markiert ist.
	 */
	public abstract Boolean isCreated();
}
