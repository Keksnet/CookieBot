package de.neo.cookiebot.tick;

/**
 * Interface für regelmäßig ausgeführte Ticks.
 * 
 * @author Neo8
 * @version 1.0
 */
public interface Tick {
	
	/**
	 * Starten des regelmäßigen Ticks.
	 */
	public abstract void run();
	
	/**
	 * Einmaliges ausführen des Ticks.
	 */
	public abstract void tick();
	
}
