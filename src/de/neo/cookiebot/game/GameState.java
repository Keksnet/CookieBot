package de.neo.cookiebot.game;

/**
 * Status eines Spiels.
 * 
 * @author Neo8
 * @version 1.0
 */
public enum GameState {
	
	/**
	 * Spiel ist bereit.
	 */
    READY,
    
    /**
     * Spiel ist gestartet.
     * 
     * @see de.neo.cookiebot.game.Game#start()
     */
    STARTED,
    
    /**
     * Spiel ist gestoppt.
     * 
     * @see de.neo.cookiebot.game.Game#stop()
     */
    STOPPED,
    
    /**
     * Spiel ist pausiert.
     */
    PAUSED
}
