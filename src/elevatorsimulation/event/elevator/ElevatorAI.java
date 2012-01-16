package elevatorsimulation.event.elevator;

import elevatorsimulation.event.Event;
import elevatorsimulation.master.EventQueue;
import elevatorsimulation.participants.*;

import java.util.*;

/**
 * This class is used to simulate the AI which 
 * drives the elevators, it updates the People waiting on the
 * floors for elevators.
 * 
 * This event is always present in the eventQueue.
 * 
 * It enqueues one move for each elevator ( or allow people to enter / exit
 * the elevator ) in the event queue.
 * It increments waiting time of people at each level.
 * It removes impatient people at each level.
 * 
 * @author vijay
 *
 */
public class ElevatorAI extends Event {
	
	// Varible declarations are self explanatory
	private Building acadBlock;
	private Integer eventId;
	private EventQueue eventQueue;
	private double probElevatorFails;
	private static Integer elapsedTime = 0;

	/**
	 * Function to set eventId
	 * @param eventId used to set the event id of this event
	 */
	private void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	/**
	 * Constructor
	 * Sets eventId and other fields
	 * 
	 * @param acadBlock the building wheter the elevators and floors are present
	 * @param eventQueue the eventQueue of master class
	 * @param probElevatorFails probability that an elevator will fail
	 */
	public ElevatorAI(Building acadBlock, EventQueue eventQueue, double probElevatorFails) {
		super();
		this.acadBlock = acadBlock;
		this.setEventId( Event.getNumCreatedEvents() );
		this.eventQueue = eventQueue;
		
		// Check vaildity of probability
		if ( probElevatorFails < 1 && probElevatorFails >= 0 ) {
			this.probElevatorFails = probElevatorFails;
		}
		
		else {
			this.probElevatorFails = 0.05;
		}
	}

	/**
	 * Returns event ID of this event
	 * 
	 * @return event Id of the event
	 */
	public Integer getEventId() {
		return this.eventId;
	}

	/**
	 * Executes the event.
	 * It enqueues one move for each elevator ( or allow people to enter / exit
	 * the elevator ) in the event queue.
	 * It increments waiting time of people at each level.
	 * It removes impatient people at each level.
	 */
	@Override
	public void executeEvent() {
		System.out.println( "Elevator AI Executing" );
		
		Integer numElevators = this.acadBlock.getNumElevators();
		Integer numFloors = this.acadBlock.getNumLevels();
		Integer elevatorIter;
		Integer floorIter;
		
		// Enqueue an action for each elevator
		for ( elevatorIter = 0; elevatorIter < numElevators; elevatorIter++ ) {
			Double randomNumber = Math.random();
			Elevator elevator = this.acadBlock.getElevator( elevatorIter );
			
			// Check if elevator is operational
			if ( elevator.getIsOperational() ) {
				if ( randomNumber < this.probElevatorFails ) {
					// Elevator Fails
					elevatorFails( elevatorIter );
				}
			
				else {
					// Enqueue an action for the elevator
					System.out.println( "Elevator " + elevatorIter 
							+ " " + elevator.getButtonsPressed() );
					setAction( elevatorIter );
				}
			}
			
			else {
				// Elevator is not operational, so do nothing
				continue;
			}
		}
		
		/**
		 * For each floor, 
		 * Increment waiting time of people
		 * Remove Impatient People
		 * Update Floor buttons
		 */
		for ( floorIter = 0; floorIter < numFloors; floorIter++ ) {
			Level level = this.acadBlock.getLevel( floorIter );
			level.incrementWaitingTimeOfPeople();
			level.removeImpatientPeople();
			level.updateButtons();
		}
		
		/**
		 * Requeue this event. It must always remain in the event queue.
		 */
		this.eventQueue.addEventBack(this);
		ElevatorAI.elapsedTime++;
	}

