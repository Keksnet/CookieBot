package de.neo.cookiebot.account.type;

import de.neo.cookiebot.account.Account;
import de.neo.cookiebot.account.AccountType;
import net.dv8tion.jda.api.entities.Member;

public class MasterAccount implements Account {
	
	private AccountType type;
	private MasterAccount master;
	private Member member;
	private Boolean created;
	
	public MasterAccount(Member m) {
		this.type = AccountType.MASTER;
		this.master = this;
		this.member = m;
		this.created = false;
		this.create();
	}
	
	public Member getMember() {
		return this.member;
	}

	@Override
	public AccountType getType() {
		return this.type;
	}

	@Override
	public MasterAccount getMaster() {
		return this.master;
	}

	@Override
	public void create() {
		if(!this.created) {
			this.created = true;
		}
	}

	@Override
	public void delete() throws IllegalAccessException {
		throw new IllegalAccessException("This account can not deleted.");
	}

	@Override
	public Boolean isCreated() {
		return this.created;
	}

}
