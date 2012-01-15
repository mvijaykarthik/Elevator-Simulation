package elevatorsimulation.participants.impl;

import java.util.*;

import elevatorsimulation.participants.Direction;
import elevatorsimulation.participants.Elevator;
import elevatorsimulation.participants.Person;

/**
 * This class is used to implement the interface Elevator. 
 * An elevator has a maximum capacity of people it can take at a time.
 * It has a set of people currently inside the lift.
 * It stores the direction in which the lift is going currently
 * It has a flag stating whether it is operational or not
 * It has a set of buttons pressed inside the lift.
 * 
 * @author vijay
 *
 */
public class ElevatorImpl implements Elevator {

	// These variable declarations are self explanatory
	private static final Integer ELEVATOR_MAX_CAPACITY = 20;
	private Set <PersonImpl> peopleInLift;
	private Integer currentFloor;
	private Direction currentDirection;
	private Boolean isOperational;
	private SortedSet <Integer> buttonsPressed;
	
	/**
	 * Constructor for ElevatorImpl class
	 * 
	 * @param peopleInLift people currently in lift
	 * @param currentFloor the floor at which the elevator is in
	 * @param currentDirection the direction in which the elevator is going
	 * @param isOperational boolean flag stating whether the elevator is
	 *                      operational
	 * @param buttonsPressed a sorted set of all buttons pressed in the 
	 *                       elevator
	 */
	public ElevatorImpl( Set<PersonImpl> peopleInLift, Integer currentFloor,
			Direction currentDirection, Boolean isOperational,
			SortedSet<Integer> buttonsPressed ) {
		super();
		this.peopleInLift = peopleInLift;
		this.currentFloor = currentFloor;
		this.currentDirection = currentDirection;
		this.isOperational = isOperational;
		this.buttonsPressed = new TreeSet<Integer>();
	}
	
