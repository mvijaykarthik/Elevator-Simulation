package elevatorsimulation.participants;

import java.util.Set;
import java.util.SortedSet;

import elevatorsimulation.participants.impl.PersonImpl;

/**
 * Interface which an Elevator class should implement.
 * 
 * @author vijay
 *
 */
public interface Elevator {

	/**
	 * Moves the elevator one floor up
	 */
	public abstract void moveUp();

	/**
	 * Moves the elevator one floor down
	 */
	public abstract void moveDown();

	/**
	 * Gets the direction in which the elevator is going
	 * 
	 * @return the direction in which the elevator is going
	 */
	public abstract Direction getDirection();

	/**
	 * Gets the opposite direction to the direction in which the 
	 * elevator is going
	 * 
	 * @return the opposite direction to the direction in which the 
	 *         elevator is moving
	 *  
	 */
	public abstract Direction getOppositeDirection();

	/**
	 * Presses the button in the elevator so that it stops at destFloor
	 * 
	 * @param destFloor the floor button which is required to be pressed
	 *        in the elevator
	 */
	public abstract void pressButton( Integer destFloor );

	/**
	 * Press a set of buttons in the elevator so that it stops in 
	 * each of them
	 * 
	 * @param allFloors A set of buttons to press in the elevator
	 */
	public abstract void pressButton( Set<Integer> allFloors );

	/**
	 * Returns a boolean flag stating whether the elevator is operational
	 * 
	 * @return whether the elevator is operational
	 */
	public abstract Boolean getIsOperational();

	/**
	 * Makes the elevator operational
	 */
	public abstract void setIsOperational();

	/**
	 * The elevator no longer operates until setIsOperational() is called
	 */
	public abstract void resetIsOperational();

	/**
	 * Returns whether the button for current floor is pressed inside the
	 * elevator
	 * 
	 * @return whether the button for current floor is pressed inside the
	 *         elevator
	 */
	public abstract Boolean currentFloorButtonIsPressed();

	/**
	 * Sets the elevator to be stationary. It is no longer moving.
	 */
	public abstract void stopElevator();

	/**
	 * This function takes a set of people who want to enter the elevator,
	 * and allows them to enter provided they want to go in the same
	 * direction of the elevator, and the elevator is not full.
	 * 
	 * @param enteringPeople : People who could potentially enter the elevator
	 * @return Set of people who entered the elevator
	 */
	public abstract Set<PersonImpl> peopleEnter( Set<PersonImpl> enteringPeople );

	/**
	 * Returns whether the elevator is full
	 * @return whether the elevator is full
	 */
	public abstract Boolean isFull();

	/**
	 * Returns whether the elevator is empty
	 * @return whether the elevator is empty
	 */
	public abstract Boolean isEmpty();
	/**
	 * People whose destination is elevators currentFloor will
	 * leave the elevator. 
	 *  
	 * @return the number of people who have left the elevator
	 */
	public abstract Integer peopleLeave();

	/**
	 * Returns the current floor the elevator is in
	 * 
	 * @return the curent floor the elevator is in
	 */
	public abstract Integer getCurrentFloor();

	/**
	 * Remove all people from the lift and reset all its buttons pressed.
	 * This is called when the lift becomes faulty
	 */
	public abstract void removeAllPeople();

	/**
	 * Returns a sorted set of button presses in the lift 
	 * to floors above current floor.
	 *  
	 * @return a sorted set of button presses in the lift to floors
	 *         above current floor
	 */
	public abstract SortedSet<Integer> getUpperRequests();

	/**
	 * Returns a sorted set of button presses in the lift 
	 * to floors below current floor.
	 * 
	 * @return a sorted set of button presses in the lift to floors
	 *         below current floor
	 */
	public abstract SortedSet<Integer> getLowerRequests();

	/**
	 * Returns a sorted set of all buttons pressed in the lift.
	 * 
	 * @return a sorted set of all buttons pressed in the lift.
	 */
	public abstract SortedSet<Integer> getButtonsPressed();

}