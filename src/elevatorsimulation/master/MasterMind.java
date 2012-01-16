package elevatorsimulation.master;

import elevatorsimulation.event.Event;
import elevatorsimulation.event.elevator.ElevatorAI;
import elevatorsimulation.event.person.PersonArrives;
import elevatorsimulation.participants.*;
import elevatorsimulation.participants.impl.BuildingImpl;
import elevatorsimulation.participants.impl.PersonImpl;

/**
 * This class prepares the simulation to run.
 * It creates a building with the required number of floors and 
 * elevators. It has an eventQueue. It creates PersonArrives 
 * event at intervals for the simulation to take place. 
 * It also creates an ElevatorAI event which manages the 
 * elevators.
 * 
 * @author vijay
 *
 */
public class MasterMind {
	// Variable declarations are self explanatory
	private Building acadBlock;
	private EventQueue eventQueue;
	private Integer timeToSimulate;
	private Double probCreatePerson;
	
	/**
	 * Constructor
	 * Creates a building and an empty eventQueue
	 * @param numFloors number of floors in the building
	 * @param numElevators number of elevators in the building
	 * @param numberOfIterations number of iterations to run the simulation
	 * @param probCreatePerson probability with which a person is created
	 */
	public MasterMind ( Integer numFloors, Integer numElevators 
			, Integer timeToSimulate, Double probCreatePerson ) {
		this.acadBlock = new BuildingImpl( numFloors, numElevators );
		this.eventQueue = new EventQueue();
		this.timeToSimulate = timeToSimulate;
		
		// Check validity of probability
		if ( probCreatePerson >= 0 && probCreatePerson <= 1 ) {
			this.probCreatePerson = probCreatePerson;
		}
		
		else {
			this.probCreatePerson = 0.2;
		}
	}
	
	/**
	 * Constructor
	 * Creates a building and an empty eventQueue
	 * @param numFloors number of floors in the building
	 * @param numElevators number of elevators in the building
	 * @param numberOfIterations number of iterations to run the simulation
	 */
	public MasterMind ( Integer numFloors, Integer numElevators 
			, Integer numberOfIterations ) {
		this.acadBlock = new BuildingImpl( numFloors, numElevators );
		this.eventQueue = new EventQueue();
		this.timeToSimulate = numberOfIterations;
		this.probCreatePerson = 0.3;
	}
	
	/**
	 * Creates a building and assigns it to acadBlock
	 * 
	 * @param numFloors number of floors in the building
	 * @param numElevators number of elevators in the building
	 */
	public void createBuilding( Integer numFloors, 
			Integer numElevators ) {
		this.acadBlock = new BuildingImpl( numFloors, numElevators );
		this.eventQueue = new EventQueue();
	}
	
	/**
	 * Runs the simulation. Create an ElevatorAI event to manage the 
	 * elevators. People are created at intervals. At every iteration, 
	 * the event at the head of the event queue is executed.
	 */
	public void startSimulation() {
		ElevatorAI elevatorAI = 
				new ElevatorAI( this.acadBlock, this.eventQueue, 0.0 );
		this.eventQueue.addEventFront( elevatorAI );
		Integer i = 0;

		while ( ElevatorAI.getElapsedTime() < this.timeToSimulate ) {
			System.out.println( "\nIter " + i );
			System.out.println( "Time " + PersonImpl.getTotalWaitingTimeOfPeople() );
			System.out.println( "Event queue size: " + 
					this.eventQueue.size() );
			this.eventQueue.printQueue();
			Double randomNumber = Math.random();
			
			// Enqueue an event personArrives
			if ( randomNumber <= this.probCreatePerson ){
				enqueuePersonArrives();
			}
			
			// Execute an event
			this.eventQueue.executeAnEvent();
			i ++;
		}
		
		// Print statistics
		System.out.println( "\nSimulated with " + 
				this.acadBlock.getNumElevators() + " elevators, " +
				this.acadBlock.getNumLevels() + " levels with " +
				this.probCreatePerson + " probability \nto create a person, " +
				"for " + this.timeToSimulate + " time instants");
		System.out.println( "Total Created Events : " + 
				Event.getNumCreatedEvents() );
		System.out.println( "Average Waiting Time : " + 
				PersonImpl.averageWaitingTimeOfPeople() );
		System.out.println( "Created People : " + 
				PersonImpl.getTotalCreatedPeople() );
		System.out.println( "Num Impatient People : " + 
				PersonImpl.getTotalImpatientPeople() );
	}
	 
	/**
	 * Enqueue PersonArrives event into the event queue
	 */
	private void enqueuePersonArrives() {
		PersonArrives newEvent = new PersonArrives( this.acadBlock );
		this.eventQueue.addEventBack( newEvent );
	}
}
