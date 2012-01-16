package elevatorsimulation.participants;

import java.util.Set;

import elevatorsimulation.participants.impl.PersonImpl;

/**
 * Interface which a Level class should Implement
 * 
 * @author vijay
 *
 */
public interface Level {

	/**
	 * Returns the set of people waiting in the floor
	 * 
	 * @return Set of people waiting in the floor
	 */
	public abstract Set<PersonImpl> getPeopleWaitingInFloor();

	/**
	 * Push the up button on the floor
	 */
	public abstract void pushUpButton();

	/**
	 * Reset the up button on the floor
	 */
	public abstract void resetUpButton();

	/**
	 * Push the down button on the floor
	 */
	public abstract void pushDownButton();

	/**
	 * Reset the down button on the floor
	 */
	public abstract void resetDownButton();

	/**
	 * Get the floor number of the floor
	 * 
	 * @return the floor number of the floor
	 */
	public abstract Integer getLevelNumber();

	/**
	 * Adds a person to the set of people waiting for an elevator
	 * on this level.
	 * 
	 * @param p person to add to the set of people waiting for
	 *        an elevator on this level
	 */
	public abstract void addPerson( PersonImpl p );

	/**
	 * Add a set of people to the set of people waiting for an elevator
	 * on this level.
	 * 
	 * @param setOfPeople Set of people to add to the set of people
	 *        waiting for an elevator on this level
	 */
	public abstract void addPeople( Set<PersonImpl> setOfPeople );

	/**
	 * Remove a set of people from the set of people waiting for an elevator
	 * on this level.
	 * 
	 * @param setOfPeople Set of people to remove from the set of people
	 *        waiting for an elevator on this level
	 */
	public abstract void removePeople( Set<PersonImpl> setOfPeople );

	/**
	 * Removes a person from the set of people waiting for an elevator
	 * on this level.
	 * 
	 * @param p person to remove from the set of people waiting for
	 *        an elevator on this level
	 */
	public abstract void removePerson(Person p);

	/**
	 * Remove all impatient people from this level.
	 */
	public abstract void removeImpatientPeople();

	/**
	 * Increment waiting time for all the people waiting at this level.
	 */
	public abstract void incrementWaitingTimeOfPeople();

	/**
	 * Returns whether the upButton of the floor is pressed or not
	 * 
	 * @return whether the upButton of the floor is pressed or not
	 */
	public abstract Boolean getUpButton();

	/**
	 * Returns whether the downButton of the floor is pressed or not
	 * 
	 * @return whether the downButton of the floor is pressed or not
	 */
	public abstract Boolean getDownButton();

	/**
	 * If the lift is stationary, then returns whether any button 
	 * on this floor is pressed or not
	 * If the lift is going up, then returns whether up button is 
	 * pressed or not
	 * If the lift is going down, then returns whether down button
	 * is pressed or not
	 * 
	 * @param direction direction in which the lift is going
	 * @return returns if the appropriate button in the floor is pressed
	 *         ( see function description )
	 */
	public abstract boolean buttonPressed( Direction direction );

	/**
	 * Update the upButton and downButton of the floor based on 
	 * the set of people waiting for an elevator on this floor
	 */
	public abstract void updateButtons();

}