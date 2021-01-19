package de.neo.cookiebot.tick;

/**
 * Interface f&uuml;r regelm&auml;&szlig;ig ausgef&uuml;hrte Ticks.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface Tick {
	
	/**
	 * Starten des regelm&auml;&szlig;igen Ticks.
	 */
	public abstract void run();
	
	/**
	 * Einmaliges ausf&uuml;hren des Ticks.
	 */
	public abstract void tick();
	
}
