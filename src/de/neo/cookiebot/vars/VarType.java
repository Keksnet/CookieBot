package de.neo.cookiebot.vars;

/**
 * Typ eines Werts.
 * 
 * @author Neo8
 * @version 1.0
 */
public enum VarType {
	
	/**
	 * Technikrolle.
	 */
    TECHNIK,
    
    /**
     * Debugrolle.
     */
    DEBUG,
    
    /**
     * Werden Debugnachricht gesendet.
     */
    REPORT_ERRORS,
    
    /**
     * Regelnchannel.
     */
    RULES,
    
    /**
     * Supportchannel.
     */
    SUPPORT,
    
    /**
     * TicTacToe Kategorie.
     */
    TICTACTOE_CATEGORY,
    
    /**
     * Lizenzschlüssel.
     */
    LICENSE_KEY,
    
    /**
     * CommandPrefix.
     */
    PREFIX
}
