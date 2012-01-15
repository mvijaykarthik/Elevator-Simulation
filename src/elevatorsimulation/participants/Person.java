package elevatorsimulation.participants;

/**
 * Interface which a Person class should implement.
 * 
 * @author vijay
 *
 */
public interface Person {

	/**
	 * This function returns if the person gives up waiting
	 * for the elevator
	 * 
	 * @return true if person gives up waiting. Else returns false
	 */
	public abstract Boolean giveUpWaiting();

	/**
	 * Increment waiting time of the person
	 */
	public abstract void incrementWaitingTime();

	/**
	 * Returns start floor of the person
	 * 
	 * @return start floor of the person
	 */
	public abstract Integer getStartFloor();

	/**
	 * Return destination floor of the person
	 * 
	 * @return destination floor of the person
	 */
	public abstract Integer getDestFloor();

	/**
	 * Press the appropriate button on the floor so that
	 * the person can get to his destination using an 
	 * elevator
	 * 
	 * @param level the level at which the person is waiting
	 */
	public abstract void updateFloorButton(Level level);

}