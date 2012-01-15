package elevatorsimulation.participants.impl;

import elevatorsimulation.participants.Level;
import elevatorsimulation.participants.Person;

/**
 * This class is used to implement the interface Person.
 * A person has a start and a destination floor.
 * A person has a maxWaitingTime after which he gets impatient and leaves.
 * A person has a currentWaitingTime
 * 
 * Static Methods include total waiting time of all people, total created
 * people and total number of people who became impatient
 * 
 * @author vijay
 *
 */
public class PersonImpl implements Person {

	// Variable declarations are self explanatory
	private Integer startFloor;
	private Integer destFloor;
	private Integer maxWaitingTime;
	private Integer currentWaitingTime;
	private static Integer totalWaitingTimeOfPeople = 0;
	private static Integer totalCreatedPeople = 0;
	private static Integer totalImpatientPeople = 0;
	private static Integer totalReachedDestination = 0;
	
	/**
	 * Constructor for PersonImpl
	 * Sets the instance fields, and increments totalCreatedPeople
	 * 
	 * @param startFloor sets the start floor of the person
	 * @param destFloor sets the destination floor of the person
	 * @param maxWaitingTime sets the max waiting time of the person
	 */
	public PersonImpl( Integer startFloor, Integer destFloor,
			Integer maxWaitingTime) {
		super();
		this.startFloor = startFloor;
		this.destFloor = destFloor;
		this.maxWaitingTime = maxWaitingTime;
		this.currentWaitingTime = 0;
		PersonImpl.totalCreatedPeople++;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Person#giveUpWaiting()
	 */
	@Override
	public Boolean giveUpWaiting() {
		if ( this.currentWaitingTime > this.maxWaitingTime ) {
			return true;
		}
		
		else {
			return false;
		}		
	}	
	
	/**
	 * Returns the total waiting time of all people created
	 * 
	 * @return waiting time of all people created
	 */
	public static Integer getTotalWaitingTimeOfPeople() {
		return PersonImpl.totalWaitingTimeOfPeople;
	}
	
	/**
	 * Returns total created people till now
	 * @return total created people
	 */
	public static Integer getTotalCreatedPeople() {
		return PersonImpl.totalCreatedPeople;
	}

	/* (non-Javadoc)
	 * @see datastructs.Person#incrementWaitingTime()
	 */
	@Override
	public void incrementWaitingTime() {
		this.currentWaitingTime++;
		PersonImpl.totalWaitingTimeOfPeople++;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Person#getStartFloor()
	 */
	@Override
	public Integer getStartFloor() {
		return this.startFloor;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.Person#getDestFloor()
	 */
	@Override
	public Integer getDestFloor() {
		return this.destFloor;
	}

	/* (non-Javadoc)
	 * @see datastructs.Person#updateFloorButton(datastructs.Level)
	 */
	@Override
	public void updateFloorButton( Level level ) {
		if ( getStartFloor() > getDestFloor() ) {
			if ( level.getDownButton() == false ) {
				level.pushDownButton();
			}
		}
			
		else {
			if ( level.getUpButton() == false ) {
				level.pushUpButton();
			}
		}
	}
	
	/**
	 * Returns the average waiting time of all people created
	 * 
	 * @return average waiting time of all people created
	 */
	public static double averageWaitingTimeOfPeople() {
		return (  ( double ) PersonImpl.totalWaitingTimeOfPeople / 
				  ( double ) PersonImpl.totalCreatedPeople );
	}
	
	/**
	 * Increment the number of impatient people
	 */
	public static void incrementImpatientPeople() {
		PersonImpl.totalImpatientPeople++;
	}
	
	/**
	 * Returns the number of impatient people
	 * 
	 * @return the number of impatient people
	 */
	public static Integer getTotalImpatientPeople() {
		return PersonImpl.totalImpatientPeople;
	}
	
	/**
	 * Increment the number of people who reached destination
	 */
	public static void incrementPeopleWhoReachedDestination() {
		PersonImpl.totalReachedDestination++;
	}
	
	/**
	 * Returns the number of people who reached destination
	 * 
	 * @return the number of people who reached destination
	 */
	public static Integer getTotalPeopleWhoReachedDestination() {
		return PersonImpl.totalReachedDestination;
	}
}
	

