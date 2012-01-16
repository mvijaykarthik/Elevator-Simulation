package elevatorsimulation.participants;

import java.util.SortedSet;

/**
 * Interface which a Building class should implement.
 * 
 * @author vijay
 *
 */
public interface Building {

	/**
	 * This function adds a new elevator to the building.
	 */
	public abstract void addElevator();

	/**
	 * This function adds a new floor to the building.
	 */
	public abstract void addLevel();

	/**
	 * This function returns the elevatorNumber th elevator
	 * of the building.
	 * 
	 * @param elevatorNumber specifies which elevator to return
	 * @return the elevatorNumber th elevator
	 */
	public abstract Elevator getElevator( Integer elevatorNumber );

	/**
	 * This function returns the levelNumber th floor of the 
	 * building
	 * 
	 * @param levelNumber specifies which floor to return
	 * @return the levelNumber th floor
	 */
	public abstract Level getLevel( Integer levelNumber );

	/**
	 * This function returns the number of floors in the building.
	 * 
	 * @return the number of floors in the building
	 */
	public abstract Integer getNumLevels();

	/**
	 * This function returns the number of elevators in the building
	 * @return the number of elevators in the building.
	 */
	public abstract Integer getNumElevators();

	/**
	 * This function returns a set of floors above currentFloor 
	 * which have requested an elevator.
	 * 
	 * @param currentFloor the floor above which elevator requests 
	 *        must be returned.
	 * @return the set of floors above currentFloor which have 
	 *        requested an elevator
	 */
	public abstract SortedSet<Integer> getRequestsAbove( Integer currentFloor );

	/**
	 * This function returns a set of floors below currentFloor 
	 * which have requested an elevator.
	 * 
	 * @param currentFloor the floor below which elevator requests 
	 *        must be returned.
	 * @return the set of floors below currentFloor which have 
	 *        requested an elevator
	 */
	public abstract SortedSet<Integer> getRequestsBelow( Integer currentFloor );

}