package elevatorsimulation.event.person;

import elevatorsimulation.event.Event;
import elevatorsimulation.participants.*;
import elevatorsimulation.participants.impl.PersonImpl;

import java.util.Random;

/**
 * This class is used to simulate the event when a person arrives at
 * a level. He is assigned random start, destination floor and 
 * max waiting time. He is added to the set of waiting people on
 * the start floor
 * 
 * @author vijay
 *
 */
public class PersonArrives extends Event {
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
	 * Sets eventId and other fields of this class
	 * 
	 * @param acadBlock the building which has the elevators and floors
	 */
	public PersonArrives(Building acadBlock) {
		super();
		this.acadBlock = acadBlock;
		this.setEventId( Event.getNumCreatedEvents() );
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
	 * A person is created and is assigned random start, destination 
	 * floor and max waiting time. He is added to the set of waiting 
	 * people on the start floor
	 */
	@Override
	public void executeEvent() {
	    Random randomGenerator = new Random();
	    Integer numFloors = this.acadBlock.getNumLevels();
	    
	    // Source is a random floor
	    Integer sourceLevel = randomGenerator.nextInt( numFloors );
	    Integer destinationLevel;
	    
	    // Destination should be different from source
	    do {
	    	destinationLevel = randomGenerator.nextInt( numFloors );
	    } while ( destinationLevel == sourceLevel );
	    
	    // Generate max Waiting time ( 3 to 4 times number of levels )
	    Integer maxWaitingTime = randomGenerator.nextInt( acadBlock.getNumLevels() ) + 
	    					3 * acadBlock.getNumLevels();
	    
	    // Create a new person
		PersonImpl newPerson = new PersonImpl( sourceLevel, 
				destinationLevel, maxWaitingTime );

		// Add the person to the set of waiting people in the source level
		Level level = this.acadBlock.getLevel( sourceLevel );
		level.addPerson( newPerson );
		
		// Update the buttons to be pressed on the floor
		newPerson.updateFloorButton ( level );
		
		System.out.println( "New Person Arrives " + sourceLevel + 
				" " + destinationLevel );
//		updateFloorButtons( newPerson, level );
	}

	/**
	 * Prints the name of the event
	 */
	@Override
	public void printMe() {
		System.out.println(" | PersonArrives | ");
	}
	
//	private void updateFloorButtons(Person person, Level level) {
//		if ( person.getStartFloor() > person.getDestFloor() ) {
//			if ( level.getDownButton() == false ) {
//				level.pushDownButton();
//			}
//		}
//		
//		else {
//			if ( level.getUpButton() == false ) {
//				level.pushUpButton();
//			}
//		}
//	}
}
