package elevatorsimulation.event.elevator;

import elevatorsimulation.event.Event;
import elevatorsimulation.participants.*;

/**
 * This class is to simulate the event where an elevator moves
 * It makes an elevator move either up or down.
 * 
 * @author vijay
 *
 */
public class ElevatorMoves extends Event {
	// Variable declarations are self explanatory
	private Building acadBlock;
	private Integer eventId;
	private Integer elevatorNumber;
	private Direction direction;
	
	/**
	 * Function to set event id of this event
	 * @param eventId used to set event id of this event
	 */
	private void setEventId( Integer eventId ) {
		this.eventId = eventId;
	}
	
	/**
	 * Function to get eventId of this event
	 * @return eventId of this event
	 */
	public Integer getEventId() {
		return this.eventId;
	}

	/**
	 * Constructor
	 * Sets eventId and fields of this class
	 * 
	 * @param acadBlock the building which has the elevators and floors
	 * @param elevatorNumber the elevator which to move
	 * @param direction the direction in which to move the elevator
	 */
	public ElevatorMoves( Building acadBlock, Integer elevatorNumber,
			Direction direction ) {
		this.acadBlock = acadBlock;
		this.setEventId( Event.getNumCreatedEvents() );
		this.elevatorNumber = elevatorNumber;
		this.direction = direction;
	}
	
	/**
	 * Execute the event.
	 * This makes the elevator specified move in the direction specified
	 * when creating this event.
	 */
	@Override
	public void executeEvent() {
		System.out.println("Elevator Move Executing");
		Elevator elevator = this.acadBlock.getElevator( elevatorNumber );
		
		// If direction is up, then move the elevator up
		if ( this.direction == Direction.UP ) {
			elevator.moveUp();
		}
		
		// If direction is down, then move the elevator down
		if ( this.direction == Direction.DOWN ) {
			elevator.moveDown();
		}
		
		System.out.println( "Elevator " + elevatorNumber + 
				" moved to floor " + elevator.getCurrentFloor() );
	}
	
	/**
	 * Print the name of the event
	 */
	@Override
	public void printMe() {
		System.out.println( " | Elevator " + elevatorNumber + " Moves | " );
	}
}
