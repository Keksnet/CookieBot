package de.neo.cookiebot.account;

import java.util.HashMap;

import de.neo.cookiebot.account.type.MasterAccount;
import net.dv8tion.jda.api.entities.Member;

public class AccountManager {
	
	private HashMap<Member, MasterAccount> accounts;
	
	public AccountManager() {
		this.accounts = new HashMap<>();
	}
	
	public void add(Member m) {
		MasterAccount master = new MasterAccount(m);
		this.accounts.put(m, master);
	}
	
	public Account get(Member m) {
		return this.accounts.get(m);
	}
	
	public Boolean contains(Member m) {
		return this.accounts.containsKey(m);
	}
	
	public HashMap<Member, MasterAccount> getAccounts() {
		return this.accounts;
	}
}
