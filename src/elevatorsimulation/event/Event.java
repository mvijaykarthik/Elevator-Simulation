package elevatorsimulation.event;

/**
 * This class in an abstract class for an event.
 * It has a virtual function executeEvent which is implemented
 * by each of the derived classes, and performs the actions required
 * to complete that particular event.
 * 
 * @author vijay
 *
 */
public abstract class Event {
	private static Integer numCreatedEvents = 0;

	/**
	 * Constructor for abstract class event
	 * Increments the number of created events
	 */
	public Event() {
		Event.numCreatedEvents++;
	}
	
	/**
	 * Returns the number of created events so far
	 * @return the number of created events so far
	 */
	public static Integer getNumCreatedEvents() {
		return Event.numCreatedEvents;
	}	
	
	/**
	 * This abstract function is called to execute an event
	 */
	public abstract void executeEvent();
	
	/**
	 * This abstract function is called to print the event name
	 */
	public abstract void printMe();
}