	/**
	 * Called if an elevator fails. The duration of fail is set by this function
	 * randomly. This enqueues the event that the elevator fails.
	 * 
	 * @param elevatorNumber The elevator number which fails.
	 */
	private void elevatorFails(Integer elevatorNumber) {
	    Random randomGenerator = new Random();
	    Integer duration = randomGenerator.nextInt( 3 ) + 2;
	    
	    // Create an event which will make the elevator fail
		ElevatorStopsWorking newEvent = new ElevatorStopsWorking( elevatorNumber, 
				duration, this.acadBlock, this.eventQueue );
		
		// Enqueue it to the eventQueue
		this.eventQueue.addEventBack( newEvent );
	}

	/**
	 * Sets an action for an elevator number elevatorNumber
	 * It either :
	 *   Makes an elevator move ( up or down )
	 *   Make people enter / leave the elevator
	 *   Does nothing if no one needs the elevator
	 *   
	 * @param elevatorNumber the elevator to which action must be assigned
	 */
	private void setAction( Integer elevatorNumber ) {
		
		Elevator elevator = this.acadBlock.getElevator( elevatorNumber );
		Integer currentFloor = elevator.getCurrentFloor();
		SortedSet<Integer> allUpDirectionRequests = new TreeSet<Integer>();
		SortedSet<Integer> allDownDirectionRequests = new TreeSet<Integer>();

		// Get all requests for elevator from higher floors
		allUpDirectionRequests.addAll( elevator.getUpperRequests() );
		allUpDirectionRequests.addAll( this.acadBlock.getRequestsAbove( currentFloor ) );
		
		// Get all requests for elevator from lower floors
		allDownDirectionRequests.addAll( elevator.getLowerRequests() );
		allDownDirectionRequests.addAll( this.acadBlock.getRequestsBelow( currentFloor ) );
		
		System.out.println("UP Requests " + allUpDirectionRequests);
		System.out.println("DOWN Requests " + allDownDirectionRequests);
		
		// Check if elevator should open its doors at this level
		if ( elevator.currentFloorButtonIsPressed() || 
				peopleWantToEnter ( elevator ) ) {
			
			// There are people who want to leave the elevator at this floor
			if ( elevator.currentFloorButtonIsPressed() ) {
				
				// Enqueue an event to make people leave elevator
				enqueuePeopleLeavingElevator( elevatorNumber );
			}
			
			// There are people who want to enter the elevator at this floor
			if ( peopleWantToEnter ( elevator ) ) {
				
				// Enqueue an event to make people enter the elevator
				enqueuePeopleEnteringElevator( elevatorNumber );
				
//				// Other elevators need not stop here now
//				resetFloorButton( level );
			}
			
			// Return after enqueueing an event to open door of elevator
			return;
		}
		
		if ( elevator.getDirection() == Direction.STATIONARY ) {
			
			// If elevator is stationary, go to nearest request, if it exists
			goToNearestRequest( elevatorNumber, allUpDirectionRequests, 
					allDownDirectionRequests );
			return;
		}
		
		// Select direction to move this elevator depending on requests
		// Higher priority is given to current direction requests
		Direction toMove = Direction.STATIONARY;
		if ( elevator.getDirection() == Direction.UP ) {
			if ( allUpDirectionRequests.size() > 0 ) {
				toMove = Direction.UP;
			}
			
			else if ( allDownDirectionRequests.size() > 0 ) {
				toMove = Direction.DOWN;
			}
			
			else {
				// No requests are there for this elevator
				elevator.stopElevator();
			}
		}
		
		// Select direction to move this elevator depending on requests
		// Higher priority is given to current direction requests
		if ( elevator.getDirection() == Direction.DOWN ) {
			if ( allDownDirectionRequests.size() > 0 ) {
				toMove = Direction.DOWN;
			}
			
			else if ( allUpDirectionRequests.size() > 0 ) {
				toMove = Direction.UP;
			}
			
			else {
				// No requests are there for this elevator
				elevator.stopElevator();
			}
		}
		
		if ( toMove != Direction.STATIONARY ) {
			// Enqueue an event to move the elevator in the required direction
			enqueueMove( elevatorNumber, toMove );
		}
	}