	/**
	 * Constructor for ElevatorImpl class
	 * Creates a new set for people currently in lift and the
	 * buttons pressed in lift. 
	 * Initializes current floor to 0
	 * Sets the flag that the elevator is operational.
	 */
	public ElevatorImpl() {
		super();
		this.peopleInLift = new HashSet<PersonImpl>();
		this.currentFloor = 0;
		this.currentDirection = Direction.STATIONARY;
		this.isOperational = true;
		this.buttonsPressed = new TreeSet<Integer>();
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#moveUp()
	 */
	@Override
	public void moveUp() {
		this.currentDirection = Direction.UP;
		this.currentFloor++;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#moveDown()
	 */
	@Override
	public void moveDown() {
		this.currentDirection = Direction.DOWN;
		this.currentFloor--;		
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return this.currentDirection;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#getOppositeDirection()
	 */
	@Override
	public Direction getOppositeDirection() {
		if ( this.currentDirection == Direction.UP ) {
			return Direction.DOWN;
		}
		
		else if ( this.currentDirection == Direction.DOWN ) {
			return Direction.UP;
		}
		
		else {
			return Direction.STATIONARY;
		}
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#pressButton(java.lang.Integer)
	 */
	@Override
	public void pressButton( Integer destFloor ) {
		buttonsPressed.add( destFloor );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#pressButton(java.util.Set)
	 */
	@Override
	public void pressButton( Set<Integer> allFloors ) {
		buttonsPressed.addAll( allFloors );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#getIsOperational()
	 */
	@Override
	public Boolean getIsOperational() {
		return this.isOperational;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#setIsOperational()
	 */
	@Override
	public void setIsOperational() {
		this.isOperational = true;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#resetIsOperational()
	 */
	@Override
	public void resetIsOperational() {
		this.isOperational = false;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#currentFloorButtonIsPressed()
	 */
	@Override
	public Boolean currentFloorButtonIsPressed() {
		return this.buttonsPressed.contains( this.currentFloor );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#stopElevator()
	 */
	@Override
	public void stopElevator() {
		this.currentDirection = Direction.STATIONARY;
	}
	
	/**
	 * Returns whether a person will enter the elevator. A person 
	 * enters the elevator if it is going in the same direction of his
	 * destination floor.
	 * 
	 * @param p the person to check whether he would enter
	 * @return whether the person enters the elevator
	 */
	private Boolean willPersonEnterElevator ( Person p ) {
		
		// If the elevator is stationary, the person enters the 
		// elevator and sets it to go in the direction he wants.
		if ( this.currentDirection == Direction.STATIONARY ) {
			if ( p.getDestFloor() > this.currentFloor ) {
				this.currentDirection = Direction.UP;
				return true;
			}
			
			else {
				if ( p.getDestFloor() < this.currentFloor ) {
					this.currentDirection = Direction.DOWN;
					return true;
				}
			}
		}
		
		// If the lift is not stationary, then the person would enter
		// if it is going in the same direction as his destination floor
		if( this.currentDirection == Direction.UP ) {
			if ( p.getDestFloor() > this.currentFloor ) {
				return true;
			}
		}
		
		if( this.currentDirection == Direction.DOWN ) {
			if ( p.getDestFloor() < this.currentFloor ) {
				return true;
			}
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#peopleEnter(java.util.Set)
	 */
	@Override
	public Set<PersonImpl> peopleEnter( Set<PersonImpl> enteringPeople ) {
		Set <PersonImpl> peopleWhoHaveEntered = new HashSet<PersonImpl>();
		
		for ( PersonImpl p : enteringPeople ) {
			// break if elevator is full
			if ( this.peopleInLift.size() >= ElevatorImpl.ELEVATOR_MAX_CAPACITY ) {
				break;
			}
			
			// check if the person would want to enter the elevator
			if ( willPersonEnterElevator( p ) ) {
				this.peopleInLift.add( p );
				peopleWhoHaveEntered.add( p );
				this.buttonsPressed.add( p.getDestFloor() );
			}
		}
		
		return peopleWhoHaveEntered;
	}
	
	/**
	 * Returns the maximum capacity of an elevator
	 * @return the maximum capacity of an elevator
	 */
	public static Integer getElevatorMaxCapacity() {
		return ELEVATOR_MAX_CAPACITY;
	}

	/* (non-Javadoc)
	 * @see datastructs.Elevator#isFull()
	 */
	@Override
	public Boolean isFull() {
		return ! ( this.peopleInLift.size() == ElevatorImpl.ELEVATOR_MAX_CAPACITY );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#peopleLeave()
	 */
	@Override
	public Integer peopleLeave() {
		Set <PersonImpl> peopleToRemove = new HashSet<PersonImpl>();
		for( PersonImpl p : this.peopleInLift ) {
			if ( p.getDestFloor() == this.currentFloor ) {
				PersonImpl.incrementPeopleWhoReachedDestination();
				peopleToRemove.add(p);
			}
		}
		
		this.peopleInLift.removeAll( peopleToRemove );
		this.buttonsPressed.remove( this.currentFloor );
		return peopleToRemove.size();
	}

	/* (non-Javadoc)
	 * @see datastructs.Elevator#getCurrentFloor()
	 */
	@Override
	public Integer getCurrentFloor() {
		return this.currentFloor;
	}

	/* (non-Javadoc)
	 * @see datastructs.Elevator#removeAllPeople()
	 */
	@Override
	public void removeAllPeople() {
		this.peopleInLift = new HashSet<PersonImpl>();
		this.resetButtons();
	}
	
	/**
	 * Reset buttons pressed of the lift
	 */
	private void resetButtons() {
		this.buttonsPressed = new TreeSet<Integer>();
	}

	/* (non-Javadoc)
	 * @see datastructs.Elevator#getUpperRequests()
	 */
	@Override
	public SortedSet<Integer> getUpperRequests() {
		return this.buttonsPressed.tailSet( this.currentFloor + 1 );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Elevator#getLowerRequests()
	 */
	@Override
	public SortedSet<Integer> getLowerRequests() {
		return this.buttonsPressed.headSet( this.currentFloor );
	}

	/* (non-Javadoc)
	 * @see datastructs.Elevator#getButtonsPressed()
	 */
	@Override
	public SortedSet<Integer> getButtonsPressed() {
		return this.buttonsPressed;
	}
}
