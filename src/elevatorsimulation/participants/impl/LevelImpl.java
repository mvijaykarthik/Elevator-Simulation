package elevatorsimulation.participants.impl;

import java.util.*;

import elevatorsimulation.participants.Direction;
import elevatorsimulation.participants.Level;
import elevatorsimulation.participants.Person;

/**
 * This class is used to implement the interface Level.
 * A level has 2 buttons to request an elevator, 
 *    upButton if a person wishes to go to a destination at
 *    a higher floor
 *    downButton if a person wishes to go to a destination at 
 *    a lower floor
 * A level also has a floor Number, which represents its position
 * in the building.
 * It also has a list of people waiting in the level for an elevator.
 * 
 * @author vijay
 *
 */
public class LevelImpl implements Level {
	
	// These variable declarations are self explanatory
	private Boolean upButton;
	private Boolean downButton;
	private Integer floorNumber;
	private Set <PersonImpl> peopleWaitingInFloor;

	/**
	 * Set / reset upButton of the floor, requesting an elevator to come.
	 * 
	 * @param upButton whether to set of reset upButton of floor
	 */
	private void setUpButton( Boolean upButton ) {
		this.upButton = upButton;
	}
	
	/**
	 * Set / reset downButton of the floor, requesting an elevator to come.
	 * 
	 * @param downButton whether to set of reset downButton of floor
	 */
	private void setDownButton( Boolean downButton ) {
		this.downButton = downButton;
	}
	
	/**
	 * Constructor for LevelImpl class
	 * 
	 * Sets the floor number of the level.
	 * Creates a set to store people waiting in the floor.
	 * Set the floor buttons to false
	 * 
	 * @param floorNumber the position of the floor in the building
	 */
	public LevelImpl( Integer floorNumber ) {
		super();
		this.floorNumber = floorNumber;
		this.peopleWaitingInFloor = new HashSet<PersonImpl>();
		this.downButton = false;
		this.upButton = false;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Level#getPeopleWaitingInFloor()
	 */
	@Override
	public Set<PersonImpl> getPeopleWaitingInFloor() {
		return peopleWaitingInFloor;
	}

	/* (non-Javadoc)
	 * @see datastructs.Level#pushUpButton()
	 */
 	@Override
	public void pushUpButton() {
		this.setUpButton(true);
	}

 	/* (non-Javadoc)
	 * @see datastructs.Level#resetUpButton()
	 */
 	@Override
	public void resetUpButton() {
		this.setUpButton(false);
	}

 	/* (non-Javadoc)
	 * @see datastructs.Level#pushDownButton()
	 */
 	@Override
	public void pushDownButton() {
		this.setDownButton(true);
	}

 	/* (non-Javadoc)
	 * @see datastructs.Level#resetDownButton()
	 */
 	@Override
	public void resetDownButton() {
		this.setDownButton(false);
	}

 	/* (non-Javadoc)
	 * @see datastructs.Level#getLevelNumber()
	 */
 	@Override
	public Integer getLevelNumber() {
 		return this.floorNumber;
 	}
 	
 	/* (non-Javadoc)
	 * @see datastructs.Level#addPerson(datastructs.Person)
	 */
 	@Override
	public void addPerson( PersonImpl p ) {
 		this.peopleWaitingInFloor.add( p );
 	}
 	
 	/* (non-Javadoc)
	 * @see datastructs.Level#addPeople(java.util.Set)
	 */
 	@Override
	public void addPeople( Set<PersonImpl> setOfPeople ) {
 		this.peopleWaitingInFloor.addAll( setOfPeople );
 	}
 	
 	/* (non-Javadoc)
	 * @see datastructs.Level#removePeople(java.util.Set)
	 */
 	@Override
	public void removePeople( Set<PersonImpl> setOfPeople ) {
 		this.peopleWaitingInFloor.removeAll( setOfPeople );
 	}
 	
 	/* (non-Javadoc)
	 * @see datastructs.Level#removePerson(datastructs.Person)
	 */
 	@Override
	public void removePerson( Person p ) {
 		this.peopleWaitingInFloor.remove( p );
 	}
 	
 	
 	/* (non-Javadoc)
	 * @see datastructs.Level#removeImpatientPeople()
	 */
 	@Override
	public void removeImpatientPeople() {
 		Set<PersonImpl> peopleToRemove = new HashSet<PersonImpl>(); 
 		
 		// Iterate through all waiting people and remove impatient
 		// people from the set of waiting people.
 		for ( PersonImpl peopleIter : this.peopleWaitingInFloor ) {
 			if ( peopleIter.giveUpWaiting() ) {
 				System.out.println( "A person gave up waiting." );
 				PersonImpl.incrementImpatientPeople();
 				peopleToRemove.add( peopleIter );
 			}
 		}
 		
 		this.peopleWaitingInFloor.removeAll(peopleToRemove);
 	}

 	/* (non-Javadoc)
	 * @see datastructs.Level#incrementWaitingTimeOfPeople()
	 */
 	@Override
	public void incrementWaitingTimeOfPeople() {
 		for ( Person peopleIter : this.peopleWaitingInFloor ) {
 			peopleIter.incrementWaitingTime();
 		}
 	}
 	
 	/* (non-Javadoc)
	 * @see datastructs.Level#getUpButton()
	 */
	@Override
	public Boolean getUpButton() {
		return this.upButton;
	}

 	/* (non-Javadoc)
	 * @see datastructs.Level#getDownButton()
	 */
	@Override
	public Boolean getDownButton() {
		return this.downButton;
	}

	/* (non-Javadoc)
	 * @see datastructs.Level#buttonPressed(datastructs.Direction)
	 */
	@Override
	public boolean buttonPressed(Direction direction) {
		if ( direction == Direction.STATIONARY ) {
			return ( this.upButton || this.downButton );
		}
		
		if ( direction == Direction.UP ) {
			return this.upButton;
		}
		
		if ( direction == Direction.DOWN ) {
			return this.downButton;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see datastructs.Level#updateButtons()
	 */
	@Override
	public void updateButtons() {
		this.resetDownButton();
		this.resetUpButton();
 		for ( Person peopleIter : this.peopleWaitingInFloor ) {
 			peopleIter.updateFloorButton( this );
 		}
	}
 }
