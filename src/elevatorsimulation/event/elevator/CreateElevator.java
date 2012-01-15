package elevatorsimulation.event.elevator;

import elevatorsimulation.event.Event;
import elevatorsimulation.participants.*;

/**
 * This class is used to simulate the event 
 * if an elevator needs to be created.
 * 
 * @author vijay
 *
 */
public class CreateElevator extends Event {
	// Building where the elevator should be created
	private Building acadBlock;
	private Integer eventId;
	
	/**
	 * Set the event ID of this event
	 * @param eventId used to set eventId of this event
	 */
	private void setEventId( Integer eventId ) {
		this.eventId = eventId;
	}
	
	/**
	 * Constructor
	 * Sets eventId and other fields
	 * @param acadBlock building where elevator needs to be added
	 */
	public CreateElevator( Building acadBlock ) {
		this.acadBlock = acadBlock;
		this.setEventId( Event.getNumCreatedEvents() );
	}
	
	/**
	 * Executes this event.
	 * Adds an elevator to the building 
	 */
	@Override
	public void executeEvent() {
		this.acadBlock.addElevator();
	}

	/**
	 * Returns the event ID of this event
	 * @return eventId of this event
	 */
	public Integer getEventId() {
		return this.eventId;
	}
	
	/**
	 * Prints the name of the event
	 */
	@Override
	public void printMe() {
		System.out.println(" | CreateElevator | ");
	}
}
 