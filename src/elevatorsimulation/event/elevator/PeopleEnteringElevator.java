package elevatorsimulation.event.elevator;

import elevatorsimulation.event.Event;
import elevatorsimulation.participants.*;
import elevatorsimulation.participants.impl.PersonImpl;

import java.util.*;

/**
 * This class is used to simulate the event where people enter the 
 * elevator.
 * 
 * @author vijay
 *
 */
public class PeopleEnteringElevator extends Event{
	// Variable declarations are self explanatory
	private Integer elevatorNumber;
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
	 * 
	 * Sets eventId and other fields of this class
	 * 
	 * @param elevatorNumber the elevator which people enter
	 * @param acadBlock the building which has the elevators and floors
	 */
	public PeopleEnteringElevator(Integer elevatorNumber, Building acadBlock) {
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
		return eventId;
	}

	/**
	 * Executes the event
	 * 
	 * Allow a set of people who want to enter the elevator to 
	 * enter the elevator.
	 * Remove them from the set of people waiting in the floor.
	 * Reset the floor button as the elevator allowed people to enter.
	 */
	@Override
	public void executeEvent() {
		Elevator elevator = this.acadBlock.getElevator( this.elevatorNumber );
		Integer currentFloor = elevator.getCurrentFloor();
		Level level = this.acadBlock.getLevel( currentFloor );
		
		Set<PersonImpl> waitingPeople = level.getPeopleWaitingInFloor();
		Set<PersonImpl> enteringPeople = elevator.peopleEnter( waitingPeople );
		
		level.removePeople( enteringPeople );
		resetFloorButton(elevator, level);
		System.out.println(enteringPeople.size() + 
				" People Entering Elevator " + this.elevatorNumber );
	}

	/**
	 * Resets the appropriate floor button of the level
	 * @param elevator the elevator which people entered
	 * @param level the level whose button to reset
	 */
	private void resetFloorButton(Elevator elevator, Level level) {
		if ( elevator.getDirection() == Direction.UP ) {
			level.resetUpButton();
		}
		
		if ( elevator.getDirection() == Direction.DOWN ) {
			level.resetDownButton();
		}
	}
	
	/**
	 * Prints the name of the event
	 */
	@Override
	public void printMe() {
		System.out.println( " | PeopleEnteringElevator " + 
				elevatorNumber + " | " );
	}
}
