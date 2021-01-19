package de.neo.cookiebot.tick;

/**
 * Interface f�r regelm��ig ausgef�hrte Ticks.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface Tick {
	
	/**
	 * Starten des regelm��igen Ticks.
	 */
	public abstract void run();
	
	/**
	 * Einmaliges ausf�hren des Ticks.
	 */
	public abstract void tick();
	
}
