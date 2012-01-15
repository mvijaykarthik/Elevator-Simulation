package elevatorsimulation.event.elevator;

import elevatorsimulation.event.Event;
import elevatorsimulation.participants.*;

/**
 * This class is used to simulate the event where people leave 
 * the elevator
 * 
 * @author vijay
 *
 */
public class PeopleLeavingElevator extends Event {
	// Variable declarations are self explanatory
	private Integer elevatorNumber;
	private Building acadBlock;
	private Integer eventId;
	
	/**
	 * Set the event ID of this event
	 * @param eventId used to set eventId of this event
	 */
	private void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	/**
	 * Constructor
	 * Sets the eventId and the parameters of this class
	 * 
	 * @param elevatorNumber the elevator which people are leaving
	 * @param acadBlock the building which has the elevators and floors
	 */
	public PeopleLeavingElevator(Integer elevatorNumber, Building acadBlock) {
		super();
		this.setEventId( Event.getNumCreatedEvents() );
		this.elevatorNumber = elevatorNumber;
		this.acadBlock = acadBlock;
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
	 * People whose destination is elevators current floor leave the elevator.
	 */
	@Override
	public void executeEvent() {
		Elevator elevator = 
				this.acadBlock.getElevator( this.elevatorNumber );
		Integer numPeopleLeave = elevator.peopleLeave();
		System.out.println( numPeopleLeave + 
				" People Leaving Elevator " + this.elevatorNumber );
	}
	
	/**
	 * Prints the name of the event.
	 */
	@Override
	public void printMe() {
		System.out.println( " | PeopleLeavingElevator " + 
				this.elevatorNumber + "  | " );
	}
}