	/**
	 * Returns whether people want to enter elevator at current floor
	 * @param elevator the elevator which people might enter
	 * @return whether people want to enter the elevator
	 */
	private boolean peopleWantToEnter( Elevator elevator ) {
		Integer currentFloor = elevator.getCurrentFloor();
		Level level = acadBlock.getLevel( currentFloor );
		
		if ( elevator.isEmpty() ) {
			if ( level.buttonPressed( Direction.UP ) ||
					level.buttonPressed( Direction.DOWN ) ) {
				return true;
			}
		}
		
		return level.buttonPressed( elevator.getDirection() );
	}

	/**
	 * Enqueue an event in the eventQueue to allow people of elevator's 
	 * current floor to enter the elevator
	 * 
	 * @param elevatorNumber the elevator which people of elevator's
	 *        current floor may enter
	 */
	private void enqueuePeopleEnteringElevator( Integer elevatorNumber ) {
		PeopleEnteringElevator newEvent = 
				new PeopleEnteringElevator( elevatorNumber, this.acadBlock );
		this.eventQueue.addEventBack( newEvent );
	}

	/**
	 * Enqueue an event in the eventQueue to allow people to leave the 
	 * elevator
	 * 
	 * @param elevatorNumber the elevator from which people inside the 
	 *        elevator may leave
	 */
	private void enqueuePeopleLeavingElevator( Integer elevatorNumber ) {
		PeopleLeavingElevator newEvent = 
				new PeopleLeavingElevator( elevatorNumber, this.acadBlock );
		this.eventQueue.addEventBack( newEvent );
	}

	/**
	 * Enqueue an event to make the elevator move one floor in a direction
	 * set by the parameter toMove
	 * 
	 * @param elevatorNumber the elevator which would move
	 * @param toMove the direction in which to move
	 */
	private void enqueueMove ( Integer elevatorNumber, Direction toMove ) {
		ElevatorMoves elevatorMoves = 
				new ElevatorMoves( this.acadBlock, elevatorNumber, toMove );
		this.eventQueue.addEventBack( elevatorMoves );
	}
	
	/**
	 * Makes an elevator move towards its nearest elevator request.
	 * 
	 * @param elevatorNumber the elevator to move
	 * @param allUpDirectionRequests a set of all requests from higher floors
	 * @param allDownDirectionRequests a set of all requests from lower floors
	 */
	private void goToNearestRequest( Integer elevatorNumber,
			SortedSet<Integer> allUpDirectionRequests,
			SortedSet<Integer> allDownDirectionRequests ) {
		
		Elevator elevator = this.acadBlock.getElevator( elevatorNumber );
		Integer currentFloor = elevator.getCurrentFloor();
		Direction moveTo = Direction.STATIONARY;
		
		// Make the elevator move in a direction depending on the nearest request
		if ( allDownDirectionRequests.size() == 0 ) {
			if ( allUpDirectionRequests.size() == 0 ) {
				// No requests exist, so stop the elevator
				elevator.stopElevator();
				return;
			}
			
			else {
				// Move up since there are no requests from below
				moveTo = Direction.UP;
			}
		}
		
		else if ( allUpDirectionRequests.size() == 0 ) {
			// Move down since there are no requests from above
			moveTo = Direction.DOWN;
		}
		
		else {
			// Find the closest request and move in that direction.
			Integer minUpDistance = allUpDirectionRequests.first() - currentFloor;
			Integer minDownDistance = allDownDirectionRequests.last() - currentFloor;
			if ( minUpDistance > minDownDistance ) {
				moveTo = Direction.DOWN;
			}
			
			else {
				moveTo = Direction.UP;
			}
		}
		
		if ( moveTo != Direction.STATIONARY ) {
			enqueueMove( elevatorNumber, moveTo );
		}
	}
	
	/**
	 * Function to return total elapsed time.
	 * 
	 * @return total elapsed time
	 */
	public static Integer getElapsedTime() {
		return elapsedTime;
	}
	
	/**
	 * Prints event name
	 */
	@Override
	public void printMe() {
		System.out.println(" | ElevatorAI | ");
	}
}
