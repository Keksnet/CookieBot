package de.neo.cookiebot.account;

import java.util.HashMap;

import de.neo.cookiebot.account.type.MasterAccount;
import net.dv8tion.jda.api.entities.Member;

/**
 * Verwaltet alle MasterAccounts.
 * 
 * @author Neo8
 * @version 1.0
 * @see de.neo.cookiebot.account.type.MasterAccount
 */
public class AccountManager {
	
	private HashMap<Member, MasterAccount> accounts;
	
	/**
	 * Neue Instanz des AccountManagers.
	 */
	public AccountManager() {
		this.accounts = new HashMap<>();
	}
	
	/**
	 * Registriert einen neuen Nutzer. Dabei wird bereits ein {@link de.neo.cookiebot.account.type.MasterAccount} erstellt.
	 * 
	 * @param m Member, der registriert werden soll.
	 * @see de.neo.cookiebot.account.type.MasterAccount
	 */
	public void add(Member m) {
		MasterAccount master = new MasterAccount(m);
		this.accounts.put(m, master);
	}
	
	/**
	 * Gibt den MasterAccount eines Members zurück.
	 * 
	 * @param m Member, dem der Account gehört.
	 * @return MasterAccount, der dem Member zugeordnet ist.
	 */
	public MasterAccount get(Member m) {
		return this.accounts.get(m);
	}
	
	/**
	 * Gibt an, ob ein Member einen {@link de.neo.cookiebot.account.type.MasterAccount} hat.
	 * 
	 * @param m Member, der geprüft werden soll.
	 * @return Boolean, ob der Member bereits einen {@link de.neo.cookiebot.account.type.MasterAccount} hat.
	 * @see de.neo.cookiebot.account.type.MasterAccount
	 */
	public Boolean contains(Member m) {
		return this.accounts.containsKey(m);
	}
	
	/**
	 * Gibt alle registrierten {@link de.neo.cookiebot.account.type.MasterAccount} in einer HashMap zurück.
	 * 
	 * @return HashMap, die alle registrierten {@link de.neo.cookiebot.account.type.MasterAccount} enthält.
	 */
	public HashMap<Member, MasterAccount> getAccounts() {
		return this.accounts;
	}
}
