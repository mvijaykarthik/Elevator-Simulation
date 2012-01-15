package elevatorsimulation.master;

import java.util.*;

import elevatorsimulation.event.*;

/**
 * This class has a Queue of events which is executed one by one 
 * from the head end.
 * 
 * @author vijay
 *
 */
public class EventQueue {
	// The queue where events to be executed are queued
	private List <Event> eventQueue;

	/**
	 * Constructor
	 * Creates an empty eventQueue
	 */
	public EventQueue() {
		super();
		this.eventQueue = new ArrayList<Event>();
	}
	
	/**
	 * Adds an event to the back of the queue
	 * 
	 * @param event event to be added to the back of the queue
	 */
	public void addEventBack( Event event ) {
		this.eventQueue.add( this.eventQueue.size(), event );
	}

	/**
	 * Adds an event to the front of the queue
	 * 
	 * @param event event to be added to the front of the queue
	 */
	public void addEventFront( Event event ) {
		this.eventQueue.add( 0, event );
	}

	/**
	 * Returns the number of events in the event queue
	 * 
	 * @return the number of events in the event queue
	 */
	public Integer size() {
		return this.eventQueue.size();
	}
	
	/**
	 * Executes the event at the head of the queue
	 * and removes it from the queue
	 */
	public void executeAnEvent () {
		if ( this.eventQueue.size() == 0 ) {
			return;
		}
		
		Event event = this.eventQueue.get(0);
		this.eventQueue.remove(0);
		event.executeEvent();
	}
	
	/**
	 * Prints all the events in the event queue
	 */
	public void printQueue() {
		for ( Event e : this.eventQueue ) {
			e.printMe();
		}
	}
}
