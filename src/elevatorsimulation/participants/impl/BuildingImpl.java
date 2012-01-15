package elevatorsimulation.participants.impl;

import java.util.*;

import elevatorsimulation.participants.Building;
import elevatorsimulation.participants.Elevator;
import elevatorsimulation.participants.Level;

/**
 * This class is used to implement the interface Building.
 * A building has a list of floors and a list of elevators.
 * Number of floors and Number of elevators are always positive.
 * New floors and elevators can be added later on.
 * 
 * @author vijay
 *
 */
public class BuildingImpl implements Building {

	// floorList has a list of floors in the building.
	private List <LevelImpl> floorList; 

	// elevatorList has a list of elevators in the building
	private List <ElevatorImpl> elevatorList; 


	/**
	 * Constructor for BuildingImpl class.
	 * Creates a building with required number of floors and elevators.
	 * 
	 * @param numFloors Number of floors to create
	 * @param numElevators Number of elevators to create
	 */
	public BuildingImpl ( Integer numFloors, Integer numElevators ) {
		super();
		this.floorList = new ArrayList<LevelImpl>();
		this.elevatorList = new ArrayList<ElevatorImpl>();
		Integer i;

		// Add required number of floors
		for ( i = 0; i < numFloors; i++ ) {
			LevelImpl newLevel = new LevelImpl( i );
			this.floorList.add( newLevel );
		}

		// Add required number of elevators
		for ( i = 0; i < numElevators; i++ ) {
			ElevatorImpl elevator = new ElevatorImpl();
			this.elevatorList.add( elevator );
		}
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#addElevator()
	 */
	@Override
	public void addElevator() {
		ElevatorImpl newElevator = new ElevatorImpl();
		this.elevatorList.add( newElevator );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#addLevel()
	 */
	@Override
	public void addLevel() {
		LevelImpl newLevel = new LevelImpl( this.floorList.size() );
		this.floorList.add( newLevel );
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#getElevator(java.lang.Integer)
	 */
	@Override
	public Elevator getElevator( Integer elevatorNumber ) {
		// Check validity of elevatorNumber
		if ( elevatorNumber < this.elevatorList.size() 
				&& elevatorNumber >= 0 ) {
			return this.elevatorList.get( elevatorNumber );
		}
		
		// If elevator does not exist, print an error
		else {
			System.out.println("Elevator " + elevatorNumber + " does not exist.");
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#getLevel(java.lang.Integer)
	 */
	@Override
	public Level getLevel( Integer levelNumber ) {
		// Check the validity of levelNumber
		if ( levelNumber < this.floorList.size() 
				&& levelNumber >= 0 ) {
			return this.floorList.get( levelNumber );
		}
		
		// If floor does not exist, print an error
		else {
			System.out.println("Level " + levelNumber + " does not exist.");
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#getNumLevels()
	 */
	@Override
	public Integer getNumLevels() {
		return this.floorList.size();
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#getNumElevators()
	 */
	@Override
	public Integer getNumElevators() {
		return this.elevatorList.size();
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#getRequestsAbove(java.lang.Integer)
	 */
	@Override
	public SortedSet<Integer> getRequestsAbove( Integer currentFloor ) {
		Integer floorIter;
		SortedSet<Integer> elevatorRequest = new TreeSet<Integer>();
		
		// Iterate through the floors above currentFloor and check if 
		// the floor buttons are pressed
		for ( floorIter = currentFloor + 1; floorIter < this.floorList.size();
				floorIter++ ) {
			Level level = this.floorList.get(floorIter);
			if ( level.getDownButton() || level.getUpButton() ) {
				elevatorRequest.add( floorIter );
			}
		}
		
		return elevatorRequest;
	}
	
	/* (non-Javadoc)
	 * @see datastructs.BuildingInterface#getRequestsBelow(java.lang.Integer)
	 */
	@Override
	public SortedSet<Integer> getRequestsBelow( Integer currentFloor ) {
		Integer floorIter;
		SortedSet<Integer> elevatorRequest = new TreeSet<Integer>();
		
		// Iterate through the floors above currentFloor and check if 
		// the floor buttons are pressed
		for ( floorIter = currentFloor - 1; floorIter >= 0; floorIter-- ) {
			Level level = this.floorList.get(floorIter);
			if ( level.getDownButton() || level.getUpButton() ) {
				elevatorRequest.add( floorIter );
			}
		}
		
		return elevatorRequest;
	}
}
