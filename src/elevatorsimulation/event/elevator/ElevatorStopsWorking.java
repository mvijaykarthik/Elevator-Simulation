package elevatorsimulation.event.elevator;

import elevatorsimulation.event.Event;
import elevatorsimulation.master.EventQueue;
import elevatorsimulation.participants.*;

/**
 * This class is used to simulate the event where the
 * elevator stops working for the duration specified.
 * 
 * All people are removed from the elevator
 * The elevator is stopped 
 * The elevator is set not to operate
 * Enqueue the event to fix the elevator
 * 
 * @author vijay
 *
 */
public class ElevatorStopsWorking extends Event {
	
	// Variables are self explanatory
	private Integer elevatorNumber;
	private Integer duration;
	private Building acadBlock;
	private Integer eventId;
	private EventQueue eventQueue;

	/**
	 * Set the event ID of this event
	 * @param eventId used to set eventId of this event
	 */
	private void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * Constructor
	 * Sets event id and other fields of this class
	 * 
	 * @param elevatorNumber the elevator which stops working
	 * @param duration the duration for which elevator will not work
	 * @param acadBlock the building which has the elevators and the floors
	 * @param eventQueue the eventQueue which has a queue of events to be
	 *        executed
	 */
	public ElevatorStopsWorking(Integer elevatorNumber, Integer duration,
			Building acadBlock, EventQueue eventQueue) {
		super();
		this.setEventId( Event.getNumCreatedEvents() );
		this.elevatorNumber = elevatorNumber;
		this.duration = duration;
		this.acadBlock = acadBlock;
		this.eventQueue = eventQueue;
	}

	/**
	 * Returns the event id of the event
	 * 
	 * @return the event id of the event
	 */
	public Integer getEventId() {
		return this.eventId;
	}

	/**
	 * Executes the event
	 * All people are removed from the elevator
	 * The elevator is stopped 
	 * The elevator is set not to operate
	 * Enqueue the event to fix the elevator
	 */
	@Override
	public void executeEvent() {
		System.out.println( "Elevator " + this.elevatorNumber + " stops working." );
		Elevator elevator = this.acadBlock.getElevator(elevatorNumber);
		elevator.removeAllPeople();
		elevator.resetIsOperational();
		elevator.stopElevator();
		ElevatorGettingFixed newEvent = new ElevatorGettingFixed( this.acadBlock, this.elevatorNumber, 
				this.duration - 1, this.eventQueue ); 
		this.eventQueue.addEventBack(newEvent);
	}
	
	/**
	 * Print the name of the event
	 */
	@Override
	public void printMe() {
		System.out.println( " | ElevatorStopsWorking | " );
	}
}
