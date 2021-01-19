package de.neo.cookiebot.setup;

/**
 * Schritt in dem sich das Setup befindet.
 * 
 * @author Neo8
 * @version 1.0
 * @see de.neo.cookiebot.setup.Setup
 */
public enum SetupStep {
	
	/**
	 * Schritt 1.
	 * 'start' wird erwartet.
	 */
    STEP_0,
    
    /**
     * Schritt 2.
     * Lizenzschlüssel wird erwartet.
     */
    STEP_1,
    
    /**
     * Schritt 3.
     * Debugrolle erwartet.
     */
    STEP_2,
    
    /**
     * Schritt 4.
     * Debugnachricht an/aus erwartet.
     */
    STEP_3,
    
    /**
     * Schritt 5.
     * Regelnkanal erwartet.
     */
    STEP_4,
    
    /**
     * Schritt 6.
     * Supportkanal erwartet.
     */
    STEP_5,
    
    /**
     * Schritt 7.
     * Technikrolle erwartet.
     */
    STEP_6,
    
    /**
     * Schritt 8.
     * TicTacToe Kategorie erwartet.
     */
    STEP_7,
    
    /**
     * Schritt 9.
     * Prefix erwartet.
     */
    STEP_8,
    
    /**
     * Schritt 10.
     * Fertig.
     */
    STEP_9
}
