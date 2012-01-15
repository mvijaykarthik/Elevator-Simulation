package elevatorsimulation.event.elevator;

import elevatorsimulation.event.*;
import elevatorsimulation.master.EventQueue;
import elevatorsimulation.participants.*;

/**
 * This class is used to simulate the event 
 * when an elevator is getting fixed.
 * The elevator is not operational when it is getting fixed.
 * It re-queues itself the required number of times
 * and finally sets the elevator to be operational
 * 
 * @author vijay
 *
 */
public class ElevatorGettingFixed extends Event {
	
	// Variables are self explanatory
	private Building acadBlock;
	private Integer eventId;
	private Integer elevatorNumber;
	private Integer timeLeftTillOperation;
	private EventQueue eventQueue;
	
	
	/**
	 * Function to set event ID of this event
	 * @param eventId is used to set event id of this event
	 */
	private void setEventId( Integer eventId ) {
		this.eventId = eventId;
	}
	
	/**
	 * Constructor
	 * Sets the eventId and fields as given below
	 * 
	 * @param acadBlock the building which has the elevators and floors
	 * @param elevatorNumber the elevator which is getting fixed
	 * @param totalTimeToFix total time taken to fix
	 * @param eventQueue the eventQueue which has a queue of events to be 
	 *        executed
	 */
	public ElevatorGettingFixed( Building acadBlock, Integer elevatorNumber,
			Integer totalTimeToFix, EventQueue eventQueue ) {
		this.acadBlock = acadBlock;
		this.setEventId( Event.getNumCreatedEvents() );
		this.elevatorNumber = elevatorNumber;
		this.timeLeftTillOperation = totalTimeToFix;
		this.eventQueue = eventQueue;
	}
	
	/**
	 * Executes the event
	 * 
	 * It decrements time left till operation
	 * If time left till operation == 0, then it sets elevator 
	 *    as operational
	 * else, it re-queues itself into the event queue.
	 *    
	 */
	@Override
	public void executeEvent() {
		this.timeLeftTillOperation--;
		System.out.println( "Elevator " + elevatorNumber + " has " + 
				timeLeftTillOperation + " time till operation." );

		if ( this.timeLeftTillOperation <= 0 ) {
			Elevator repairedElevator = 
					this.acadBlock.getElevator(elevatorNumber);
			repairedElevator.setIsOperational();
		}
		
		else {
			this.eventQueue.addEventBack(this);
		}
	}

	/**
	 * Returns the event ID of this event
	 * 
	 * @return event ID of the event
	 */
	public Integer getEventId() {
		return this.eventId;
	}
	
	/**
	 * Prints the name of the event
	 */
	@Override
	public void printMe() {
		System.out.println( " | Elevator "+ elevatorNumber + 
				" GettingFixed | " );
	}
}
