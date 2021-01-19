package de.neo.cookiebot.account;

import de.neo.cookiebot.account.type.MasterAccount;

/**
 * Interface f&uuml;r Accounts.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface Account {
	
	/**
	 * Gibt den AccountTyp zur&uuml;ck.
	 * 
	 * @return Typ des Accounts.
	 */
	public abstract AccountType getType();
	
	/**
	 * Gibt den MasterAccount zur&uuml;ck.
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
	 * @throws IllegalAccessException Wird geworfen, wenn essentielle Accounts gel&ouml;scht werden sollen. Zum Beispiel {@link de.neo.cookiebot.account.type.MasterAccount}
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
